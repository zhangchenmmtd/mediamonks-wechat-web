package com.mediamonks.utils;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Formatter;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/8.
 */
public class AESTest {
    String text = "{'wechatId':'b83b0ab2-68f1-4a54-82ae-4724f3646053','url':'http://www.google.com'}";

    @Test
    public void testGetKey() throws Exception {
        SecretKey key = AES.getKey();
        assertNotNull(key);
        assertEquals("RAW", key.getFormat());
    }

    @Test
    public void testEncrypt() throws Exception {
        String encrypt = AES.encrypt(text);
        System.out.println(encrypt);
        assertNotNull(encrypt);
    }

    @Test
    public void testDecrypt() throws Exception {
        String encrypt = AES.encrypt(text);
        assertNotNull(encrypt);

        String decrypt = AES.decrypt(encrypt);
        assertEquals(text, decrypt);
    }

    @Test
    public void testDecrypt_exception() throws Exception {
        try {
            String decrypt = AES.decrypt("asdasdsad");
            fail("should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalBlockSizeException);
        }

    }

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * Signature with following parameters will be
     * VWuHWlrh8JLmsRIuLyQ+qLgunCA=
     * @param args
     */
    public static void main(String[] args) {
        String dataString = "";
        try {
            dataString += URLEncoder.encode("activityId", "utf-8") + "=" + URLEncoder.encode("1", "utf-8");
            dataString += "&" + URLEncoder.encode("clientKey", "utf-8") + "=" + URLEncoder.encode("sd325SFV3sd23g2", "utf-8");
            dataString += "&" + URLEncoder.encode("nonce", "utf-8") + "=" + URLEncoder.encode("dg2geh3qhe2gba34hah23", "utf-8");
            dataString += "&" + URLEncoder.encode("participantId", "utf-8") + "=" + URLEncoder.encode("1", "utf-8");
            dataString += "&" + URLEncoder.encode("points", "utf-8") + "=" + URLEncoder.encode("20", "utf-8");
            dataString += "&" + URLEncoder.encode("timestamp", "utf-8") + "=" + URLEncoder.encode("1467297198", "utf-8");


            System.out.println(dataString);
            String finalString = "POST&"
                    + URLEncoder.encode("http://adidas-ros.local/api/participants/score", "utf-8")
                    + "&"
                    + URLEncoder.encode(dataString, "utf-8");
            System.out.println(finalString);
            System.out.println(calculateRFC2104HMAC(finalString, "z_TxyIy737ibtC-6ETw_0jSD8IrSoL7OQ_MOoQovDXqYeTqt-6"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public static String calculateRFC2104HMAC(String data, String key)
            throws java.security.SignatureException {
        String result;
        try {

            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            result = new BASE64Encoder().encode(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}

