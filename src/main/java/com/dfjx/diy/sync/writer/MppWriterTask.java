package com.dfjx.diy.sync.writer;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.sync.Sync;

import java.util.concurrent.BlockingQueue;

public class MppWriterTask extends WriterTask {
    @Override
    public  void init(Param param, BlockingQueue<Object> queue, Sync sync){
        this.param = param;
        this.queue = queue;
        this.sync = sync;
    };

    @Override
    void consume(Object object) {

    }

    @Override
    void consumeBatch(Object[] objects) {

    }

    @Override
    public void close() {

    }
}
