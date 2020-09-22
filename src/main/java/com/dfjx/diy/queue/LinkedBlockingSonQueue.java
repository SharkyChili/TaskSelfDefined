package com.dfjx.diy.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author wayne
 * @date 2020.09.20
 * @param <E>
 */
public class LinkedBlockingSonQueue<E> extends LinkedBlockingQueue<E>
        implements ProduceOne<E>,ConsumeOne<E>{

    public LinkedBlockingSonQueue(int capacity) {
        super(capacity);
    }

    public E takeOne(long timeout, TimeUnit unit) throws InterruptedException {
        return poll(timeout, unit);
    }

    public void putOne(E e) throws InterruptedException {
        put(e);
    }
}
