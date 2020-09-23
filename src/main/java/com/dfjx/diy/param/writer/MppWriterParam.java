package com.dfjx.diy.param.writer;

public class MppWriterParam extends WriterParam {
    public String driver= "org.postgresql.Driver";

    public String url;
    public String username;
    public String password;

    public String tablename;
    public String fields;

    @Override
    public String getWorkerClass() {
        return "com.dfjx.diy.sync.writer.MppWriterTask";
    }
}
