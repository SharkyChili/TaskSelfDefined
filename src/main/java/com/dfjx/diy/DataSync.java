package com.dfjx.diy;

import com.dfjx.diy.conf.MergedConf;
import com.dfjx.diy.conf.SysConf;
import com.dfjx.diy.conf.XmlConf;
import com.dfjx.diy.sync.DataSyncInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wayne
 * @date 2020.09.16
 */
public class DataSync {
    public static void main(String[] args){
        DataSync dataSync = new DataSync();
        try {
            dataSync.doWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWork() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //实例化工作类
//        DataSync dataSync = new DataSync();
//        String classFromXml = dataSync.getClassFromXml();
//        Class<?> aClass = Class.forName(classFromXml);
//        DataSyncInterface dataSyncInterface = (DataSyncInterface) aClass.newInstance();

        //读取配置
        XmlConf xmlConf = getAttributeFromXml();
        SysConf sysConf = getAttributeFromSys();
        MergedConf Conf = MergedConf.merge(xmlConf, sysConf);

//        dataSyncInterface.init(Conf);
//        dataSyncInterface.start();
    }







    private String getClassFromXml(){
        return "";
    }

    private XmlConf getAttributeFromXml(){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String path = this.getClass().getClassLoader().getResource("")
                    .getPath() + "conf.xml";
            Document document = db.parse(path);
            NodeList objs = document.getChildNodes();

            String sourceType = "";
            Map<String, String> sourceAttrMap = new HashMap<>();
            String targetType = "";
            Map<String, String> targetAttrMap = new HashMap<>();

            for (int i = 0; i < objs.getLength(); i++) {
                Node conf = objs.item(i);
                if(conf==null){break;}
                NodeList dbList = conf.getChildNodes();

                for (int j = 0; j < dbList.getLength(); j++) {
                    Node node = dbList.item(j);
                    if(node==null || "#text".equals(node.getNodeName())){continue;}
//                    if(node.)
                    if("source".equals(node.getNodeName()) || "target".equals(node.getNodeName())){
                        NodeList typeList = node.getChildNodes();
                        for (int i1 = 0; i1 < typeList.getLength(); i1++) {
                            Node type = typeList.item(i1);
                            if(type==null || "#text".equals(type.getNodeName())){continue;}
                            if("source".equals(node.getNodeName())){
                                sourceType = type.getNodeName();
                            }else {
                                targetType = type.getNodeName();
                            }
                            NodeList att = type.getChildNodes();

                            for (int i2 = 0; i2 < att.getLength(); i2++) {
                                if(att.item(i2)==null || "#text".equals(att.item(i2).getNodeName())){continue;}
                                String key = att.item(i2).getNodeName();
                                String value = att.item(i2).getTextContent();
                                if("source".equals(node.getNodeName())){
                                    sourceAttrMap.put(key,value);
                                }else {
                                    targetAttrMap.put(key,value);
                                }
                            }

                        }

                    }



//                    NodeList attributes = node.getChildNodes();
//
//                    for (int k = 0; k < userMeta.getLength(); k++) {
//                        if(userMeta.item(k).getNodeName() != "#text")
//                            System.out.println(userMeta.item(k).getNodeName()
//                                    + ":" + userMeta.item(k).getTextContent());
//                    }
//

                }
            }
            System.out.println();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return new XmlConf();
    }

    private SysConf getAttributeFromSys(){
        return new SysConf();
    }
}
