package com.mediamonks.utils;

import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/8.
 */
public class AESTest {
    String text = "{'wechatId':'57b0d57f-e458-432a-875a-c413f1cc0ed4','url':'http://www.google.com'}";

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
        assertEquals(text,decrypt);
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
}