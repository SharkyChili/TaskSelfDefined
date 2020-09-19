package com.dfjx.diy.conf;

import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;

import java.util.HashMap;
import java.util.Map;

public class Conf {

    public ReaderParam readerParam;

    public WriterParam writerParam;

    public ReaderParam getReaderParam() {
        return readerParam;
    }

    public void setReaderParam(ReaderParam readerParam) {
        this.readerParam = readerParam;
    }

    public WriterParam getWriterParam() {
        return writerParam;
    }

    public void setWriterParam(WriterParam writerParam) {
        this.writerParam = writerParam;
    }


    String sourceType = "";
    Map<String, String> sourceAttrMap = new HashMap<>();
    String targetType = "";
    Map<String, String> targetAttrMap = new HashMap<>();
}
