/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsna.retrievedocumentset;

/**
 *
 * @author oyesanyf
 */
public class ITI18DataType {

    public  ITI18DataType() {

    }

    public String getAssigningAuthorityUniversalId() {
        return assigningAuthorityUniversalId;
    }

    public void setAssigningAuthorityUniversalId(String assigningAuthorityUniversalId) {
        this.assigningAuthorityUniversalId = assigningAuthorityUniversalId;
    }

    public String getAssigningAuthorityUniversalIdType() {
        return assigningAuthorityUniversalIdType;
    }

    public void setAssigningAuthorityUniversalIdType(String assigningAuthorityUniversalIdType) {
        this.assigningAuthorityUniversalIdType = assigningAuthorityUniversalIdType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClassCodes() {
        return classCodes;
    }

    public void setClassCodes(String classCodes) {
        this.classCodes = classCodes;
    }

    public String getCodedMetadataType() {
        return codedMetadataType;
    }

    public void setCodedMetadataType(String codedMetadataType) {
        this.codedMetadataType = codedMetadataType;
    }

    public String getConfidentialityCodes() {
        return confidentialityCodes;
    }

    public void setConfidentialityCodes(String confidentialityCodes) {
        this.confidentialityCodes = confidentialityCodes;
    }

    public String getCreationTimeEndDate() {
        return creationTimeEndDate;
    }

    public void setCreationTimeEndDate(String creationTimeEndDate) {
        this.creationTimeEndDate = creationTimeEndDate;
    }

    public String getCreationTimeStartDate() {
        return creationTimeStartDate;
    }

    public void setCreationTimeStartDate(String creationTimeStartDate) {
        this.creationTimeStartDate = creationTimeStartDate;
    }

    public String getEventCodes() {
        return eventCodes;
    }

    public void setEventCodes(String eventCodes) {
        this.eventCodes = eventCodes;
    }

    public String getFormatCodes() {
        return formatCodes;
    }

    public void setFormatCodes(String formatCodes) {
        this.formatCodes = formatCodes;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPracticeSettingCodes() {
        return practiceSettingCodes;
    }

    public void setPracticeSettingCodes(String practiceSettingCodes) {
        this.practiceSettingCodes = practiceSettingCodes;
    }

    public String getRegistryURL() {
        return registryURL;
    }

    public void setRegistryURL(String registryURL) {
        this.registryURL = registryURL;
    }

    public ITI18DataType(String patientID, String registryURL, String repositoryUniqueID, String assigningAuthorityUniversalId, String assigningAuthorityUniversalIdType, String classCodes, String creationTimeStartDate, String creationTimeEndDate, String practiceSettingCodes, String codedMetadataType, String eventCodes, String confidentialityCodes, String formatCodes, String author) {
        this.patientID = patientID;
        this.registryURL = registryURL;
        this.repositoryUniqueID = repositoryUniqueID;
        this.assigningAuthorityUniversalId = assigningAuthorityUniversalId;
        this.assigningAuthorityUniversalIdType = assigningAuthorityUniversalIdType;
        this.classCodes = classCodes;
        this.creationTimeStartDate = creationTimeStartDate;
        this.creationTimeEndDate = creationTimeEndDate;
        this.practiceSettingCodes = practiceSettingCodes;
        this.codedMetadataType = codedMetadataType;
        this.eventCodes = eventCodes;
        this.confidentialityCodes = confidentialityCodes;
        this.formatCodes = formatCodes;
        this.author = author;
    }



    public String getRepositoryUniqueID() {
        return repositoryUniqueID;
    }

    public void setRepositoryUniqueID(String repositoryUniqueID) {
        this.repositoryUniqueID = repositoryUniqueID;
    }

    private String patientID ;
    private String registryURL ;
    private String repositoryUniqueID ;
    private String assigningAuthorityUniversalId ;
    private String assigningAuthorityUniversalIdType ;
    private String classCodes ;
    private String creationTimeStartDate ;
    private String creationTimeEndDate ;
    private String  practiceSettingCodes ;
    private String codedMetadataType ;
    private String eventCodes ;
    private String confidentialityCodes ;
    private String formatCodes ;
    private String  author ;




}
