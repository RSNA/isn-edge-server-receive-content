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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Define DocumentInfo class.
 *
 * @version @author
 * 1.0.0    erackus
 * 1.1.0    Wendy Zhu
 *
 */
public class DocumentInfo {
    public DocumentInfo() {

    }

    private String documentUniqueID;
    private String rsnaID;
    private String patientName;
    private String studyInstanceUID;
    private String studyDescription;
    private ArrayList<String> seriesInstanceUIDList;
    private HashMap<String, ArrayList<String>> images;
    private String seriesInstanceUID;
    private String modality;
    private String accessionNumber;
    private String studyDate;
    private String studyTime;
    private String transferSyntaxUID;
    private String studyPath;

    public DocumentInfo(String documentUniqueID, String rsnaID, String patientName, String transferSyntaxUID,
            String studyInstanceUID,  ArrayList<String> seriesInstanceUIDList, HashMap<String, ArrayList<String>> images,
            String studyDescription, String modality, String accessionNumber, String studyDate, String studyTime, String studyPath) {
        this.documentUniqueID = documentUniqueID;
        this.rsnaID = rsnaID;
        this.patientName=patientName;
        this.transferSyntaxUID = transferSyntaxUID;
        this.studyInstanceUID = studyInstanceUID;
        this.studyDescription = studyDescription;
        this.seriesInstanceUIDList = seriesInstanceUIDList;
        this.images = images;
        this.modality = modality;
        this.accessionNumber = accessionNumber;
        this.studyDate = studyDate;
        this.studyTime = studyTime;
        this.studyPath = studyPath;
    }

     public String getAccessionNumber() {
        return accessionNumber;
    }

   public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getDocumentUniqueID() {
        return documentUniqueID;
    }

    public void setDocumentUniqueID(String documentUniqueID) {
        this.documentUniqueID = documentUniqueID;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getRsnaID() {
        return rsnaID;
    }

    public void setRsnaID(String rsnaID) {
        this.rsnaID = rsnaID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTransferSyntaxUID() {
        return transferSyntaxUID;
    }

    public void setTransferSyntaxUID(String transferSyntaxUID) {
        this.transferSyntaxUID = transferSyntaxUID;
    }

    public void setSeriesInstanceUID(ArrayList<String> seriesInstanceUIDList, String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
        this.seriesInstanceUIDList = seriesInstanceUIDList;
        if (!isDuplicate(seriesInstanceUIDList, seriesInstanceUID)) {
            seriesInstanceUIDList.add(seriesInstanceUID);
        }
    }

    public ArrayList<String> getSeriesInstanceUIDList() {
        return seriesInstanceUIDList;
    }

    public void setImages(HashMap<String, ArrayList<String>> images) {
        this.images = images;
    }

    public HashMap<String,ArrayList<String>> getImages() {
        return images;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

    public String getStudyInstanceUID() {
        return studyInstanceUID;
    }

    public void setStudyInstanceUID(String studyInstanceUID) {
        this.studyInstanceUID = studyInstanceUID;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public String getStudyPath() {
        return studyPath;
    }

    public void setStudyPath(String studyPath) {
        this.studyPath = studyPath;
    }

    public static <T> boolean isDuplicate(List<T> list, T e) {
        boolean hasElement = false;
        Set<T> set = new HashSet<T>();
        for (T t : list) {
            set.add(t);
        }
        if (set.contains(e)) {
            hasElement = true;
        }
        return hasElement;
    }
}
