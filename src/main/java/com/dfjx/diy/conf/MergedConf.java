package com.dfjx.diy.conf;

import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;

public class MergedConf extends Conf{




    public static MergedConf merge(XmlConf xmlConf, SysConf sysConf){
        MergedConf mergedConf = new MergedConf();

        ReaderParam xmlReaderParam = xmlConf.getReaderParam();
        xmlReaderParam.overWrite(sysConf.getReaderParam());
        mergedConf.setReaderParam(xmlReaderParam);

        WriterParam xmlWriterParam = xmlConf.getWriterParam();
        xmlWriterParam.overWrite(sysConf.getWriterParam());
        mergedConf.setWriterParam(xmlWriterParam);

        //优先sysConf,覆盖掉xmlConf
        return mergedConf;
    }







}
