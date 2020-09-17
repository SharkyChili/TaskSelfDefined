package com.dfjx.diy.conf;

import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;

public interface Conf {


    public ReaderParam getReaderParam();

    public WriterParam getWriterParam();
}
