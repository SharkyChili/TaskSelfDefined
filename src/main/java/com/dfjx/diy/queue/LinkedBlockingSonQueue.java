package com.dfjx.diy.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingSonQueue<E> extends LinkedBlockingQueue<E>
        implements ProduceOne<E>,ConsumeOne<E>{
}
