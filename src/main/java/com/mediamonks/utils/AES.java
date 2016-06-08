package com.mediamonks.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by zhangchen on 16/6/7.
 */
public class AES {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static Logger logger = LoggerFactory.getLogger(AES.class);


    public static SecretKey getKey() {

        if(secretKey!=null){
            return secretKey;
        }
        MessageDigest sha = null;
        try {
            key = PropertiesUtils.get("aesKey").getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            secretKey = new SecretKeySpec(key, "AES");
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }


    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, getKey());
            return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt.trim())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }


}
