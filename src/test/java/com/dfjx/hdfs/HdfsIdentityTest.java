package com.dfjx.hdfs;

import com.ucar.datalink.domain.media.parameter.hdfs.HDFSMediaSrcParameter;
import com.ucar.datalink.domain.plugin.writer.hdfs.HdfsWriterParameter;
import com.ucar.datalink.writer.hdfs.handle.config.HdfsConfig;
import com.ucar.datalink.writer.hdfs.handle.stream.FileSystemManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 打成jar包放到49上运行，记得放上/root/a.txt
 *
 * @author wayne
 * @date 2020.08.13
 */
public class HdfsIdentityTest {

//    @Test
    public void writeDataTest() throws Exception {
        //FileInputStream input = new FileInputStream(new File("src/a.txt"));
        FileInputStream input = new FileInputStream(new File("/root/a.txt"));
        System.out.println("获取input流成功");

        /*DistributedFileSystem hadoopFS = (DistributedFileSystem) FileSystemManager.getFileSystem(hdfsConfig);
        fileStream = hadoopFS.create(path, false,
                hdfsConfig.getConfiguration().getInt(CommonConfigurationKeysPublic.IO_FILE_BUFFER_SIZE_KEY,
                        CommonConfigurationKeysPublic.IO_FILE_BUFFER_SIZE_DEFAULT),
                (short) 3, 64 * 1024 * 1024L);

        conf.set("hadoop.security.authentication","tbds");
        conf.set("hadoop_security_authentication_tbds_secureid",parameterObj.getSecretid());
        conf.set("hadoop_security_authentication_tbds_username",((HDFSMediaSrcParameter)parameterObj).getUsername());
        conf.set("hadoop_security_authentication_tbds_securekey",parameterObj.getSecretkey());
        IOUtils.copyBytes(new ByteArrayInputStream(bytes), fsOut, conf, true);*/

        String path = "/b_" + System.currentTimeMillis() + ".txt";
        //FSDataOutputStream fsDataOutputStream = fs.create(new Path(path));

        //DistributedFileSystem hadoopFS = (DistributedFileSystem) FileSystemManager.getFileSystem(hdfsConfig);
//        FileSystem.get(
//                hdfsConfig.getHdfsUri(),
//                hdfsConfig.getConfiguration(),
//                hdfsConfig.getHadoopUser());
        URI uri = null;
        try {
//            uri = new URI("hdfs://172.19.1.14:9000");
//            uri = new URI("hdfs://172.19.1.49:8020");
            uri = new URI("hdfs://172.19.1.14:8020");
        } catch (URISyntaxException e) {
            System.out.println("uri exception");
            e.printStackTrace();
        }
        Configuration conf = new Configuration();

        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

        conf.set("hadoop.security.authentication","tbds");
        conf.set("hadoop_security_authentication_tbds_secureid","0Hr0is06in4hxBu2V6vU0DAMFw7BT50pC4V5");
        conf.set("hadoop_security_authentication_tbds_username","wayne");
        conf.set("hadoop_security_authentication_tbds_securekey","ItWu35remPNQFYhe4Szr58Id2PVbg7cM");

        //kerberos
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromSubject(null);

//        DistributedFileSystem hadoopFS = (DistributedFileSystem)FileSystem.get(uri, conf, "wayne"); //指定root用户//这里可能有问题
        DistributedFileSystem hadoopFS = (DistributedFileSystem) FileSystem.get(uri, conf);
        System.out.println("DistributedFileSystem hadoopFS = (DistributedFileSystem)FileSystem.get(uri, conf);");
        FSDataOutputStream fileStream;
        fileStream = hadoopFS.create(new Path(path), false,
                4096,
                (short) 3, 64 * 1024 * 1024L);
        System.out.println("hadoopFS.create");

        /*conf.set("hadoop.security.authentication","tbds");
        conf.set("hadoop_security_authentication_tbds_secureid","0Hr0is06in4hxBu2V6vU0DAMFw7BT50pC4V5");
        conf.set("hadoop_security_authentication_tbds_username","wayne");
        conf.set("hadoop_security_authentication_tbds_securekey","ItWu35remPNQFYhe4Szr58Id2PVbg7cM");*/



        IOUtils.copyBytes(input, fileStream, conf, true);
        System.out.println("IOUtils.copyBytes");
        System.out.println("end");


    }
}
