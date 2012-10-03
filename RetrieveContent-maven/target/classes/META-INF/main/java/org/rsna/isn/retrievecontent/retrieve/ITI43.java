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
import org.apache.log4j.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.axis2.AxisFault;
import org.openhealthtools.ihe.atna.auditor.IHEAuditor;
import org.openhealthtools.ihe.common.ws.IHESOAP12Sender;
import org.apache.axis2.client.Options;
import org.openhealthtools.ihe.common.hl7v2.CX;
import org.openhealthtools.ihe.common.hl7v2.Hl7v2Factory;
import org.openhealthtools.ihe.xds.consumer.B_Consumer;
import org.openhealthtools.ihe.xds.consumer.retrieve.DocumentRequestType;
import org.openhealthtools.ihe.xds.consumer.retrieve.RetrieveDocumentSetRequestType;
import org.openhealthtools.ihe.xds.consumer.storedquery.MalformedStoredQueryException;
import org.openhealthtools.ihe.xds.document.XDSDocument;
import org.openhealthtools.ihe.xds.response.XDSRetrieveResponseType;
//import org.eclipse.ohf.ihe.atna.agent.AtnaAgentFactory;

/**
 *  ITI-43 transactions to retrieve KOS and report documents.
 *
 * @version @author     Notes
 *          Jaroslaw Krych (stubs),Lawrence Tarbox (OHT implementation)
 * 1.1.0    Wendy Zhu   Modified for RSNA NIBIB project
 *
 */
public class ITI43 {

    private static final Logger logger = Logger.getLogger(ITI43.class);
    //private XDSQueryResponseType responseDetails;
    //private ArrayList<String> docList;
    private File destFile;
    private XDSDocument document;
    
    static
    {
        IHEAuditor.getAuditor().getConfig().setAuditorEnabled(false);
    }
        
    public File queryDocuments(ITI43DataType input, String rsnaPatiendID ) throws MalformedStoredQueryException, AxisFault, URISyntaxException, FileNotFoundException, IOException {

        String repositoryURL  = input.getRepositoryURL();
        String assigningAuthorityUniversalId = input.getAssigningAuthorityUniversalId();
        String assigningAuthorityUniversalIdType = input.getAssigningAuthorityUniversalIdType();
        String patiendID = input.getPatientID();
        String repositoryUniqueId = input.getRepositoryUniqueId();
        String documentUniqueId  = input.getDocumentUniqueId();
        String downloadDIR = input.getDownloadDIR();
        URI repositoryURI = null;
        try {
            repositoryURI = new URI(repositoryURL);
        } catch (URISyntaxException e3) {
            logger.error("iti43 - " + e3.getMessage());
        }
        System.out.println("URI of the XDS Registry - " + repositoryURI.toString());

        B_Consumer c = new B_Consumer(repositoryURI);
        IHESOAP12Sender sender = (IHESOAP12Sender) c.getSenderClient().getSender();
        Options options = sender.getAxisServiceClient().getOptions();
        options.setTimeOutInMilliSeconds(100000);
        
        //AtnaAgentFactory.getAtnaAgent().setDoAudit(false);
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
	//XDSResponseType response = null;
        XDSRetrieveResponseType response = null;
	try {
            response = c.retrieveDocumentSet(retrieveRequest, patientId);
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