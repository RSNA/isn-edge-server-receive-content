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
