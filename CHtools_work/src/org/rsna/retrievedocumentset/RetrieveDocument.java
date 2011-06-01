/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.retrievedocumentset;

//import com.sun.xml.internal.ws.developer.JAXWSProperties;
//import org.rsna.retrievedocumentset.Rad69ServiceDataType;
//import com.sun.xml.ws.developer.JAXWSProperties;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataHandler;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import org.rsna.getimaging.rad69.RegistryErrorList;
import org.rsna.getimaging.rad69.RegistryResponseType;
import org.rsna.getimaging.rad69.RetrieveDocumentSetRequestType.DocumentRequest;
import org.rsna.getimaging.rad69.RetrieveDocumentSetResponseType;
import org.rsna.getimaging.rad69.RetrieveDocumentSetResponseType.DocumentResponse;
import org.rsna.getimaging.rad69.RetrieveImagingDocumentSetRequestType;
import org.rsna.getimaging.rad69.RetrieveImagingDocumentSetRequestType.StudyRequest;
import org.rsna.getimaging.rad69.RetrieveImagingDocumentSetRequestType.StudyRequest.SeriesRequest;
import org.rsna.getimaging.rad69.RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList;
//import org.rsna.getimagingdocument.LogProvider;


/**
 *
 * @author oyesanyf
 */
public class RetrieveDocument {

    private static Properties props = new Properties();
    private static  String receiveimagePropsPath;
    private static  String r2Logger;
    private static LogProvider lp;
    //private static File destFile;
    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet;
    private static DataHandler dh;
    private static FileOutputStream fos;
   // private static int totDocs;
    private static String rsnaID;
    private static  String studyInstanceUID;
    private static  String seriesInstanceUID;
    private static String transferSyntaxUID;
    private static String documentUniqueId;
    private static String status;
    private static int numOfDocs;
    private static boolean radDir;

    public  static  int retrieveImage (Rad69ServiceDataType input) throws Exception {
        try {

            numOfDocs = 0 ;
            props.load(new FileInputStream("c:/rsna/config/rsna.properties"));
            receiveimagePropsPath = props.getProperty("receiveimagePropsPath");

            String repositoryUniqueID = props.getProperty("RepositoryUniqueID");
            String homeCommunityId = props.getProperty("HomeCommunityId");
            String receiveimageDir = props.getProperty("outgoingdir");
            r2Logger = props.getProperty("receiveimagelog");
            LogProvider.init(receiveimagePropsPath, r2Logger);
            lp = LogProvider.getInstance();
            lp.getLog().debug("Logging started GetRad69ToFile");

            rsnaID = input.getRsnaUID();
            seriesInstanceUID = input.getSeriesInstanceUID();
            studyInstanceUID = input.getStudyInstanceUID();
            transferSyntaxUID = input.getTransferSyntaxUID();
            documentUniqueId = input.getDocumentUniqueId();

            RetrieveImagingDocumentSetRequestType r = new RetrieveImagingDocumentSetRequestType();
            SeriesRequest series = new SeriesRequest();
            ArrayList<String> seriesArrayList = new ArrayList<String>();
            series.setSeriesInstanceUID(seriesInstanceUID);

            StudyRequest studyrq = new StudyRequest();
            studyrq.setStudyInstanceUID(studyInstanceUID);

            List<SeriesRequest> seriesRequestList = studyrq.getSeriesRequest();
            seriesRequestList.add(series);

            List<StudyRequest> studyRequestList = r.getStudyRequest();
            studyRequestList.add(studyrq);

            TransferSyntaxUIDList transferList = new TransferSyntaxUIDList();
            List<String> transferSyntaxUIDs = transferList.getTransferSyntaxUID();
            transferSyntaxUIDs.add(transferSyntaxUID);

            r.setTransferSyntaxUIDList(transferList);
            DocumentRequest d = new DocumentRequest();
            List<DocumentRequest> documentRequestList = series.getDocumentRequest();

            d.setDocumentUniqueId(documentUniqueId);
            d.setHomeCommunityId(homeCommunityId);
            d.setRepositoryUniqueId(repositoryUniqueID);
            documentRequestList.add(d);

            RetrieveDocumentSetResponseType response = new RetrieveDocumentSetResponseType();
            DocumentResponse documentresponse = new DocumentResponse();
            try {
                response = imagingDocumentSourceRetrieveImagingDocumentSet(r);
            } catch (Exception e) {
                System.out.println("Find query execution failed");
                lp.getLog().debug("No image returned for ID " + rsnaID + " for studyInstanceUID " + studyInstanceUID);
            }

            List<DocumentResponse> responseList = response.getDocumentResponse();
            RegistryResponseType registryresponse = new RegistryResponseType();
            RegistryErrorList errList = new RegistryErrorList();
            if (responseList.isEmpty()) {
                status = response.getRegistryResponse().getStatus();
                errList = response.getRegistryResponse().getRegistryErrorList();
                String err = errList.getHighestSeverity();
                System.out.println("NO DOCUMENTS FOUND " + status);
                lp.getLog().debug("registry respose for this query for " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + status);

                throw new Exception("No documents returned ");
            }
            numOfDocs = responseList.size();
            System.out.println("Number of docs: " + numOfDocs);
            lp.getLog().debug("Number of images retuened for " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + numOfDocs);

            for (int i = 0; i < numOfDocs; i++) {
                documentresponse = responseList.get(i);
                String cID = documentresponse.getHomeCommunityId();
                byte[] document = documentresponse.getDocument();

                DataSource dataSource = new ByteArrayDataSource(document, "application/octet-stream");
                dh = new DataHandler(dataSource);

                File outFile = new File(receiveimageDir + File.separatorChar + rsnaID + File.separatorChar + studyInstanceUID + File.separatorChar + File.separatorChar + seriesInstanceUID);

                if (!outFile.exists()) {
                  radDir = outFile.mkdirs();
                }

                if (radDir == false  || outFile.exists() ) {
                     lp.getLog().debug("Error:  unable to create directory or directory already exists "  + outFile);
                }
                String filename = documentUniqueId;

                File inputDir = null;
                try {
                    inputDir = new File(outFile, filename + ".dcm");
                } catch (Exception e) {
                    lp.getLog().debug("Error for  " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + e.getMessage());
                }

                fos = new FileOutputStream(inputDir);
                dh.writeTo(fos);
                fos.close();
            }
        } catch (Exception e) {
            lp.getLog().debug("Error for  " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + e.getMessage());
        }
        return numOfDocs;
    }

    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet(org.rsna.getimaging.rad69.RetrieveImagingDocumentSetRequestType body) throws Exception {

        try {

            org.rsna.getimaging.rad69.ImagingDocumentSourceService service = new org.rsna.getimaging.rad69.ImagingDocumentSourceService();
            MTOMFeature feature = new MTOMFeature();
            org.rsna.getimaging.rad69.ImagingDocumentSourcePortType port = service.getImagingDocumentSourcePortSoap12(feature);
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size", 1048576);
        //ctxt.put(com.sun.xml.internal.ws.developer.JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 1048576);
            imagingDocumentSourceRetrieveImagingDocumentSet = port.imagingDocumentSourceRetrieveImagingDocumentSet(body);
        } catch (Exception e) {
             lp.getLog().debug("Error for is " + e.getMessage());
        }

        return imagingDocumentSourceRetrieveImagingDocumentSet;
    }
}
