package com.dfjx.diy.queue;

import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;

public interface ProduceBatch<E>  {
    public void putAll(Object[] objs) throws InterruptedException;
}
