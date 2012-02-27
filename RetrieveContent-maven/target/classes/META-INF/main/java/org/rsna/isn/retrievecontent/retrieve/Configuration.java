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

import java.io.File;
import org.apache.commons.lang.StringUtils;
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
    private static String rootPath;
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

    public Configuration() {

    }

    public Configuration(String key, String value, Timestamp modified_date) {
        this.key = key;
        this.value = value;
        this.modified_date = modified_date;
    }

    public synchronized static void init(){

        if (rootPath != null)
            return;

        //
        // Read from RSNA_ROOT environment variable
        //
        String env = System.getenv("RSNA_ROOT");
        if (rootPath == null && StringUtils.isNotBlank(env))
        {
            File rootDir = new File(env);

            if (!rootDir.isDirectory())
                throw new IllegalArgumentException(env + " is not a directory.");

            rootPath = env;
        }

        //
        // Read from "rsna.root" system property
        //
        String sys = System.getProperty("rsna.root");
        if (StringUtils.isNotBlank(sys))
        {
            File rootDir = new File(sys);

            if (!rootDir.isDirectory())
                throw new IllegalArgumentException(sys + " is not a directory.");

            rootPath = sys;
        }

        //
        // Default to ${user.home}/rsna
        //
        if (rootPath == null)
        {
            String user = System.getProperty("user.home");

            File rootDir = new File(user, "rsna");
            rootDir.mkdir();

            if (!rootDir.isDirectory())
                throw new IllegalArgumentException("Unable to create "
                        + rootDir + " directory.");

            rootPath = rootDir.getPath();
        }

        File imgDir = getImageDir();
        System.out.println(imgDir.toString());
        if (!imgDir.exists())
            imgDir.mkdir();
        if (!imgDir.isDirectory())
        {
            if (!imgDir.isDirectory())
                throw new IllegalArgumentException(imgDir + " is not a directory.");
        }

        File confDir = getConfDir();
        if (!confDir.isDirectory())
        {
            if (!confDir.isDirectory())
                throw new IllegalArgumentException(confDir + " is not a directory.");
        }

        File tmpDir = getTmpDir();
        if (!tmpDir.exists())
            tmpDir.mkdir();
        if (!tmpDir.isDirectory())
        {
            if (!tmpDir.isDirectory())
                throw new IllegalArgumentException(tmpDir + " is not a directory.");
        }

        File logDir = getLogDir();
        if (!logDir.exists())
            logDir.mkdir();

        File log4j = new File(confDir, "retrieve-log4j.properties");
        PropertyConfigurator.configure(log4j.getPath());

        try {
            props.load(new java.io.FileInputStream(new File(confDir,"retrieve-content.properties")));

            RegistryURL = props.getProperty("RegistryURL");
            RepositoryURL = props.getProperty("RepositoryURL");
            Rad69URL = props.getProperty("Rad69URL");
            RepositoryUniqueID = props.getProperty("RepositoryUniqueID");
            AssigningAuthorityUniversalId = props.getProperty("AssigningAuthorityUniversalId");
            AssigningAuthorityUniversalIdType = props.getProperty("AssigningAuthorityUniversalIdType");
            HomeCommunityId = props.getProperty("HomeCommunityId");

            String keystorepassword = props.getProperty("keystorepassword");
            String truststorepassword = props.getProperty("truststorepassword");

            File keystore = new File(confDir, "keystore.jks");
            setProperty("javax.net.ssl.keyStore", keystore.getPath());
            setProperty("javax.net.ssl.keyStorePassword", keystorepassword);

            File truststore = new File(confDir, "truststore.jks");
            setProperty("javax.net.ssl.trustStore", truststore.getPath());
            setProperty("javax.net.ssl.trustStorePassword", truststorepassword);
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


    /**
     * Get the rsna root directory.
     *
     * @return A file instance
     */
    public static File getRootDir()
    {
        return new File(rootPath);
    }

    /**
     * Get the image directory for retrieve.
     *
     * @return A file instance
     */
    public static File getImageDir()
    {
        return new File(rootPath, "images");
    }

    /**
     * Get the rsna/tmp directory.
     * @return A file instance
     */
    public static File getTmpDir()
    {
        return new File(rootPath, "temp");
    }

    /**
     * Get the rsna/conf directory.
     * @return A file instance
     */
    public static File getConfDir()
    {
        return new File(rootPath, "conf");
    }

    public static File getLogDir()
    {
        return new File(rootPath, "logs");
    }

    private static void setProperty(String key, String value)
    {
        String temp = System.getProperty(key);
        if(StringUtils.isBlank(temp))
            System.setProperty(key, value);
    }
}
