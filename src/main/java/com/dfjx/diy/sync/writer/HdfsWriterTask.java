package com.dfjx.diy.sync.writer;


public class HdfsWriterTask extends WriterTask {

    @Override
    void consume(Object object) {
        //do nothing
    }

    @Override
    void consumeBatch(Object[] objects) {
        //todo 需要完成
    }


/*
    conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));

//参数 tbds_username，tbds_username，tbds_securekey通过工作流自定义参数传入

conf.set("hadoop.security.authentication", "tbds");
conf.set("hadoop_security_authentication_tbds_username",tbds_username);
conf.set("hadoop_security_authentication_tbds_secureid",tbds_secureid);
conf.set("hadoop_security_authentication_tbds_securekey",tbds_securekey);
UserGroupInformation.setConfiguration( conf );
UserGroupInformation.loginUserFromSubject(null);
*/

}
