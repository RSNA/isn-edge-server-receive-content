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

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.axis2.AxisFault;
import org.openhealthtools.ihe.common.ebxml._3._0.rim.ObjectRefType;
import org.openhealthtools.ihe.common.hl7v2.CX;
import org.openhealthtools.ihe.common.hl7v2.Hl7v2Factory;
import org.openhealthtools.ihe.xds.consumer.B_Consumer;
import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.metadata.AvailabilityStatusType;
import org.openhealthtools.ihe.xds.metadata.CodedMetadataType;
import org.openhealthtools.ihe.xds.metadata.DocumentEntryType;
import org.openhealthtools.ihe.xds.metadata.MetadataFactory;
import org.openhealthtools.ihe.xds.response.DocumentEntryResponseType;
import org.openhealthtools.ihe.xds.response.XDSQueryResponseType;
//import org.eclipse.ohf.ihe.atna.agent.AtnaAgentFactory;

/**
 * ITI-18 transactions with FindSubmissionSetsQuery and GetSubmissionSetAndContentQuery
 *
 * @version @author     Notes
 *          Jaroslaw Krych (stubs),Lawrence Tarbox (OHT implementation)
 * 1.0.0    oyesanyf    FindDocumentsQuery, GetDocumentsQuery
 * 1.1.0    Wendy Zhu   implement FindSubmissionSetsQuery and GetSubmissionSetAndContentQuery
 *
 */
public class ITI18 {

    private static final Logger logger = Logger.getLogger(ITI18.class);
    private HashMap<String, ArrayList<String>> docList;

    public HashMap<String, ArrayList<String>> queryDocuments(ITI18DataType input) throws MalformedStoredQueryException, AxisFault, URISyntaxException, Exception {

        try {
            String registryURL  = input.getRegistryURL();
            String XDS_B_REPOSITORY_UNIQUE_ID = input.getRepositoryUniqueID();
            String AssigningAuthorityUniversalId = input.getAssigningAuthorityUniversalId();
            String AssigningAuthorityUniversalIdType = input.getAssigningAuthorityUniversalIdType();
            String patientID = input.getPatientID();
            String codedMetadataType = input.getCodedMetadataType();
            String creationstartDate = input.getCreationTimeStartDate();
            String creationEndDate = input.getCreationTimeEndDate();
              
            URI registryURI = null;
            try {
                registryURI = new URI(registryURL);
            } catch (URISyntaxException e) {
                logger.error("iti18 - " + e.getMessage());
            }

            B_Consumer c = new B_Consumer(registryURI);
            c.getRepositoryMap().put(XDS_B_REPOSITORY_UNIQUE_ID, registryURI);

            //AtnaAgentFactory.getAtnaAgent().setDoAudit(false);

            CX patientId = Hl7v2Factory.eINSTANCE.createCX();
            patientId.setAssigningAuthorityUniversalId(AssigningAuthorityUniversalId);
            patientId.setAssigningAuthorityUniversalIdType(AssigningAuthorityUniversalIdType);
            patientId.setIdNumber(patientID);

            CodedMetadataType[] hcfc1 = {MetadataFactory.eINSTANCE.createCodedMetadataType()};
            hcfc1[0].setCode(codedMetadataType);

            AvailabilityStatusType[] status = new AvailabilityStatusType[]{AvailabilityStatusType.APPROVED_LITERAL} ;

            //Get SubmissionSets
            docList = new HashMap<String, ArrayList<String>>();
            FindSubmissionSetsQuery qSubmission = new FindSubmissionSetsQuery(patientId, status);
            XDSQueryResponseType resSubmission = null;
            try {
                resSubmission = c.invokeStoredQuery(qSubmission, true);
                logger.info(resSubmission.getSubmissionSetResponses().size());

                int numOfsets = resSubmission.getReferences().size();
                logger.info("iti18-FindSubmissionSets-" + numOfsets + " submission sets were found");
                for (int i=0; i < numOfsets; i++)
                {
                    ArrayList<String> docIDs=new ArrayList<String>();
                    ObjectRefType setRef = (ObjectRefType) resSubmission.getReferences().get(i);
                    String submissionSetID = setRef.getId();
                    logger.info("iti18-FindSubmissionSets-" + submissionSetID);

                    //Get SubmissionSets
                    GetSubmissionSetAndContentsQuery qGetSubmission = null;
                    XDSQueryResponseType resGetSubmission = null;
                    try{
                        qGetSubmission = new GetSubmissionSetAndContentsQuery(submissionSetID, true);
                        resGetSubmission = c.invokeStoredQuery(qGetSubmission, false);

                        int numOfdocs = resGetSubmission.getDocumentEntryResponses().size();
                        for (int n = 0; n<numOfdocs; n++)
                        {
                            DocumentEntryType docDetail = ((DocumentEntryResponseType) resGetSubmission.getDocumentEntryResponses().get(n)).getDocumentEntry();
                            String docID = docDetail.getUniqueId();
                            docIDs.add(docID);
                            logger.info("Find docID#[" + docID + "] under SubmissionSet#" + submissionSetID);
                        }
                    } catch (Exception e) {
                        logger.error("iti18-GetSubmissionSetAndContents- " + e.getMessage());
                    }
                    docList.put(submissionSetID, docIDs);
                }                
            } catch (Exception e) {
                logger.error("iti18-FindSubmissionSets- " + e.getMessage());
            }
        } catch (Exception e) {
            logger.error("iti18 - " + e.getMessage());
        }

        return docList;
    }
}