package com.dfjx.diy.sync.reader;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.sync.Sync;

import java.util.concurrent.BlockingQueue;

public class HdfsReaderTask extends ReaderTask {
    @Override
    public  void init(Param param, BlockingQueue<Object> queue, Sync sync){
        this.param = param;
        this.queue = queue;
        this.sync = sync;
    };


    @Override
    Object produce() {
        return null;
    }

    @Override
    Object[] produceBatch() {
        return new Object[0];
    }

    @Override
    public void close() {

    }
}
