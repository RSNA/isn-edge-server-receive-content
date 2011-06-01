/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsna.retrievedocumentset;

import java.sql.Timestamp;


 /**
     * Method selects value of a configuration key from configuration table
     * argument is configuration key
     * <p>
     * @param  String key
     * @return  String value
     */


public class Configuration {
    
    private String  key ;
    private String value ;
    private  java.sql.Timestamp modified_date ;

    public Configuration() {

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

    public Configuration(String key, String value, Timestamp modified_date) {
        this.key = key;
        this.value = value;
        this.modified_date = modified_date;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
