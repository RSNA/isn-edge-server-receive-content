
package org.rsna.getimaging.rad69;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrieveImagingDocumentSetRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveImagingDocumentSetRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StudyRequest" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SeriesRequest" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{urn:ihe:iti:xds-b:2007}RetrieveDocumentSetRequestType">
 *                           &lt;attribute name="seriesInstanceUID" use="required" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="studyInstanceUID" use="required" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TransferSyntaxUIDList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TransferSyntaxUID" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveImagingDocumentSetRequestType", namespace = "urn:ihe:rad:xdsi-b:2009", propOrder = {
    "studyRequest",
    "transferSyntaxUIDList"
})
public class RetrieveImagingDocumentSetRequestType {

    @XmlElement(name = "StudyRequest", required = true)
    protected List<RetrieveImagingDocumentSetRequestType.StudyRequest> studyRequest;
    @XmlElement(name = "TransferSyntaxUIDList", required = true)
    protected RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList transferSyntaxUIDList;

    /**
     * Gets the value of the studyRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studyRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudyRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RetrieveImagingDocumentSetRequestType.StudyRequest }
     * 
     * 
     */
    public List<RetrieveImagingDocumentSetRequestType.StudyRequest> getStudyRequest() {
        if (studyRequest == null) {
            studyRequest = new ArrayList<RetrieveImagingDocumentSetRequestType.StudyRequest>();
        }
        return this.studyRequest;
    }

    /**
     * Gets the value of the transferSyntaxUIDList property.
     * 
     * @return
     *     possible object is
     *     {@link RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList }
     *     
     */
    public RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList getTransferSyntaxUIDList() {
        return transferSyntaxUIDList;
    }

    /**
     * Sets the value of the transferSyntaxUIDList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList }
     *     
     */
    public void setTransferSyntaxUIDList(RetrieveImagingDocumentSetRequestType.TransferSyntaxUIDList value) {
        this.transferSyntaxUIDList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SeriesRequest" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{urn:ihe:iti:xds-b:2007}RetrieveDocumentSetRequestType">
     *                 &lt;attribute name="seriesInstanceUID" use="required" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" />
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="studyInstanceUID" use="required" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "seriesRequest"
    })
    public static class StudyRequest {

        @XmlElement(name = "SeriesRequest", namespace = "urn:ihe:rad:xdsi-b:2009", required = true)
        protected List<RetrieveImagingDocumentSetRequestType.StudyRequest.SeriesRequest> seriesRequest;
        @XmlAttribute(name = "studyInstanceUID", required = true)
        protected String studyInstanceUID;

        /**
         * Gets the value of the seriesRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the seriesRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSeriesRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RetrieveImagingDocumentSetRequestType.StudyRequest.SeriesRequest }
         * 
         * 
         */
        public List<RetrieveImagingDocumentSetRequestType.StudyRequest.SeriesRequest> getSeriesRequest() {
            if (seriesRequest == null) {
                seriesRequest = new ArrayList<RetrieveImagingDocumentSetRequestType.StudyRequest.SeriesRequest>();
            }
            return this.seriesRequest;
        }

        /**
         * Gets the value of the studyInstanceUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStudyInstanceUID() {
            return studyInstanceUID;
        }

        /**
         * Sets the value of the studyInstanceUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStudyInstanceUID(String value) {
            this.studyInstanceUID = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{urn:ihe:iti:xds-b:2007}RetrieveDocumentSetRequestType">
         *       &lt;attribute name="seriesInstanceUID" use="required" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class SeriesRequest
            extends RetrieveDocumentSetRequestType
        {

            @XmlAttribute(name = "seriesInstanceUID", required = true)
            protected String seriesInstanceUID;

            /**
             * Gets the value of the seriesInstanceUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeriesInstanceUID() {
                return seriesInstanceUID;
            }

            /**
             * Sets the value of the seriesInstanceUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeriesInstanceUID(String value) {
                this.seriesInstanceUID = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TransferSyntaxUID" type="{urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0}LongName" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transferSyntaxUID"
    })
    public static class TransferSyntaxUIDList {

        @XmlElement(name = "TransferSyntaxUID", namespace = "urn:ihe:rad:xdsi-b:2009", required = true)
        protected List<String> transferSyntaxUID;

        /**
         * Gets the value of the transferSyntaxUID property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the transferSyntaxUID property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTransferSyntaxUID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTransferSyntaxUID() {
            if (transferSyntaxUID == null) {
                transferSyntaxUID = new ArrayList<String>();
            }
            return this.transferSyntaxUID;
        }

    }

}
