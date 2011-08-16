/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import org.openhealthtools.ihe.xds.consumer.storedquery.FindDocumentsQuery;
import org.openhealthtools.ihe.xds.consumer.storedquery.GetDocumentsQuery;
import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.metadata.AvailabilityStatusType;
import org.openhealthtools.ihe.xds.metadata.CodedMetadataType;
import org.openhealthtools.ihe.xds.metadata.DocumentEntryType;
import org.openhealthtools.ihe.xds.metadata.MetadataFactory;
import org.openhealthtools.ihe.xds.response.DocumentEntryResponseType;
import org.openhealthtools.ihe.xds.response.XDSQueryResponseType;
import org.openhealthtools.ihe.xds.consumer.query.DateTimeRange;
import org.eclipse.ohf.ihe.atna.agent.AtnaAgentFactory;

/**
 * @author Jaroslaw Krych (stubs), Lawrence Tarbox (OHT implementation)
 *
 */
public class ITI18 {

    private static final Logger logger = Logger.getLogger(ITI18.class);
    private XDSQueryResponseType responseDetails;
    private ArrayList<String> docList;
    private  DateTimeRange[] creationTimeRange = null;
    private HashMap hm;

    public ArrayList queryDocuments(ITI18DataType input) throws MalformedStoredQueryException, AxisFault, URISyntaxException, Exception {

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

            AtnaAgentFactory.getAtnaAgent().setDoAudit(false);

            CX patientId = Hl7v2Factory.eINSTANCE.createCX();
            patientId.setAssigningAuthorityUniversalId(AssigningAuthorityUniversalId);
            patientId.setAssigningAuthorityUniversalIdType(AssigningAuthorityUniversalIdType);
            patientId.setIdNumber(patientID);

            CodedMetadataType[] hcfc1 = {MetadataFactory.eINSTANCE.createCodedMetadataType()};
            hcfc1[0].setCode(codedMetadataType);

            AvailabilityStatusType[] status = new AvailabilityStatusType[]{AvailabilityStatusType.APPROVED_LITERAL} ;

            FindDocumentsQuery query = null;

            try {
                query = new FindDocumentsQuery(patientId, null, null, null, null, null, null, null, null, status);
            } catch (Exception e) {
                logger.error("iti18 - " + e.getMessage());
            }
      
            XDSQueryResponseType responseList = null;

            try {
                responseList = c.invokeStoredQuery(query, true);
            } catch (Exception e) {
                logger.error("iti18 - " + e.getMessage());
            }
            if (responseList == null || responseList.getReferences().size() == 0) {
                throw new Exception("NO DOCUMENTS FOUND " + "for " + patientID);
            }

            int numOfDocs = responseList.getReferences().size();
            String[] docReferences = new String[numOfDocs];
            for (int i = 0; i < numOfDocs; i++) {
                ObjectRefType docReference = (ObjectRefType) responseList.getReferences().get(i);
                docReferences[i] = docReference.getId();
            }
            XDSQueryResponseType response;
            try {
                GetDocumentsQuery docsQuery = new GetDocumentsQuery(docReferences, true);
                response = c.invokeStoredQuery(docsQuery, false);
            } catch (MalformedStoredQueryException e) {
                logger.error("iti18 - " + e.getMessage());
                return null;
            } catch (Exception e) {
                logger.error("iti18 - " + e.getMessage());
                return null;
            }
            int numOfResponses = response.getDocumentEntryResponses().size();

            docList = new ArrayList<String>();
            hm = new HashMap();

            for (int i = 0; i < numOfResponses; i++) {
                DocumentEntryType docDetails = ((DocumentEntryResponseType) response.getDocumentEntryResponses().get(i)).getDocumentEntry();
                String id = docDetails.getUniqueId();
                docList.add(id) ;
            }
        } catch (Exception e) {
            logger.error("iti18 - " + e.getMessage());
        }

        return docList;
    }
}