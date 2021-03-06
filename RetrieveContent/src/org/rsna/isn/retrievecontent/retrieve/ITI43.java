/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.isn.retrievecontent.retrieve;

import java.io.*;
import org.apache.log4j.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.axis2.AxisFault;
import org.openhealthtools.ihe.common.hl7v2.CX;
import org.openhealthtools.ihe.common.hl7v2.Hl7v2Factory;
import org.openhealthtools.ihe.xds.consumer.B_Consumer;
import org.openhealthtools.ihe.xds.consumer.retrieve.DocumentRequestType;
import org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveDocumentSetRequestType;
import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.document.XDSDocument;
import org.openhealthtools.ihe.xds.response.XDSRetrieveResponseType;
import org.eclipse.ohf.ihe.atna.agent.AtnaAgentFactory;

/**
 * @author Jaroslaw Krych (stubs), Lawrence Tarbox (OHT implementation)
 * @author Wendy Zhu (RSNA NIBIB implementation)
 */
public class ITI43 {

    private static final Logger logger = Logger.getLogger(ITI43.class);
    //private XDSQueryResponseType responseDetails;
    //private ArrayList<String> docList;
    private File destFile;
    private XDSDocument document;

    public File queryDocuments(ITI43DataType input, String rsnaPatiendID ) throws MalformedStoredQueryException, AxisFault, URISyntaxException, FileNotFoundException, IOException {

        String repositoryURL  = input.getRepositoryURL();
        String assigningAuthorityUniversalId = input.getAssigningAuthorityUniversalId();
        String assigningAuthorityUniversalIdType = input.getAssigningAuthorityUniversalIdType();
        String patiendID = input.getPatientID();
        String  repositoryUniqueId = input.getRepositoryUniqueId();
        String documentUniqueId  = input.getDocumentUniqueId();
        String downloadDIR = input.getDownloadDIR();
       
        URI repositoryURI = null;
        try {
            repositoryURI = new URI(repositoryURL);
        } catch (URISyntaxException e3) {
            // TODO Auto-generated catch block
            logger.error("iti43 - " + e3.getMessage());
        }
        System.out.println("URI of the XDS Registry - " + repositoryURI.toString());

        B_Consumer c = new B_Consumer(repositoryURI);
        AtnaAgentFactory.getAtnaAgent().setDoAudit(false);
        CX patientId = Hl7v2Factory.eINSTANCE.createCX();

        patientId.setAssigningAuthorityUniversalId(assigningAuthorityUniversalId);
        patientId.setAssigningAuthorityUniversalIdType(assigningAuthorityUniversalIdType);
        patientId.setIdNumber(patiendID);

        c.getRepositoryMap().put(repositoryUniqueId, repositoryURI);
        
        RetrieveDocumentSetRequestType retrieveRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createRetrieveDocumentSetRequestType();
        DocumentRequestType documentRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createDocumentRequestType();
        documentRequest.setRepositoryUniqueId(repositoryUniqueId);
        documentRequest.setDocumentUniqueId(documentUniqueId);

        retrieveRequest.getDocumentRequest().add(documentRequest);
	List documents = new ArrayList();
	XDSRetrieveResponseType response = null;
	try {
            response = c.retrieveDocumentSet(retrieveRequest,patientId);
            documents = response.getAttachments();
	} catch (Exception e) {
            logger.error("iti43 - " + e.getMessage());
            return null;
	}

        if(documents.size() > 0){
            document = (XDSDocument)documents.get(0);
	}
        
        File importDir = new File(downloadDIR);
	File inputDir = null;
	try {
            inputDir = File.createTempFile(rsnaPatiendID+"RSNA","", importDir);
	} catch (IOException e) {
            logger.error("iti43 - " + e.getMessage());
	}

        importDir = inputDir;
	inputDir.delete();
	destFile = importDir;
	OutputStream out = new FileOutputStream(destFile);
	InputStream documentStream = document.getStream();
	      
	byte[] buf = new byte[1024];
	int len;
	try {
            while ((len = documentStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            documentStream.close();
	    out.close();
	} catch (IOException e) {
            logger.error("iti43 - " + e.getMessage());
	}

        return destFile;		
    }
}