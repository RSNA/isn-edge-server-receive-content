/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.retrievedocumentset;

/**
 *
 * @author oyesanyf
 */
public class Rad69ServiceDataType {

    public Rad69ServiceDataType() {
    }
    private String rsnaUID;

    public Rad69ServiceDataType(String rsnaUID, String seriesInstanceUID, String studyInstanceUID, String transferSyntaxUID, String homeCommunityId, String repositoryUniqueId, String documentUniqueId, String importDir) {
        this.rsnaUID = rsnaUID;
        this.seriesInstanceUID = seriesInstanceUID;
        this.studyInstanceUID = studyInstanceUID;
        this.transferSyntaxUID = transferSyntaxUID;
        this.homeCommunityId = homeCommunityId;
        this.repositoryUniqueId = repositoryUniqueId;
        this.documentUniqueId = documentUniqueId;
        this.importDir = importDir;
    }
    private String seriesInstanceUID;
    private String studyInstanceUID;
    private String transferSyntaxUID;
    private String homeCommunityId;
    private String repositoryUniqueId;
    private String documentUniqueId;

    public String getDocumentUniqueId() {
        return documentUniqueId;
    }

    public void setDocumentUniqueId(String documentUniqueId) {
        this.documentUniqueId = documentUniqueId;
    }

    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    public void setHomeCommunityId(String homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }

    public String getImportDir() {
        return importDir;
    }

    public void setImportDir(String importDir) {
        this.importDir = importDir;
    }

    public String getRepositoryUniqueId() {
        return repositoryUniqueId;
    }

    public void setRepositoryUniqueId(String repositoryUniqueId) {
        this.repositoryUniqueId = repositoryUniqueId;
    }

    public String getRsnaUID() {
        return rsnaUID;
    }

    public void setRsnaUID(String rsnaUID) {
        this.rsnaUID = rsnaUID;
    }

    public String getSeriesInstanceUID() {
        return seriesInstanceUID;
    }

    public void setSeriesInstanceUID(String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
    }

    public String getStudyInstanceUID() {
        return studyInstanceUID;
    }

    public void setStudyInstanceUID(String studyInstanceUID) {
        this.studyInstanceUID = studyInstanceUID;
    }

    public String getTransferSyntaxUID() {
        return transferSyntaxUID;
    }

    public void setTransferSyntaxUID(String transferSyntaxUID) {
        this.transferSyntaxUID = transferSyntaxUID;
    }
    private String importDir;
}
