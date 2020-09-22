package com.dfjx.diy.sync;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.reader.ReaderParam;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Task implements Runnable {
    public Param param;

    public BlockingQueue<Object> queue;

    public Sync sync;

    public final AtomicBoolean stopped = new AtomicBoolean(false);

    public  void init(Param param, BlockingQueue<Object> queue, Sync sync){
        this.param = param;
        this.queue = queue;
        this.sync = sync;
    };

    public abstract void close();
}
