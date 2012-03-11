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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import org.apache.log4j.Logger;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
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
 * @version @author         Notes
 *          oyesanyf
 * 1.0.0    Wendy Zhu       Retrieve images by series
 *
 */
public class RetrieveDocuments {

    private static final Logger logger = Logger.getLogger(RetrieveDocuments.class);
    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet;
    private static DataHandler dh;
    private static FileOutputStream fos;
   // private static int totDocs;
    private static String rsnaID;
    private static String studyInstanceUID;
    private static String seriesInstanceUID;
    private static String transferSyntaxUID;
    private static String documentUniqueId;
    private static String status;

    public static int RetrieveStudy (DocumentInfo input) throws Exception {
        //One study per input
        int numOfDocs = 0;
        int totalDocs = 0;
        boolean radDir = false;

        RetrieveImagingDocumentSetRequestType dsRequest;
        StudyRequest studyRequest;
        List<StudyRequest> studyRequestList;
        SeriesRequest seriesRequest;
        List<SeriesRequest> seriesRequestList;
        DocumentRequest docRequest;
        List<DocumentRequest> documentRequestList;
        ArrayList<String> sopInstanceUIDList;

        RetrieveDocumentSetResponseType dsResponse;
        List<DocumentResponse> responseList;
        DocumentResponse documentResponse;
        RegistryErrorList errList;

        try {
            rsnaID = input.getRsnaID();            
            studyInstanceUID = input.getStudyInstanceUID();
            transferSyntaxUID = input.getTransferSyntaxUID();
                        
            TransferSyntaxUIDList transferList = new TransferSyntaxUIDList();
            List<String> transferSyntaxUIDs = transferList.getTransferSyntaxUID();
            transferSyntaxUIDs.add(transferSyntaxUID);            
              
            ArrayList<String> seriesInstanceUIDList = input.getSeriesInstanceUIDList();
            if (seriesInstanceUIDList != null) {
                //Generate Rad69 request meta data dsRequest per Series
                Iterator seriesItr = seriesInstanceUIDList.iterator();
                while (seriesItr.hasNext()){
                    seriesInstanceUID = (String) seriesItr.next();

                    dsRequest = new RetrieveImagingDocumentSetRequestType();
                    dsRequest.setTransferSyntaxUIDList(transferList);

                    studyRequest = new StudyRequest();
                    studyRequest.setStudyInstanceUID(studyInstanceUID);
                    studyRequestList = dsRequest.getStudyRequest();
                    studyRequestList.add(studyRequest);

                    seriesRequest = new SeriesRequest();
                    seriesRequest.setSeriesInstanceUID(seriesInstanceUID);
                    seriesRequestList = studyRequest.getSeriesRequest();
                    seriesRequestList.add(seriesRequest);

                    documentRequestList = seriesRequest.getDocumentRequest();
                    sopInstanceUIDList = (ArrayList<String>) input.getImages().get(seriesInstanceUID);
                    if (sopInstanceUIDList != null) {
                        Iterator imagesItr = sopInstanceUIDList.iterator();
                        while (imagesItr.hasNext()){
                            documentUniqueId = (String) imagesItr.next();
                            docRequest = new DocumentRequest();
                            docRequest.setDocumentUniqueId(documentUniqueId);
                            docRequest.setHomeCommunityId(Configuration.HomeCommunityId);
                            docRequest.setRepositoryUniqueId(Configuration.RepositoryUniqueID);
                            documentRequestList.add(docRequest);
                        }
                        
                        //Generate Rad69 response meta data dsResponse
                        dsResponse = new RetrieveDocumentSetResponseType();
                        try {
                            dsResponse = imagingDocumentSourceRetrieveImagingDocumentSet(dsRequest);
                        } catch (Exception e) {
                            logger.info("No image returned for ID " + rsnaID + " for studyInstanceUID " + studyInstanceUID);
                        }

                        //Parse Rad69 response
                        responseList = dsResponse.getDocumentResponse();
                        errList = new RegistryErrorList();

                        if (responseList.isEmpty()) {
                            status = dsResponse.getRegistryResponse().getStatus();
                            errList = dsResponse.getRegistryResponse().getRegistryErrorList();

                            String err = errList.getHighestSeverity();
                            System.out.println("NO DOCUMENTS FOUND " + status);
                            logger.info("registry respose for this query for " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + status);

                            throw new Exception("No documents returned ");
                        }

                        numOfDocs = responseList.size();
                        totalDocs += numOfDocs;
                        System.out.println("Number of docs: " + numOfDocs);
                        logger.info("Number of images retuened for " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + numOfDocs);

                        documentResponse = new DocumentResponse();
                        String imagePath = Configuration.getImageDir().toString()  + File.separatorChar + input.getPatientName() + File.separatorChar + studyInstanceUID;
                        for (int i = 0; i < numOfDocs; i++) {
                            documentResponse = responseList.get(i);
                            //String cID = documentresponse.getHomeCommunityId();
                            byte[] document = documentResponse.getDocument();

                            DataSource dataSource = new ByteArrayDataSource(document, "application/octet-stream");
                            dh = new DataHandler(dataSource);

                            File outFile = new File(imagePath);
                            if (!outFile.exists()) {
                              radDir = outFile.mkdirs();
                            }

                            String filename = documentResponse.getDocumentUniqueId();
                            File dcmFile = null;
                            try {
                                dcmFile = new File(outFile, filename + ".dcm");
                            } catch (Exception e) {
                                logger.error("Error for  " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + e.getMessage());
                            }

                            fos = new FileOutputStream(dcmFile);
                            dh.writeTo(fos);
                            fos.close();
                        }
                    }
                }
            }
            //RegistryResponseType registryResponse = new RegistryResponseType();
        } catch (Exception e) {
            logger.error("Error for  " + rsnaID + " for studyInstanceUID " + studyInstanceUID + " is " + e.getMessage());
        }
        return totalDocs;
    }

    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet(org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType body) throws Exception {

        try {
            org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourceService service = new org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourceService();
            MTOMFeature feature = new MTOMFeature();
            org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourcePortType port = service.getImagingDocumentSourcePortSoap12(feature);
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size", 4096);

        //ctxt.put(com.sun.xml.internal.ws.developer.JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 1048576);
            imagingDocumentSourceRetrieveImagingDocumentSet = port.imagingDocumentSourceRetrieveImagingDocumentSet(body);
        } catch (Exception e) {
             logger.error("Error for is " + e.getMessage());
        }

        return imagingDocumentSourceRetrieveImagingDocumentSet;
    }
}
