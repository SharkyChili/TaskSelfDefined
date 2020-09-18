package com.dfjx.diy.sync;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.reader.ReaderParam;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Task implements Runnable {
    public Param param;

    public BlockingQueue<Object> queue;

    public final AtomicBoolean stopped = new AtomicBoolean(false);

    public  void init(Param param, BlockingQueue<Object> queue){
        this.param = param;
        this.queue = queue;
    };
}
