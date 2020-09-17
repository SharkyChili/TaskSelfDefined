package com.dfjx.diy.conf;

import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;

public class MergedConf implements Conf{

    public ReaderParam readerParam;

    public WriterParam writerParam;


    public static MergedConf merge(XmlConf xmlConf, SysConf sysConf){
        MergedConf mergedConf = new MergedConf();

        //todo
        //优先sysConf,覆盖掉xmlConf
        return mergedConf;
    }
}
