/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.retrievedocumentset;

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
