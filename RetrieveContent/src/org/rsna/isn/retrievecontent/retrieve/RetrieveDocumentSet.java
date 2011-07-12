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
import java.util.Properties;
import org.apache.log4j.Logger;
//import javax.jws.soap.SOAPBinding;
//import javax.xml.ws.BindingType;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
//@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)

/**
 * Read KOS file.
 *
 * @version @author
 * 1.0.0    oyesanyf
 * 1.1.0    Wendy Zhu
 *
 */
public class RetrieveDocumentSet {

    private static final Logger logger = Logger.getLogger(RetrieveDocumentSet.class);
    public int NumOfDocs = 0;
    private ArrayList<String> docList;
    private List<DocumentInfo> docInfoList;
    private String source;
    private String destination;
    private String patientPath;
    private String reportPath;

    public RetrieveDocumentSet() throws FileNotFoundException {
        source = Configuration.tempdir;
        destination = Configuration.imagedir;
        patientPath="";
    }

    public List<DocumentInfo> RetrieveDocuments(String RsnaPatientID) throws IOException, Exception {
        docList = new ArrayList<String>();
        docInfoList = new ArrayList<DocumentInfo>();
        NumOfDocs = 0;

        try {
            if (RsnaPatientID.length() < 1) {
                throw new Exception("A value is required for all XML fields");
            }

            //Get DocumentUniqueIDs
            ITI18 query18 = new ITI18();
            ITI18DataType input18 = new ITI18DataType();

            input18.setRegistryURL(Configuration.RegistryURL);
            input18.setRepositoryUniqueID(Configuration.RepositoryUniqueID);
            input18.setAssigningAuthorityUniversalId(Configuration.AssigningAuthorityUniversalId.trim());
            input18.setAssigningAuthorityUniversalIdType(Configuration.AssigningAuthorityUniversalIdType.trim());
            input18.setPatientID(RsnaPatientID);

            docList = query18.queryDocuments(input18);
            if (docList == null) {
                throw new Exception("No documents returned for ID " + RsnaPatientID);
            }

            //Retrieve documents by DocumentUniqueIDs (KOS&Report)
            ITI43 query43 = new ITI43();
            ITI43DataType input43 = new ITI43DataType();

            input43.setRepositoryURL(Configuration.RepositoryURL.trim());
            input43.setRepositoryUniqueId(Configuration.RepositoryUniqueID.trim());
            input43.setHomeCommunityId(Configuration.HomeCommunityId.trim());
            File dirTemp = new File(Configuration.tempdir);
            input43.setDownloadDIR(dirTemp.getAbsolutePath().toString());

            Iterator<String> itr = docList.iterator();
            while (itr.hasNext()) {
                String element = itr.next();
                logger.info("getting document with id " + element);
                input43.setDocumentUniqueId(element);
                dirTemp = query43.queryDocuments(input43, RsnaPatientID);
                String downloadedFile = dirTemp.getAbsolutePath().toString();
                if (downloadedFile != null) {
                    try {
                        DocumentInfo docInfo = ProcessFile(downloadedFile, RsnaPatientID, element);
                        if (docInfo != null) {
                            docInfoList.add(docInfo);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    NumOfDocs++;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
       }
        return docInfoList;
    }

    private DocumentInfo ProcessFile(String fileName, String rsnaPatientID, String documentUniqueID) throws IOException, Exception {
        DocumentInfo docInfo = null;
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

                //Create patient folder if not exist
                patientPath = destination + File.separatorChar + docInfo.getPatientName();
                File patientDir = new File(patientPath);
                if (!patientDir.exists()) {
                    if (patientDir.mkdir()) {
                        logger.info("Created patient folder " + patientPath);
                    } else {
                        throw new Exception("Error on creating patient folder " + patientPath);
                    }
                }

                //Create study folder under patient folder if not exist
                String studyInstanceUID = docInfo.getStudyInstanceUID();
                String studyPath = patientPath + File.separatorChar + studyInstanceUID;
                File studyDir = new File(studyPath);
                if (!studyDir.exists()) {
                    if (studyDir.mkdir()) {
                        logger.info("Created study folder " + studyPath);
                    } else {
                        throw new Exception("Error on creating study folder " + studyPath);
                    }
                }

                //Store KOS File
                //String kosFilePath   = studyPath + File.separatorChar ;
                String kosFilePath   = patientPath + File.separatorChar + "kos";
                File kosDir = new File(kosFilePath);
                if (!kosDir.exists()) {
                    kosDir.mkdir();
                }
                newFname =  kosFilePath + File.separatorChar + "KOS" + studyInstanceUID + ".dcm";
                fd = new File(newFname);
                CopyFile(fs, fd);
                logger.info("Moved " + fileName + "to directory " + studyPath);
                if (!fs.delete()) {
                    logger.error("Error in deleting KOS file");
                }

                reportPath = patientPath + File.separatorChar + "report";
                File reportDir = new File(reportPath);
                if (!reportDir.exists()) {
                    reportDir.mkdir();
                }
                //move the previous report file to patient folder
                //it happens when report file was arrived prior KOS file
                File repDir = new File(Configuration.reportdir);
                String[] repFiles = repDir.list();
                if (repFiles != null) {
                    for (int i=0; i<repFiles.length; i++) {
                        fs = new File(Configuration.reportdir + File.separatorChar + repFiles[i]);
                        newFname = reportPath + File.separatorChar + fs.getName() + ".txt";
                        fd = new File(newFname);
                        try {
                            CopyFile(fs, fd);
                            if (!fs.delete()) {
                                logger.error("Error in deleting report file");
                            }
                        } catch (Exception e) {
                            logger.error("Error" + e.getMessage());
                        }
                    }
                }

            }
            else {
                //Store report file
                if (patientPath.isEmpty()) {
                    newFname = Configuration.reportdir + File.separatorChar + fs.getName() + ".txt";
                }
                else {
                    reportPath = patientPath + File.separatorChar + "report";
                    File reportDir = new File(reportPath);
                    if (!reportDir.exists()) {
                        reportDir.mkdir();
                    }
                    newFname = reportPath + File.separatorChar + fs.getName() + ".txt";
                }

                fd = new File(newFname);
                try {
                    CopyFile(fs, fd);
                    if (!fs.delete()) {
                        logger.error("Error in deleting report file");
                    }
                } catch (Exception e) {
                    logger.error("Error" + e.getMessage());
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
