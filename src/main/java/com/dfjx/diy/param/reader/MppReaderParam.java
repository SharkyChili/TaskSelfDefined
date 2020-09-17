package com.dfjx.diy.param.reader;

import com.dfjx.diy.param.Param;

public class MppReaderParam implements ReaderParam {

    String driver = "org.postgresql.Driver";
    //        String url = "jdbc:postgresql://localhost:5432/pgsqltest";
    String url = "jdbc:postgresql://10.1.3.40:7300/wayne";
    String username = "wayne";
    String password = "wayne";



    public void overWrite(Param param) {
        MppReaderParam param1 = (MppReaderParam) param;
    }
}
