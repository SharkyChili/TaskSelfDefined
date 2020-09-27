package com.dfjx.diy.sync.reader;

import com.alibaba.fastjson.JSONObject;
import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.reader.HdfsReaderParam;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.HdfsWriterParam;
import com.dfjx.diy.sync.Sync;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;

public class HdfsReaderTask extends ReaderTask {
    Configuration conf;
    URI uri;
    FileSystem fs = null;
    Path path;
    BufferedReader bufferedReader;
    String line = null;

    @Override
    public  void init(Param param, BlockingQueue<Object> queue, Sync sync){
        super.init(param,queue,sync);

        HdfsReaderParam hdfsReaderParam = (HdfsReaderParam) this.param;

        path = new Path(hdfsReaderParam.path + hdfsReaderParam.filename);

        try {
//            uri = new URI("hdfs://172.19.1.14:9000");
//            uri = new URI("hdfs://172.19.1.49:8020");
//            uri = new URI("hdfs://172.19.1.14:8020");
            uri = new URI(hdfsReaderParam.url);
        } catch (URISyntaxException e) {
            System.out.println(Thread.currentThread().getName() + " : " + "HdfsReaderTask uri exception");
            e.printStackTrace();
        }

        conf = new Configuration();
//      conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
//      conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

//        conf.set("hadoop.security.authentication","tbds");
//        conf.set("hadoop_security_authentication_tbds_secureid","0Hr0is06in4hxBu2V6vU0DAMFw7BT50pC4V5");
//        conf.set("hadoop_security_authentication_tbds_username","wayne");
//        conf.set("hadoop_security_authentication_tbds_securekey","ItWu35remPNQFYhe4Szr58Id2PVbg7cM");
        conf.set("hadoop.security.authentication","tbds");
        conf.set("hadoop_security_authentication_tbds_secureid",hdfsReaderParam.secureid);
        conf.set("hadoop_security_authentication_tbds_username",hdfsReaderParam.username);
        conf.set("hadoop_security_authentication_tbds_securekey",hdfsReaderParam.securekey);

        //kerberos
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromSubject(null);

            System.out.println(Thread.currentThread().getName() + " : " + "hdfs init: conf success");
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " : " + "HdfsReaderTask IOException");
            throw new RuntimeException(e);
        }catch (Exception e){
            System.out.println(Thread.currentThread().getName() + " : " + "HdfsReaderTask Exception");
            throw new RuntimeException(e);
        }

        //拿到一个文件系统操作的客户端实例对象
//            fs = FileSystem.get(conf) ;   //demo
        try {
            fs = FileSystem.get(uri, conf);  // 我改的
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " : " + "HdfsReaderTask FileSystem IOException");
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " : " + "hdfs 初始化成功");

        //
        FSDataInputStream inputStream = null;
        try {
            inputStream = fs.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
    };


    @Override
    Object produce() {
        try {
            if((line=bufferedReader.readLine())!=null){
                JSONObject jsonObject = JSONObject.parseObject(line);
                System.out.println(Thread.currentThread().getName() + " : " + "hdfs produce:" + jsonObject.toJSONString());
                return jsonObject;
            }
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " : " + "fs.open(path) IOException :" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    Object[] produceBatch() {
        //无用
        return new Object[0];
    }

    @Override
    public void close() {
        System.out.println(Thread.currentThread().getName() + " : " + "HdfsReaderTask 结束");
        try {
            fs.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " : " + "hdfs close Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
