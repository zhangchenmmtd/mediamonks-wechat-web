package com.mediamonks.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangchen on 16/6/6.
 */
public class MessageUtil {

    public static Map<String, String> xmlToMap(ServletInputStream inputStream) throws IOException, DocumentException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(inputStream);

        Element root = doc.getRootElement();
        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
            System.out.println(e.getName() + "--->" + e.getText());
        }
        inputStream.close();
        return map;
    }


}

