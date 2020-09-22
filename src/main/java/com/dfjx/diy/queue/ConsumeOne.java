package com.dfjx.diy.queue;

import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface ConsumeOne<E>  {
    public E takeOne(long timeout, TimeUnit unit) throws InterruptedException;
}
