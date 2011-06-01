/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsna.retrievedocumentset;

import java.security.MessageDigest;
import java.math.BigInteger;

/**
 *
 * @author wzhu
 */
public class TransHash {
    public static String gen(String userToken, String dob, String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(userToken.getBytes("UTF8"));
        md.update(dob.getBytes("UTF8"));
        md.update(password.getBytes("UTF8"));
        return (new BigInteger(1,md.digest())).toString(16);
    }
}
