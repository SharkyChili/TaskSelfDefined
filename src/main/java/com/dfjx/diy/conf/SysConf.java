package com.dfjx.diy.conf;

import com.dfjx.diy.param.Type;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;

import java.util.HashMap;
import java.util.Map;

public class SysConf extends Conf{

    public static SysConf create() {
        return new SysConf();
    }

    public SysConf buildConfFromSysParams(Map<String, String> params){
        if(params == null){return new SysConf();}
        //解析网页配置的参数

        parseMap(params);

        ReaderParam readerParam = (ReaderParam) buildParam(sourceType, sourceAttrMap, Type.Reader);
        WriterParam writerParam = (WriterParam) buildParam(targetType, targetAttrMap, Type.Writer);
        this.setReaderParam(readerParam);
        this.setWriterParam(writerParam);

        return this;
    }

    private void parseMap(Map<String, String> params){
        params.entrySet().stream().forEach(
                entry -> {
                    String[] split = entry.getKey().split("\\.");
                    String value = entry.getValue();
                    if("source".equals(split[0])){
                        sourceType = split[1];
                        sourceAttrMap.put(split[2],value);
                    }else if("target".equals(split[0])){
                        targetType = split[1];
                        targetAttrMap.put(split[2],value);
                    }
                }
        );
    }

//    public static void main(String[] args) {
//        Map<String, String> params = new HashMap<>(16);
//        params.put("source.mpp.url","jdbc:postgresql://10.1.3.40:7300/wayne");
//        params.put("source.mpp.username","wayne");
//        params.put("source.mpp.password","wayne");
//        params.put("source.mpp.sql","select * from wayne");
//
//        params.put("target.hdfs.url","hdfs://172.19.1.14:8020");
//        params.put("target.hdfs.secureid","0Hr0is06in4hxBu2V6vU0DAMFw7BT50pC4V5");
//        params.put("target.hdfs.username","wayne");
//        params.put("target.hdfs.securekey","ItWu35remPNQFYhe4Szr58Id2PVbg7cM");
//        params.put("target.hdfs.path","/b_");
//
//        SysConf sysConf = SysConf.create().buildConfFromSysParams(params);
//        System.out.println("a");
//    }
}
