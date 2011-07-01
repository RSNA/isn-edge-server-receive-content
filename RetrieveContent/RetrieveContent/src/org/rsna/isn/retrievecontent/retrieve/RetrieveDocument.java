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
import org.rsna.isn.retrievecontent.rad69.RegistryErrorList;
import org.rsna.isn.retrievecontent.rad69.RegistryResponseType;
import org.rsna.isn.retrievecontent.rad69.RetrieveDocumentSetRequestType.DocumentRequest;
import org.rsna.isn.retrievecontent.rad69.RetrieveDocumentSetResponseType;
import org.rsna.isn.retrievecontent.rad69.RetrieveDocumentSetResponseType.DocumentResponse;
import org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType;
import org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType.StudyRequest;
import org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType.StudyRequest.SeriesRequest;
import org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList;

/**
 * Retrieve image.
 *
 * @version @author
 * 1.0.0    oyesanyf
 * 1.0.0    Wendy Zhu
 *
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

    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet(org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType body) throws Exception {

        try {
            org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourceService service = new org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourceService();
            MTOMFeature feature = new MTOMFeature();
            org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourcePortType port = service.getImagingDocumentSourcePortSoap12(feature);
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
