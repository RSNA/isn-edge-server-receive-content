/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.retrievedocumentset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.openhealthtools.ihe.xds.document.Document;
//import org.openhealthtools.ihe.xds.response.XDSQueryResponseType;
import org.openhealthtools.ihe.xds.response.XDSResponseType;

import org.eclipse.ohf.ihe.atna.agent.AtnaAgentFactory;

/**
 * @author Jaroslaw Krych (stubs), Lawrence Tarbox (OHT implementation)
 *
 */
public class ITI43 {

    //private XDSQueryResponseType responseDetails;
    //private ArrayList<String> docList;
    private File destFile;
    private Document document;

    public File queryDocuments(ITI43DataType    input, String rsnaPatiendID ) throws MalformedStoredQueryException, AxisFault, URISyntaxException, FileNotFoundException, IOException {

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
            e3.printStackTrace();
        }
        System.out.println("URI of the XDS Registry - " + repositoryURI.toString());

        B_Consumer c = new B_Consumer(repositoryURI);

        AtnaAgentFactory.getAtnaAgent().setDoAudit(false);

        CX patientId = Hl7v2Factory.eINSTANCE.createCX();

        patientId.setAssigningAuthorityUniversalId(assigningAuthorityUniversalId);
        patientId.setAssigningAuthorityUniversalIdType(assigningAuthorityUniversalIdType);
        patientId.setIdNumber(patiendID);
       
        URI XDS_B_REPOSITORY_URI = null;

        c.getRepositoryMap().put(repositoryUniqueId, repositoryURI);
        
        RetrieveDocumentSetRequestType retrieveRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createRetrieveDocumentSetRequestType();
        DocumentRequestType documentRequest = org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveFactory.eINSTANCE.createDocumentRequestType();
        documentRequest.setRepositoryUniqueId(repositoryUniqueId);
  //    documentRequest.setHomeCommunityId(HomeCommunityId);
        documentRequest.setDocumentUniqueId(documentUniqueId);

        retrieveRequest.getDocumentRequest().add(documentRequest);
	List documents = new ArrayList();
	XDSResponseType response = null;
	try {
            response = c.retrieveDocumentSet(retrieveRequest, documents,null);
	} catch (Exception e) {
            e.printStackTrace();
            return null;
	}

        if(documents.size() > 0){
            document = (Document)documents.get(0);
            System.out.println("First document returned: " + document.toString());
	}
        
        File importDir = new File(downloadDIR);
	File inputDir = null;
	try {
            inputDir = File.createTempFile(rsnaPatiendID+"RSNA","", importDir);
	} catch (IOException e) {
            e.printStackTrace();
	}

        importDir = inputDir;
	inputDir.delete();
	destFile = importDir;
	OutputStream out = new FileOutputStream(destFile);
	InputStream documentStream = document.getDocumentData();
	      
	byte[] buf = new byte[1024];
	int len;
	try {
            while ((len = documentStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            documentStream.close();
	    out.close();
	} catch (IOException e) {
            e.printStackTrace();
	}

        return destFile;		
    }
}



      



