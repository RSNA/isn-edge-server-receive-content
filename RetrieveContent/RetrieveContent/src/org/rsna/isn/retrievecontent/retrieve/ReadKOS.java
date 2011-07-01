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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.util.TagUtils;

/**
 * Read KOS file.
 *
 * @version @author
 * 1.0.0    erackus
 * 1.1.0    Wendy Zhu
 *
 */
public class ReadKOS {
    private DocumentInfo docInfo = new DocumentInfo();
    private String transferSyntaxUID = "";
    private String savedTagName = "";
    private String currentSeriesInstanceUID = "";
    private ArrayList<String> seriesInstanceUIDList = new ArrayList<String>();
    private ArrayList<String> imageList = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> images = new HashMap<String, ArrayList<String>>();

    public ReadKOS() {
    }

    public DocumentInfo listHeader(DicomObject object, String rsnaID, String documentUniqueID) {

        if (object == null) {
            return null;
        }
        docInfo.setRsnaID(rsnaID);
        docInfo.setDocumentUniqueID(documentUniqueID);
        transferSyntaxUID = object.getString(Tag.TransferSyntaxUID);
        docInfo.setTransferSyntaxUID(transferSyntaxUID);
        Iterator<DicomElement> iter = object.datasetIterator();
        while(iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = object.nameOf(tag);
                if (tagName.equals("Referenced SOP Sequence")) {
                    savedTagName = tagName;
                } else if (tagName.equals("Series Instance UID")) {
                    savedTagName = tagName;
                }
                String tagAddr = TagUtils.toString(tag);
                String tagVR = object.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        int num = element.countItems();
                        System.out.println(tagAddr +" ["+  tagVR +"] "+ tagName + " size= " + num);
                        for (int i=0; i<num; i++) {
                            DicomObject object1 = element.getDicomObject(i);
                            Iterator<DicomElement> iter1 = object1.datasetIterator();
                            while(iter1.hasNext()) {
                                DicomElement element1 = iter1.next();
                                int tag1 = element1.tag();
                                try {
                                    String tagName1 = object1.nameOf(tag1);
                                    if (tagName1.equals("Referenced SOP Sequence")) {
                                        savedTagName = tagName1;
                                    }
                                    String tagAddr1 = TagUtils.toString(tag1);
                                    String tagVR1 = object1.vrOf(tag1).toString();
                                    if (tagVR1.equals("SQ")) {
                                        if (element1.hasItems()) {
                                            int num1 = element1.countItems();
                                            System.out.println(tagAddr1 +" ["+  tagVR1 +"] "+ tagName1 + " size= " + num1);
                                            for (int j=0; j<num1; j++) {
                                                DicomObject object2 = element1.getDicomObject(j);
                                                Iterator<DicomElement> iter2 = object2.datasetIterator();
                                                while(iter2.hasNext()) {
                                                    DicomElement element2 = iter2.next();
                                                    int tag2 = element2.tag();
                                                    try {
                                                        String tagName2 = object2.nameOf(tag2);
                                                        if (tagName2.equals("Referenced SOP Sequence")) {
                                                            savedTagName = tagName2;
                                                        }
                                                        String tagAddr2 = TagUtils.toString(tag2);
                                                        String tagVR2 = object2.vrOf(tag2).toString();
                                                        if (tagVR2.equals("SQ")) {
                                                            if (element2.hasItems()) {
                                                                int num2 = element2.countItems();
                                                                System.out.println(tagAddr2 +" ["+  tagVR2 +"] "+ tagName2 + " size= " + num2);
                                                                for (int k=0; k<num2; k++) {
                                                                    DicomObject object3 = element2.getDicomObject(k);
                                                                    Iterator<DicomElement> iter3 = object3.datasetIterator();
                                                                    while(iter3.hasNext()) {
                                                                        DicomElement element3 = iter3.next();
                                                                        int tag3 = element3.tag();
                                                                        try {
                                                                            String tagName3 = object3.nameOf(tag3);
                                                                            if (tagName3.equals("Referenced SOP Sequence")) {
                                                                                savedTagName = tagName3;
                                                                            }
                                                                            String tagAddr3 = TagUtils.toString(tag3);
                                                                            String tagVR3 = object3.vrOf(tag3).toString();
                                                                            if (tagVR3.equals("SQ")) {
                                                                                if (element3.hasItems()) {
                                                                                    int num3 = element3.countItems();
                                                                                    System.out.println(tagAddr +" ["+  tagVR3 +"] "+ tagName3 + " size= " + num3);
                                                                                    for (int l=0; l<num3; l++) {
                                                                                        DicomObject object4 = element3.getDicomObject(l);
                                                                                        Iterator<DicomElement> iter4 = object4.datasetIterator();
                                                                                        while(iter4.hasNext()) {
                                                                                            DicomElement element4 = iter4.next();
                                                                                            int tag4 = element4.tag();
                                                                                            try {
                                                                                                String tagName4 = object4.nameOf(tag4);
                                                                                                if (tagName4.equals("Referenced SOP Sequence")) {
                                                                                                    savedTagName = tagName4;
                                                                                                }
                                                                                                String tagAddr4 = TagUtils.toString(tag4);
                                                                                                String tagVR4 = object4.vrOf(tag4).toString();
                                                                                                String tagValue4 = object4.getString(tag4);
                                                                                                addDocInfo(tagAddr4, tagValue4);
                                                                                                System.out.println(tagAddr4 +" ["+ tagVR4 +"] "+ tagName4 +" ["+ tagValue4+"]");
                                                                                            } catch (Exception e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                String tagValue3 = object3.getString(tag3);
                                                                                addDocInfo(tagAddr3, tagValue3);
                                                                                System.out.println(tagAddr3 +" ["+ tagVR3 +"] "+ tagName3 +" ["+ tagValue3+"]");
                                                                            }
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            String tagValue2 = object2.getString(tag2);
                                                            addDocInfo(tagAddr2, tagValue2);
                                                            System.out.println(tagAddr2 +" ["+ tagVR2 +"] "+ tagName2 +" ["+ tagValue2 +"]");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        String tagValue1 = object1.getString(tag1);
                                        addDocInfo(tagAddr1, tagValue1);
                                       System.out.println(tagAddr1 +" ["+ tagVR1 +"] "+ tagName1 +" ["+ tagValue1+"]");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {
                    String tagValue = object.getString(tag);
                    addDocInfo(tagAddr, tagValue);
                    System.out.println(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return docInfo;
    }

    private void addDocInfo(String tagAddr, String tagValue) throws SQLException, InterruptedException, FileNotFoundException, IOException, Exception {
        if (tagAddr.equals("(0020,000D)")) {
            docInfo.setStudyInstanceUID(tagValue);
        } else if (tagAddr.equals("(0020,000E)")) {
            docInfo.setSeriesInstanceUID(seriesInstanceUIDList, tagValue);
            currentSeriesInstanceUID = tagValue;
            if (!imageList.isEmpty()) {
                images.put(currentSeriesInstanceUID, imageList);
                docInfo.setImages(images);
                imageList = new ArrayList<String>();
                savedTagName = "Series Instance UID";
            }
        } else if (tagAddr.equals("(0008,1030)")) {
            docInfo.setStudyDescription(tagValue);
        } else if (tagAddr.equals("(0008,0060)")) {
            docInfo.setModality(tagValue);
        } else if (tagAddr.equals("(0008,0020)")) {
            docInfo.setStudyDate(tagValue);
        } else if (tagAddr.equals("(0008,0030)")) {
            docInfo.setStudyTime(tagValue);
        } else if (tagAddr.equals("(0008,0050)")) {
            docInfo.setAccessionNumber(tagValue);
        } else if (tagAddr.equals("(0008,1155)")) {
            if (savedTagName.equals("Referenced SOP Sequence")) {
                imageList.add(tagValue);
            }
        } else if (tagAddr.equals("(0010,0010)")) {
            docInfo.setPatientName(tagValue);
        } 
        //else if (tagAddr.equals("(0008,1150)")) {
            //sopClassUID = tagValue;
            //modality = SQLQueries.GetModality(sopClassUID);
            //docInfo.setModality(modality);
        //}
    }
}
