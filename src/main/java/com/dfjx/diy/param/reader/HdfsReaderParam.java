package com.dfjx.diy.param.reader;

public class HdfsReaderParam extends ReaderParam {
    public String url;
    public String secureid;
    public String username;
    public String securekey;
    public String path;
    public String filename;

    @Override
    public String getWorkerClass() {
        return "com.dfjx.diy.sync.reader.HdfsReaderTask";
    }
}
