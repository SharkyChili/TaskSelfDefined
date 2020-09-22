package com.dfjx.diy.param.writer;

import com.dfjx.diy.param.Param;

public class HdfsWriterParam extends WriterParam {
    public String getWorkerClass(){
        return "com.dfjx.diy.sync.writer.HdfsWriterTask";
    }


    public String url = "hdfs://172.19.1.14:8020";
    public String secureid = "0Hr0is06in4hxBu2V6vU0DAMFw7BT50pC4V5";
    public String username = "wayne";
    public String securekey = "ItWu35remPNQFYhe4Szr58Id2PVbg7cM";
    public String path = "/b_";

}
