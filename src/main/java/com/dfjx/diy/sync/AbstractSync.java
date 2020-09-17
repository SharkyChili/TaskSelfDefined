package com.dfjx.diy.sync;

import com.dfjx.diy.conf.Conf;
import com.dfjx.diy.sync.reader.ReaderTask;
import com.dfjx.diy.sync.writer.WriterTask;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractSync implements DataSyncInterface{
    public BlockingQueue<Object> queue;

    public ReaderTask readerTask;

    public WriterTask writerTask;
}
