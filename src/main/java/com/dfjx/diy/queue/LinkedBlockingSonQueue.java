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
}
