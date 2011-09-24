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

import java.sql.Timestamp;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

/**
 * Method selects value of a configuration key from configuration table
 * argument is configuration key
 * <p>
 * @param  String key
 * @return  String value
 *
 * @version @author         Note
 * 1.0.0    Wendy Zhu       Modified based on origin copy
 *
 */
public class Configuration {

    public final static String configfile = "imageretrieve.properties";
    public final static String getRad69URL = "";
    private String key ;
    private String value ;
    private java.sql.Timestamp modified_date;
    
    private static Properties props = new Properties();
    public static String RegistryURL;
    public static String RepositoryURL;
    public static String Rad69URL;
    public static String RepositoryUniqueID;
    public static String AssigningAuthorityUniversalId;
    public static String AssigningAuthorityUniversalIdType;
    public static String HomeCommunityId;
    public static String tempdir;
    public static String imagedir;
    public static String reportdir;

    public Configuration() {
        
    }

    public Configuration(String key, String value, Timestamp modified_date) {
        this.key = key;
        this.value = value;
        this.modified_date = modified_date;
    }

    public synchronized static void init(){
        try {
            props.load(new java.io.FileInputStream(configfile));
            
            RegistryURL = props.getProperty("RegistryURL");
            RepositoryURL = props.getProperty("RepositoryURL");
            Rad69URL = props.getProperty("Rad69URL");
            RepositoryUniqueID = props.getProperty("RepositoryUniqueID");
            AssigningAuthorityUniversalId = props.getProperty("AssigningAuthorityUniversalId");
            AssigningAuthorityUniversalIdType = props.getProperty("AssigningAuthorityUniversalIdType");            
            HomeCommunityId = props.getProperty("HomeCommunityId");

            tempdir = props.getProperty("tempdir");
            imagedir = props.getProperty("imagedir");
            reportdir = props.getProperty("reportdir");

            String logPropsPath = props.getProperty("logPropsPath");
            PropertyConfigurator.configure(logPropsPath);

            String keystorefile = props.getProperty("keystorefile");
            String keystorepassword = props.getProperty("keystorepassword");
            System.setProperty("javax.net.ssl.keyStore", keystorefile);
            System.setProperty("javax.net.ssl.keyStorePassword", keystorepassword);

            String truststorefile = props.getProperty("truststorefile");
            String truststorepassword = props.getProperty("truststorepassword");
            System.setProperty("javax.net.ssl.trustStore", truststorefile);
            System.setProperty("javax.net.ssl.trustStorePassword", truststorepassword);
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
       }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Timestamp getModified_date() {
        return modified_date;
    }

    public void setModified_date(Timestamp modified_date) {
        this.modified_date = modified_date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
