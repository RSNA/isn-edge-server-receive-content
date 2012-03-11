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
 * 2.1.1    Wendy Zhu       Retrieve X number of images per request
 *
 */
public class RetrieveDocuments {

    private static final Logger logger = Logger.getLogger(RetrieveDocuments.class);
    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet;

    public static int RetrieveStudy (DocumentInfo input) throws Exception {
        //One study per input
        int imageCounter = 0;
        int imagesOfStudy = 0;
        DataHandler dh;
        FileOutputStream fos;
        String studyInstanceUID = "";
        String seriesInstanceUID;
        String transferSyntaxUID;
        String documentUniqueId;
        String status;
    
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
        //RegistryErrorList errList;

        try {
            studyInstanceUID = input.getStudyInstanceUID();
            transferSyntaxUID = input.getTransferSyntaxUID();
            logger.info("***Start to retrieve study of [" + studyInstanceUID + "]");

            String studyFolderName = "UNKNOWN";
            if (input.getStudyDescription() != null)
                studyFolderName = input.getStudyDescription().replaceAll("[^a-zA-Z0-9]+", "_");
            if (input.getStudyDate() != null)
                studyFolderName = studyFolderName + "-" + input.getStudyDate();
            if (input.getStudyTime() != null)
                studyFolderName = studyFolderName + "-" + studyInstanceUID;
            //Max folder name is 256
            if (studyFolderName.length() > 256)
            {
                studyFolderName = studyFolderName.substring(0, 255);
            }
                
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
                        int imagesBySeries = sopInstanceUIDList.size();
                        imagesOfStudy += imagesBySeries;
                        int totalCounter = 0;                        
                        int requestCounter = 0;
                        Iterator imagesItr = sopInstanceUIDList.iterator();
                        
                        /*
                         * Send request with X number of images each time                         * 
                         */                        
                        while (imagesItr.hasNext()){
                            documentUniqueId = (String) imagesItr.next();
                            totalCounter = totalCounter + 1;
                            requestCounter = requestCounter + 1;
                            
                            /*
                             * Add image into dsRequest/studyRequestList/seriesRequestList/documentRequestList
                             * Send the request when accumulate X number of images
                             * After send, clear the documentRequestList
                             */
                            docRequest = new DocumentRequest();
                            docRequest.setDocumentUniqueId(documentUniqueId);
                            docRequest.setHomeCommunityId(Configuration.HomeCommunityId);
                            docRequest.setRepositoryUniqueId(Configuration.RepositoryUniqueID);
                            documentRequestList.add(docRequest);
                            
                            if (requestCounter >= Configuration.ImagesPerRequest || totalCounter >= imagesBySeries)
                            {
                               /*
                                * Generate Rad69 response meta data dsResponse
                                * Send the request and save the retrieved images
                                */
                                //dsResponse = new RetrieveDocumentSetResponseType();
                                dsResponse = imagingDocumentSourceRetrieveImagingDocumentSet(dsRequest);
                                responseList = dsResponse.getDocumentResponse();

                                if (responseList.isEmpty()) {
                                    status = dsResponse.getRegistryResponse().getStatus();
                                    System.out.println("NO DOCUMENTS FOUND " + status);
                                    logger.info("registry respose for this query is " + status);
                                }
                                else
                                {
                                    int returnedDocs = responseList.size();
                                    imageCounter += returnedDocs;

                                    documentResponse = new DocumentResponse();
                                    String imagePath = Configuration.getImageDir().toString()  + File.separatorChar 
                                            + input.getPatientName().replaceAll("[^a-zA-Z0-9]+", "_") + File.separatorChar + studyFolderName;
                                    for (int i = 0; i < returnedDocs; i++) {
                                        documentResponse = responseList.get(i);
                                        byte[] document = documentResponse.getDocument();

                                        DataSource dataSource = new ByteArrayDataSource(document, "application/octet-stream");
                                        dh = new DataHandler(dataSource);

                                        File outFile = new File(imagePath);
                                        if (!outFile.exists()) {
                                            outFile.mkdirs();
                                        }

                                        String filename = documentResponse.getDocumentUniqueId();
                                        File dcmFile = null;
                                        try {
                                            dcmFile = new File(outFile, filename + ".dcm");
                                        } catch (Exception e) {
                                            logger.error("Error on  " + filename + ": " + e.getMessage());
                                        }

                                        fos = new FileOutputStream(dcmFile);
                                        dh.writeTo(fos);
                                        fos.close();
                                    }  
                                    
                                    /*
                                     * Clean up the request counter and image request list
                                     */
                                    requestCounter = 0;
                                    documentRequestList.clear();
                                }                              
                            }                            
                        }                    
                    }
                }
            }
            logger.info("***Complete : " + imageCounter + " out of " + imagesOfStudy + " images were retrieved");
        } catch (Exception e) {
            logger.error("Error for studyUID[" + studyInstanceUID + "] is " + e.getMessage());
        }
        return imageCounter;
    }

    private static RetrieveDocumentSetResponseType imagingDocumentSourceRetrieveImagingDocumentSet(org.rsna.isn.retrievecontent.rad69.RetrieveImagingDocumentSetRequestType body) throws Exception {

        try {
            org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourceService service = new org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourceService();
            MTOMFeature feature = new MTOMFeature();
            org.rsna.isn.retrievecontent.rad69.ImagingDocumentSourcePortType port = service.getImagingDocumentSourcePortSoap12(feature);
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size", 8096);
        //ctxt.put(com.sun.xml.internal.ws.developer.JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 1048576);
            imagingDocumentSourceRetrieveImagingDocumentSet = port.imagingDocumentSourceRetrieveImagingDocumentSet(body);
        } catch (Exception e) {
             logger.error("Error for is " + e.getMessage());
        }

        return imagingDocumentSourceRetrieveImagingDocumentSet;
    }
}
