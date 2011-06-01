/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.retrievedocumentset;

import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
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

    private XDSQueryResponseType responseDetails;
    private ArrayList<String> docList;
    private  DateTimeRange[] creationTimeRange = null;
    private HashMap hm;
    Properties props = new Properties();
    private static LogProvider lp;
    private String logPropsPath;
    private String r2Logger;

    public   ArrayList  queryDocuments(ITI18DataType input) throws MalformedStoredQueryException, AxisFault, URISyntaxException, Exception {
   
        lp = LogProvider.getInstance();
        lp.getLog().error("Logging started. queryDocuments");

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
                lp.getLog().error(e.getMessage());
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
                lp.getLog().error(e.getMessage());
            }
      
            XDSQueryResponseType responseList = null;

            try {
                responseList = c.invokeStoredQuery(query, true);
            } catch (Exception e) {
                lp.getLog().debug(e.getMessage());
            }
            if (responseList == null || responseList.getReferences().size() == 0) {
                throw  new Exception("NO DOCUMENTS FOUND " +   "for "  +    patientID) ;
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
                lp.getLog().debug(e.getMessage());
                return null;
            } catch (Exception e) {
                lp.getLog().debug(e.getMessage());
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
            lp.getLog().error(e.getMessage());
        }

        return docList;
    }
}