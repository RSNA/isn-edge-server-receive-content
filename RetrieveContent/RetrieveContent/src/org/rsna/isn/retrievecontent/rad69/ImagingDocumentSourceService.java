
package org.rsna.isn.retrievecontent.rad69;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.1-hudson-28-
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ImagingDocumentSource_Service", targetNamespace = "urn:ihe:rad:xdsi-b:2009", wsdlLocation = "https://clearinghouse.lifeimage.com/ImagingDocumentSource_Service?wsdl")
public class ImagingDocumentSourceService
    extends Service
{

    private final static URL IMAGINGDOCUMENTSOURCESERVICE_WSDL_LOCATION;
    private final static WebServiceException IMAGINGDOCUMENTSOURCESERVICE_EXCEPTION;
    private final static QName IMAGINGDOCUMENTSOURCESERVICE_QNAME = new QName("urn:ihe:rad:xdsi-b:2009", "ImagingDocumentSource_Service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://clearinghouse.lifeimage.com/ImagingDocumentSource_Service?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        IMAGINGDOCUMENTSOURCESERVICE_WSDL_LOCATION = url;
        IMAGINGDOCUMENTSOURCESERVICE_EXCEPTION = e;
    }

    public ImagingDocumentSourceService() {
        super(__getWsdlLocation(), IMAGINGDOCUMENTSOURCESERVICE_QNAME);
    }

    public ImagingDocumentSourceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), IMAGINGDOCUMENTSOURCESERVICE_QNAME, features);
    }

    public ImagingDocumentSourceService(URL wsdlLocation) {
        super(wsdlLocation, IMAGINGDOCUMENTSOURCESERVICE_QNAME);
    }

    public ImagingDocumentSourceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, IMAGINGDOCUMENTSOURCESERVICE_QNAME, features);
    }

    public ImagingDocumentSourceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ImagingDocumentSourceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ImagingDocumentSourcePortType
     */
    @WebEndpoint(name = "ImagingDocumentSource_Port_Soap12")
    public ImagingDocumentSourcePortType getImagingDocumentSourcePortSoap12() {
        return super.getPort(new QName("urn:ihe:rad:xdsi-b:2009", "ImagingDocumentSource_Port_Soap12"), ImagingDocumentSourcePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ImagingDocumentSourcePortType
     */
    @WebEndpoint(name = "ImagingDocumentSource_Port_Soap12")
    public ImagingDocumentSourcePortType getImagingDocumentSourcePortSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("urn:ihe:rad:xdsi-b:2009", "ImagingDocumentSource_Port_Soap12"), ImagingDocumentSourcePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (IMAGINGDOCUMENTSOURCESERVICE_EXCEPTION!= null) {
            throw IMAGINGDOCUMENTSOURCESERVICE_EXCEPTION;
        }
        return IMAGINGDOCUMENTSOURCESERVICE_WSDL_LOCATION;
    }

}
