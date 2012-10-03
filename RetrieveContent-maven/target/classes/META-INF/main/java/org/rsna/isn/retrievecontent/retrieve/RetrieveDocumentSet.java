/* Copyright (c) <2011>, <Radiological Society of North America>
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the <RSNA> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package org.rsna.isn.retrievecontent.retrieve;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

/**
 * Handle iti-18 and iti-43 to Retrieve KOS and report documents.
 *
 * @version @author
 * 1.0.0    oyesanyf
 * 1.1.0    Wendy Zhu   implement code to handle result from GetSubmissionSetAndContentQuery
 * 2.1.1    Wendy Zhu   study folder name:StudyDesc-StudyDate-StudyUID
 *
 */
public class RetrieveDocumentSet {

    private static final Logger logger = Logger.getLogger(RetrieveDocumentSet.class);
    private HashMap<String, ArrayList<String>> docList;
    private List<DocumentInfo> docInfoList;
    private String source;
    private String destination;
    public int NumOfDocs;

    public RetrieveDocumentSet() throws FileNotFoundException {
        source = Configuration.getTmpDir().toString();
        destination = Configuration.getImageDir().toString();
    }

    public List<DocumentInfo> RetrieveDocuments(String RsnaPatientID) throws IOException, Exception {
        docList = new HashMap<String, ArrayList<String>>();
        docInfoList = new ArrayList<DocumentInfo>();
        NumOfDocs = 0;

        try {
            if (RsnaPatientID.length() < 1) {
                throw new Exception("A value is required for all XML fields");
            }

            //Get SubmissionSet IDs and DocumentUniqueIDs
            ITI18 query18 = new ITI18();
            ITI18DataType input18 = new ITI18DataType();

            input18.setRegistryURL(Configuration.RegistryURL);
            input18.setRepositoryUniqueID(Configuration.RepositoryUniqueID);
            input18.setAssigningAuthorityUniversalId(Configuration.AssigningAuthorityUniversalId.trim());
            input18.setAssigningAuthorityUniversalIdType(Configuration.AssigningAuthorityUniversalIdType.trim());
            input18.setPatientID(RsnaPatientID);

            docList = query18.queryDocuments(input18);
            if (docList.isEmpty()) {
                throw new Exception("No documents returned for ID " + RsnaPatientID);
            }

            //Retrieve documents by DocumentUniqueIDs (KOS&Report)
            ITI43 query43 = new ITI43();
            ITI43DataType input43 = new ITI43DataType();

            input43.setRepositoryURL(Configuration.RepositoryURL.trim());
            input43.setRepositoryUniqueId(Configuration.RepositoryUniqueID.trim());
            input43.setHomeCommunityId(Configuration.HomeCommunityId.trim());
            input43.setAssigningAuthorityUniversalId(Configuration.AssigningAuthorityUniversalId.trim());
            input43.setAssigningAuthorityUniversalIdType(Configuration.AssigningAuthorityUniversalIdType.trim());
            input43.setPatientID(RsnaPatientID);

            File dirTemp = Configuration.getTmpDir();
            input43.setDownloadDIR(dirTemp.getAbsolutePath().toString());

            //For each submission set, get documents
            Iterator ssItr = docList.keySet().iterator();
            while (ssItr.hasNext()) {
                String submissionSetID = (String) ssItr.next();
                logger.info("Getting documents for submission set#"+submissionSetID+"...");

                DocumentInfo docInfo = null;
                Iterator<String> docItr = docList.get(submissionSetID).iterator();
                while (docItr.hasNext()) {
                    String docID = docItr.next();
                    logger.info("docID# " + docID);

                    input43.setDocumentUniqueId(docID);
                    dirTemp = query43.queryDocuments(input43, RsnaPatientID);
                    String downloadedFile = dirTemp.getAbsolutePath().toString();

                    if (downloadedFile != null) {
                        try {
                            String studyPath="";
                            if (docInfo != null)
                                studyPath = docInfo.getStudyPath();
                            docInfo = ProcessFile(downloadedFile, RsnaPatientID, docID, submissionSetID, studyPath);
                            if (docInfo != null) {
                                docInfoList.add(docInfo);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                        NumOfDocs++;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
       }
        return docInfoList;
    }

    private DocumentInfo ProcessFile(String fileName, String rsnaPatientID, String documentUniqueID, String submissionSetID, String studyPath) throws IOException, Exception {
        DocumentInfo docInfo = null;
        String ssID = submissionSetID.substring(submissionSetID.lastIndexOf(":")+1);
        String ssPath = destination + File.separatorChar + ssID;
        String newFname = "";
        String mimeType;

        File fs = new File(fileName);
        File fd = new File(destination);

        //  check if source directory is there
        if (!fs.exists()) {
            throw new Exception("Source Directory or File doesn't exist " + fs);
        } else if (!fd.exists()) {
            throw new Exception("Destination Directory or File doesn't exist " + fd);
        // create destination Directory
        } else if (fs.exists() && fd.exists()) {
            //decide KOS or Report file
            DicomObject object = null;
            DicomInputStream dis = null;
            try {
                dis = new DicomInputStream(fs);
                object = dis.readDicomObject();
                mimeType = "application/dicom-kos";
            } catch (Exception e) {
                mimeType = "text/plain";
            }

            //Read KOS header
            if (mimeType.equals("application/dicom-kos")) {
                dis.close();
                ReadKOS readKOS = new ReadKOS();
                docInfo = readKOS.listHeader(object, rsnaPatientID, documentUniqueID);

                String studyFolderName = "UNKNOWN";
                if (docInfo.getStudyDescription() != null)
                    studyFolderName = docInfo.getStudyDescription().replaceAll("[^a-zA-Z0-9]+", "_");
                if (docInfo.getStudyDate() != null)
                    studyFolderName = studyFolderName + "-" + docInfo.getStudyDate();
                if (docInfo.getStudyTime() != null)
                    studyFolderName = studyFolderName + "-" + docInfo.getStudyInstanceUID();
                //Max folder name is 256
                if (studyFolderName.length() > 256)
                {
                    studyFolderName = studyFolderName.substring(0, 255);
                }
                
                studyPath = destination + File.separatorChar + docInfo.getPatientName().replaceAll("[^a-zA-Z0-9]+", "_")
                        + File.separatorChar + studyFolderName;
                
                docInfo.setStudyPath(studyPath);
                File studyDir = new File(studyPath);
                if (!studyDir.exists()) {
                    studyDir.mkdirs();
                }

                String studyUID = docInfo.getStudyInstanceUID();
                //not store KOS File
                if (!fs.delete()) {
                    logger.error("Error in deleting KOS file of study#[" + studyUID + "]");
                }

                //move the previous report file to patient folder
                //it happens when report file was arrived prior KOS file
                File ssDir = new File(ssPath);
                String[] repFiles = ssDir.list();
                if (repFiles != null) {
                    for (int i=0; i<repFiles.length; i++) {
                        fs = new File(ssPath + File.separatorChar + repFiles[i]);

                        File docDir = new File(studyPath + File.separatorChar + "other");
                        if (!docDir.exists()) {
                            docDir.mkdirs();
                        }
                        newFname = studyPath + File.separatorChar + "other" + File.separatorChar + "REPORT" + ssID + ".txt";
                        fd = new File(newFname);
                        try {
                            CopyFile(fs, fd);
                            if (!fs.delete()) {
                                logger.error("Error in deleting report file");
                            }
                        } catch (Exception e) {
                            logger.error("Error on move report file:" + e.getMessage());
                        }
                    }
                    ssDir.delete();
                }
            }
            else {
                /*
                 * Store report file.
                 * If studyPath is passed in means KOS file has been processed,
                 *  Save the report file in studyPath\other
                 * Else means report file come in first, no study information
                 *  Save the report file in a temp folder, move it to final path when KOS received
                 */
                if (studyPath.length() > 0) {
                    File ssDir = new File(studyPath + File.separatorChar + "other");
                    if (!ssDir.exists()) {
                        ssDir.mkdirs();
                    }
                    newFname = studyPath + File.separatorChar + "other" + File.separatorChar + "REPORT" + ssID + ".txt";
                }
                else {
                    newFname = ssPath + File.separatorChar + fs.getName();
                    File ssDir = new File(ssPath);
                    if (!ssDir.exists()) {
                        ssDir.mkdirs();
                    }
                }

                fd = new File(newFname);
                try {
                    CopyFile(fs, fd);
                    if (!fs.delete()) {
                        logger.error("Error in deleting report file");
                    }
                } catch (Exception e) {
                    logger.error("Error on move report file:" + e.getMessage());
                }
            }
        }
        return docInfo;
    }

    private void CopyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        java.nio.channels.FileChannel source = null;
        java.nio.channels.FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }

            if(destination != null) {
                destination.close();
            }
        }
    }
}
