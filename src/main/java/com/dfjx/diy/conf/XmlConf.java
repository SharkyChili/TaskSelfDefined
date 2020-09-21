package com.dfjx.diy.conf;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.Type;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class XmlConf extends Conf{



    public static XmlConf create(){
        return new XmlConf();
    }

    public XmlConf buildConfFromXml(String xmlPath) {
//        XmlConf xmlConf = new XmlConf();
        //解析xml
        parseXml(xmlPath);

        ReaderParam readerParam = (ReaderParam) buildParam(sourceType, sourceAttrMap, Type.Reader);
        WriterParam writerParam = (WriterParam) buildParam(targetType, targetAttrMap, Type.Writer);
        this.setReaderParam(readerParam);
        this.setWriterParam(writerParam);

        return this;
    }

    private void parseXml(String xmlPath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        String path = this.getClass().getClassLoader().getResource("")
                .getPath() + xmlPath;
        Document document = null;
        try {
            document = db.parse(path);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList objs = document.getChildNodes();


        for (int i = 0; i < objs.getLength(); i++) {
            Node conf = objs.item(i);
            if (conf == null) {
                break;
            }
            NodeList dbList = conf.getChildNodes();

            for (int j = 0; j < dbList.getLength(); j++) {
                Node node = dbList.item(j);
                if (node == null || "#text".equals(node.getNodeName())) {
                    continue;
                }
//                    if(node.)
                if ("source".equals(node.getNodeName()) || "target".equals(node.getNodeName())) {
                    NodeList typeList = node.getChildNodes();
                    for (int i1 = 0; i1 < typeList.getLength(); i1++) {
                        Node type = typeList.item(i1);
                        if (type == null || "#text".equals(type.getNodeName())) {
                            continue;
                        }
                        if ("source".equals(node.getNodeName())) {
                            sourceType = type.getNodeName();
                        } else {
                            targetType = type.getNodeName();
                        }
                        NodeList att = type.getChildNodes();

                        for (int i2 = 0; i2 < att.getLength(); i2++) {
                            if (att.item(i2) == null || "#text".equals(att.item(i2).getNodeName())) {
                                continue;
                            }
                            String key = att.item(i2).getNodeName();
                            String value = att.item(i2).getTextContent();
                            if ("source".equals(node.getNodeName())) {
                                sourceAttrMap.put(key, value);
                            } else {
                                targetAttrMap.put(key, value);
                            }
                        }
                    }
                }
            }
        }
    }




}
