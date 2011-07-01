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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)

/**
 * Read KOS file.
 *
 * @version @author
 * 1.0.0    oyesanyf
 * 1.1.0    Wendy Zhu
 *
 */
public class RetrieveDocumentSet {
    public int NumOfDocs;
    private ArrayList<String> docList;
    private List<DocumentInfo> docInfoList;
    Properties props = new Properties();
    private static LogProvider lp;
    private String logPropsPath;
    private String r2Logger;
    private int docsreturn = 0;
    private String RegistryURL;
    private String RepositoryUniqueID;
    private String AssigningAuthorityUniversalId;
    private String AssigningAuthorityUniversalIdType;
    private String RepositoryURL;
    private String HomeCommunityId;
    private String tempdir;
    private String imagedir;
    private String reportdir;
    private String source;
    private String destination;

    public RetrieveDocumentSet() throws FileNotFoundException {
        try {
            props.load(new FileInputStream(Configuration.PROPERTYFILE));
            
            RegistryURL = props.getProperty("RegistryURL");
            RepositoryURL = props.getProperty("RepositoryURL");
            RepositoryUniqueID = props.getProperty("RepositoryUniqueID");
            AssigningAuthorityUniversalId = props.getProperty("AssigningAuthorityUniversalId");
            AssigningAuthorityUniversalIdType = props.getProperty("AssigningAuthorityUniversalIdType");            
            HomeCommunityId = props.getProperty("HomeCommunityId");

            tempdir = props.getProperty("tempdir");
            source = tempdir;
            destination = props.getProperty("imagedir");
            reportdir = props.getProperty("reportdir");

            logPropsPath = props.getProperty("logPropsPath");
            r2Logger = props.getProperty("r2Logger");
            LogProvider.init(logPropsPath, r2Logger);
            lp = LogProvider.getInstance();            

            NumOfDocs = 0;
        } catch (Exception e) {
            lp.getLog().debug(e.getMessage());
       }
    }
    
    public List<DocumentInfo> RetrieveDocuments(String RsnaPatientID) throws IOException, Exception {
        docList = new ArrayList<String>();
        docInfoList = new ArrayList<DocumentInfo>();
        NumOfDocs = 0;
        
        try {            
            lp.getLog().debug("Logging started iti18");

            if (RsnaPatientID.length() < 1) {
                lp.getLog().debug("Input XML values can not be null!");
                throw new Exception("A value is required for all XML fields");
            }
            
            //Get DocumentUniqueIDs
            ITI18 query18 = new ITI18();
            ITI18DataType input18 = new ITI18DataType();            
            
            input18.setRegistryURL(RegistryURL.trim());
            System.out.println(RegistryURL);
            input18.setRepositoryUniqueID(RepositoryUniqueID.trim());
            input18.setAssigningAuthorityUniversalId(AssigningAuthorityUniversalId.trim());
            input18.setAssigningAuthorityUniversalIdType(AssigningAuthorityUniversalIdType.trim());
            input18.setPatientID(RsnaPatientID);            

            docList = query18.queryDocuments(input18);
            if (docList == null) {
                lp.getLog().debug("No documents returned for ID " + RsnaPatientID);
                throw new Exception("No documents returned for ID " + RsnaPatientID);
            }
            
            //Retrieve documents by DocumentUniqueIDs (KOS&Report)
            ITI43 query43 = new ITI43();
            ITI43DataType input43 = new ITI43DataType();
            
            input43.setRepositoryURL(RepositoryURL.trim());
            input43.setRepositoryUniqueId(RepositoryUniqueID.trim());
            input43.setHomeCommunityId(HomeCommunityId.trim());
            File dirTemp = new File(tempdir);
            input43.setDownloadDIR(dirTemp.getAbsolutePath().toString());

            Iterator<String> itr = docList.iterator();
            while (itr.hasNext()) {
                String element = itr.next();
                lp.getLog().debug("getting document with id " + element);
                input43.setDocumentUniqueId(element);
                dirTemp = query43.queryDocuments(input43, RsnaPatientID);
                String downloadedFile = dirTemp.getAbsolutePath().toString();
                if (downloadedFile != null) {
                    try {
                        DocumentInfo docInfo = ProcessFile(downloadedFile, RsnaPatientID, element);
                        if (docInfo != null) {
                            docInfoList.add(docInfo);
                        }
                        lp.getLog().debug("retuning  document " + element + "for token " + RsnaPatientID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    NumOfDocs++;
                }
            }
        } catch (Exception e) {
            lp.getLog().debug(e.getMessage());
       }
        return docInfoList;
    }
    
    private DocumentInfo ProcessFile(String fileName, String rsnaPatientID, String documentUniqueID) throws IOException, Exception {
        DocumentInfo docInfo = null;
        String newFname;
        String mimeType;

        File fs = new File(fileName);
        File fd = new File(destination);        

        //  check if source directory is there
        if (!fs.exists()) {
            System.out.println("Source Directory or File doesn't exist:" + fs);
            lp.getLog().error("CopyDocumentFiles: Source Directory or File doesn't exist " + fs);
            throw new Exception("CopyDocumentFiles: Source Directory or File doesn't exist " + fs);
        } else if (!fd.exists()) {
            System.out.println("Destination Directory or File doesn't exist:" + fd);
            lp.getLog().info("Destination Directory or File doesn't exist:" + fd);
            throw new Exception("CopyDocumentFiles: Destination Directory or File doesn't exist " + fd);
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
                String patientName = docInfo.getPatientName();
                String patientPath = destination + File.separatorChar + patientName;
                File patientDir = new File(patientPath);
                if (!patientDir.exists()) {
                    if (patientDir.mkdir()) {
                        lp.getLog().info("Created patient folder " + patientPath);
                    } else {
                        lp.getLog().error("Error on creating patient folder " + patientPath);
                        throw new Exception("Error on creating patient folder " + patientPath);
                    }
                }

                //Create study folder under patient folder if not exist
                String studyInstanceUID = docInfo.getStudyInstanceUID();
                String studyPath = patientPath + File.separatorChar + studyInstanceUID;
                File studyDir = new File(studyPath);
                if (!studyDir.exists()) {
                    if (studyDir.mkdir()) {
                        lp.getLog().info("Created study folder " + studyPath);
                    } else {
                        lp.getLog().error("Error on creating study folder " + studyPath);
                        throw new Exception("Error on creating study folder " + studyPath);
                    }
                }

                //Store KOS File
                String kosFilePath   = studyPath + File.separatorChar ;
                newFname =  kosFilePath + File.separatorChar + "KOS.dcm";
                fd = new File(newFname);
                if (fd.exists()) {
                    lp.getLog().error(newFname + " already exists!");
                }
                else {
                    CopyFile(fs, fd);
                    lp.getLog().info("Moved " + fileName + "to directory " + studyPath);

                    if (!fs.delete()) {
                        lp.getLog().error("Error in deleting KOS file");
                    }
                }
            }
            else {
                //Store report file
                newFname = reportdir + File.separatorChar + fs.getName() + ".txt";
                fd = new File(newFname);
                try {
                    CopyFile(fs, fd);
                } catch (Exception e) {
                    lp.getLog().error("Error" + e.getMessage());
                }

                if (!fs.delete()) {
                    lp.getLog().error("Error in deleting report file");
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
