package com.dfjx.diy.queue;

import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;

public interface ConsumeBatch<E>  {
    public Object[] takeAll() throws InterruptedException;
}
