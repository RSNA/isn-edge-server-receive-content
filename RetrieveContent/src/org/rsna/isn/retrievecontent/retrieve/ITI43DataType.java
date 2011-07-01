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

/**
 *
 * @author oyesanyf
 */
public class ITI43DataType {
    
    public  ITI43DataType() {
        
    }

    public ITI43DataType(String patientID, String RepositoryUniqueId, String HomeCommunityId, String DocumentUniqueId, String downloadDIR, String repositoryURL, String AssigningAuthorityUniversalId, String AssigningAuthorityUniversalIdType) {
        this.patientID = patientID;
        this.RepositoryUniqueId = RepositoryUniqueId;
        this.HomeCommunityId = HomeCommunityId;
        this.DocumentUniqueId = DocumentUniqueId;
        this.downloadDIR = downloadDIR;
        this.repositoryURL = repositoryURL;
        this.AssigningAuthorityUniversalId = AssigningAuthorityUniversalId;
        this.AssigningAuthorityUniversalIdType = AssigningAuthorityUniversalIdType;
    }

    private String patientID;
    private String RepositoryUniqueId;
    private String HomeCommunityId;
    private String DocumentUniqueId;

    public String getAssigningAuthorityUniversalId() {
        return AssigningAuthorityUniversalId;
    }

    public void setAssigningAuthorityUniversalId(String AssigningAuthorityUniversalId) {
        this.AssigningAuthorityUniversalId = AssigningAuthorityUniversalId;
    }

    public String getAssigningAuthorityUniversalIdType() {
        return AssigningAuthorityUniversalIdType;
    }

    public void setAssigningAuthorityUniversalIdType(String AssigningAuthorityUniversalIdType) {
        this.AssigningAuthorityUniversalIdType = AssigningAuthorityUniversalIdType;
    }

    public String getDocumentUniqueId() {
        return DocumentUniqueId;
    }

    public void setDocumentUniqueId(String DocumentUniqueId) {
        this.DocumentUniqueId = DocumentUniqueId;
    }

    public String getHomeCommunityId() {
        return HomeCommunityId;
    }

    public void setHomeCommunityId(String HomeCommunityId) {
        this.HomeCommunityId = HomeCommunityId;
    }

    public String getRepositoryUniqueId() {
        return RepositoryUniqueId;
    }

    public void setRepositoryUniqueId(String RepositoryUniqueId) {
        this.RepositoryUniqueId = RepositoryUniqueId;
    }

    public String getDownloadDIR() {
        return downloadDIR;
    }

    public void setDownloadDIR(String downloadDIR) {
        this.downloadDIR = downloadDIR;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getRepositoryURL() {
        return repositoryURL;
    }

    public void setRepositoryURL(String repositoryURL) {
        this.repositoryURL = repositoryURL;
    }
    private String downloadDIR;
    private String repositoryURL ;
    private String AssigningAuthorityUniversalId ;
    private String AssigningAuthorityUniversalIdType ;










}
