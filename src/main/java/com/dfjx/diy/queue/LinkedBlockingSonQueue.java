package com.dfjx.diy.queue;

import java.util.concurrent.LinkedBlockingQueue;

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

    public E takeOne() throws InterruptedException {
        return take();
    }

    public void putOne(E e) throws InterruptedException {
        put(e);
    }
}
