/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.retrievedocumentset;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.HashMap;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
/**
 *
 * @author oyesanyf
 */
public class CHGetDocumentSets {
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
    private String DownloadDIR;
    private String source;
    private String destination;

    public CHGetDocumentSets() throws FileNotFoundException {
        try {
            props.load(new FileInputStream("C://RSNA/config/rsna.properties"));
            logPropsPath = props.getProperty("logPropsPath");
            RegistryURL = props.getProperty("RegistryURL");
            RepositoryUniqueID = props.getProperty("RepositoryUniqueID");
            AssigningAuthorityUniversalId = props.getProperty("AssigningAuthorityUniversalId");
            AssigningAuthorityUniversalIdType = props.getProperty("AssigningAuthorityUniversalIdType");
            RepositoryURL = props.getProperty("RepositoryURL");
            HomeCommunityId = props.getProperty("HomeCommunityId");
            DownloadDIR = props.getProperty("DownloadDIR");
            
            r2Logger = props.getProperty("r2Logger");
            LogProvider.init(logPropsPath, r2Logger);
            lp = LogProvider.getInstance();

            source = props.getProperty("DownloadDIR");
            destination = props.getProperty("outgoingdir");

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
            File importDir = new File(DownloadDIR);
            input43.setDownloadDIR(importDir.getAbsolutePath().toString());

            Iterator<String> itr = docList.iterator();
            while (itr.hasNext()) {
                String element = itr.next();
                lp.getLog().debug("getting document with id " + element);
                input43.setDocumentUniqueId(element);
                importDir = query43.queryDocuments(input43, RsnaPatientID);
                String downloadedFile = importDir.getAbsolutePath().toString();
                if (downloadedFile != null) {
                    try {
                        DocumentInfo docInfo = ProcessFile(downloadedFile, RsnaPatientID, element);
                        if (docInfo != null) {
                            docInfoList.add(docInfo);
                        }
                        lp.getLog().debug("retuning  document "   +  element + "for patient " + RsnaPatientID);
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
    
    private DocumentInfo ProcessFile(String fileName, String rsnaID, String documentUniqueID) throws IOException, Exception {
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
                           
            //get list of files in source dir
            String rsnaIdPath = destination + File.separatorChar + rsnaID;
            File rsnaIdDir = new File(rsnaIdPath);
            if (!rsnaIdDir.exists()) {
                if (rsnaIdDir.mkdir()) {
                    System.out.println("Created filefolder " + rsnaIdPath);
                    lp.getLog().info("CopyFile: Created filefolder " + rsnaIdPath);
                } else {
                    System.out.println("Error creating filefolder " + rsnaIdPath);
                    lp.getLog().error("Exception in CopyFile: Error creating filefolder " + rsnaIdPath);
                    throw new Exception("CopyDocumentFiles: Error creating filefolder " + rsnaIdPath);
                }
            }

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
                String rsnaStudyPath;
                if (mimeType.equals("application/dicom-kos")) {
                    dis.close();
                    ReadKOS readKOS = new ReadKOS();
                    docInfo = readKOS.listHeader(object, rsnaID, documentUniqueID);
                    String studyInstanceUID = docInfo.getStudyInstanceUID();

                    rsnaStudyPath = rsnaIdPath + File.separatorChar + studyInstanceUID;
                    String kosFilePath   = rsnaStudyPath + File.separatorChar ;
                    File rsnaStudyDir = new File(rsnaStudyPath);
                    if (!rsnaStudyDir.exists()) {
                        if (rsnaStudyDir.mkdir()) {
                            System.out.println("Created filefolder " + rsnaStudyPath);
                            lp.getLog().info("CopyDocumentFiles: Created filefolder " + rsnaStudyPath);
                        } else {
                            System.out.println("Error creating filefolder " + rsnaStudyPath);
                            lp.getLog().error("Exception in CopyFile: Error creating filefolder " + rsnaStudyPath);
                            throw new Exception("CopyDocumentFiles: Error creating filefolder " + rsnaStudyPath);
                        }
                    }

                    newFname =  kosFilePath + File.separatorChar + "KOS.dcm";
                    fd = new File(newFname);
                    if (fd.exists()) {
                        lp.getLog().error(newFname + " already exists!");
                    }
                    else {
                        CopyFile(fs, fd);
                        System.out.println("Moved " + fileName + " to directory " + rsnaStudyPath);
                        lp.getLog().info("Moved " + fileName + "to directory " + rsnaStudyPath);

                        if (!fs.delete()) {
                            lp.getLog().error("Error in deleting KOS file");
                        }
                    }
                }
                else {

                    newFname = rsnaIdPath + File.separatorChar + fs.getName() + ".txt";
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

    public int RetrieveImages(DocumentInfo docInfo, String rsnaID, String studyInstanceUID) throws IOException, Exception {
        Rad69ServiceDataType input;
        String seriesInstanceUID;
        String sopInstanceUID;
        ArrayList<String> seriesInstanceUIDList;
        ArrayList<String> sopInstanceUIDList;
        HashMap<String,ArrayList<String>> images;
        int numOfDocs = 0 ;
        try {

            lp.getLog().debug("Logging started RsnaRetrieveImagingDocument");

            if (rsnaID.length()  < 1   ||  studyInstanceUID.length()  < 1   )  {
                lp.getLog().debug("Input XML can not be null values, a value is required for all XML fields");
                throw new Exception("A value is required for all XML fields");
            }

            //Get studies list for rsnaID
            String studyDir = DownloadDIR + File.separatorChar + rsnaID + File.separatorChar + studyInstanceUID;
            //String kosFileName = studyDir + File.separatorChar + "KOS.dcm";
            
            input = new Rad69ServiceDataType();
            input.setRsnaUID(rsnaID);
            input.setStudyInstanceUID(studyInstanceUID);

            seriesInstanceUIDList = docInfo.getSeriesInstanceUIDList();
            images = docInfo.getImages();

            if (seriesInstanceUIDList != null)
            {
                Iterator seriesItr = seriesInstanceUIDList.iterator();
                while (seriesItr.hasNext()){
                    seriesInstanceUID = (String) seriesItr.next();
                    input.setSeriesInstanceUID(seriesInstanceUID);

                    sopInstanceUIDList = (ArrayList<String>) images.get(seriesInstanceUID);
                    if (sopInstanceUIDList != null)
                    {
                        Iterator imagesItr = sopInstanceUIDList.iterator();
                        while (imagesItr.hasNext()){
                            sopInstanceUID = (String) imagesItr.next();
                            input.setDocumentUniqueId(sopInstanceUID);
                            int status = RetrieveDocument.retrieveImage(input);
                            numOfDocs++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            lp.getLog().debug("Error for  " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + e.getMessage());
        }
        return numOfDocs ;
    }
}
