package com.dfjx.diy;

import com.dfjx.diy.conf.MergedConf;
import com.dfjx.diy.conf.SysConf;
import com.dfjx.diy.conf.XmlConf;
import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.Type;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;
import com.dfjx.diy.sync.DataSyncInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wayne
 * @date 2020.09.16
 */
public class DataSync {
    public static void main(String[] args) {
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
        XmlConf xmlConf = XmlConf.create().buildConfFromXml("conf.xml");
        //todo 这里暂时不写
        SysConf sysConf = getAttributeFromSys();
        MergedConf Conf = MergedConf.merge(xmlConf, sysConf);

//        dataSyncInterface.init(Conf);
//        dataSyncInterface.start();
    }


    private String getClassFromXml() {
        return "";

    }








    private SysConf getAttributeFromSys() {
        return new SysConf();
    }

}
