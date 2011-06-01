/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsna.retrievedocumentset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author erackus
 */
public class DocumentInfo {
    public DocumentInfo() {

    }

    private String documentUniqueID;
    private String rsnaID;
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

    public DocumentInfo(String documentUniqueID, String rsnaID, String transferSyntaxUID, String studyInstanceUID,  ArrayList<String> seriesInstanceUIDList, HashMap<String, ArrayList<String>> images, String studyDescription, String modality, String accessionNumber, String studyDate, String studyTime) {
        this.documentUniqueID = documentUniqueID;
        this.rsnaID = rsnaID;
        this.transferSyntaxUID = transferSyntaxUID;
        this.studyInstanceUID = studyInstanceUID;
        this.studyDescription = studyDescription;
        this.seriesInstanceUIDList = seriesInstanceUIDList;
        this.images = images;
        this.modality = modality;
        this.accessionNumber = accessionNumber;
        this.studyDate = studyDate;
        this.studyTime = studyTime;
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
