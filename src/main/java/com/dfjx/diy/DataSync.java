package com.dfjx.diy;

import com.dfjx.diy.conf.MergedConf;
import com.dfjx.diy.conf.SysConf;
import com.dfjx.diy.conf.XmlConf;
import com.dfjx.diy.sync.DataSyncInterface;

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
        DataSync dataSync = new DataSync();
        String classFromXml = dataSync.getClassFromXml();
        Class<?> aClass = Class.forName(classFromXml);
        DataSyncInterface dataSyncInterface = (DataSyncInterface) aClass.newInstance();

        //读取配置
        XmlConf xmlConf = getAttributeFromXml();
        SysConf sysConf = getAttributeFromSys();
        MergedConf Conf = MergedConf.merge(xmlConf, sysConf);

        dataSyncInterface.init(Conf);
        dataSyncInterface.start();
    }







    private String getClassFromXml(){
        return "";
    }

    private XmlConf getAttributeFromXml(){
        return new XmlConf();
    }

    private SysConf getAttributeFromSys(){
        return new SysConf();
    }
}
