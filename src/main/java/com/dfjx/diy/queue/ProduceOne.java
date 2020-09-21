package com.dfjx.diy.queue;

import java.util.AbstractQueue;
import java.util.concurrent.BlockingQueue;

public interface ProduceOne<E> {
    public void putOne(E e) throws InterruptedException;
}
