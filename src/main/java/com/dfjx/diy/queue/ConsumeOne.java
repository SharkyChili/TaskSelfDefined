package com.dfjx.diy.queue;

import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;

public interface ConsumeOne<E>  {
    public E takeOne() throws InterruptedException;
}
