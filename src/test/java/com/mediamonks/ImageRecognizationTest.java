package com.mediamonks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhangchen on 16/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MmWechatAdminConsoleApplication.class)
@WebAppConfiguration
public class ImageRecognizationTest {

    @Test
    public void testImage(){
        String httpUrl = "http://apis.baidu.com/image_search/shitu/shitu_image";
        byte[] httpArg =getBytes("http://static.zhulong.com/photo/small/201404/17/091314h3vfsl40xd2fuqnv_0_0_560_w_0.jpg");
        String jsonResult = request(httpUrl, httpArg);
        System.out.println(jsonResult);
    }

    public static byte[] getBytes(String filePath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            URL url = new URL(filePath);
            is = url.openStream ();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }
        }
        catch (IOException e) {
            e.printStackTrace ();
        }
        finally {
            String toString = baos.toString();
            return baos.toByteArray();
        }
    }

    public static String request(String httpUrl, byte[] httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "480be5f860ee194db167a14cb2c28218");
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
