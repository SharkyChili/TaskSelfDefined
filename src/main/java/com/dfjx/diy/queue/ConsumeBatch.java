package com.dfjx.diy.queue;

import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface ConsumeBatch<E>  {
    public Object[] takeAll(long timeout, TimeUnit unit) throws InterruptedException;
}
