package com.dfjx.diy.sync.reader;

import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.queue.ConsumeBatch;
import com.dfjx.diy.queue.ConsumeOne;
import com.dfjx.diy.queue.ProduceBatch;
import com.dfjx.diy.queue.ProduceOne;
import com.dfjx.diy.sync.Task;
import com.dfjx.diy.sync.writer.WriterTask;

public abstract class ReaderTask extends Task {

    public volatile boolean isRunning = true;

    public void stop(){
        isRunning = false;
    }

    public void interruptWriter(){
        this.sync.writerThread.interrupt();
    }

    abstract Object produce();

    abstract Object[] produceBatch();

    public void run(){
        //正常流程，走到produce为空时，自动结束
        //中断流程
        //被中断，就应该停止生产，并通知writer结束
        while(isRunning) {
            if(queue instanceof ProduceOne){
                try {
                    Object object = produce();
                    if(object==null){
                        //正常结束，用于结束线程
                        isRunning = false;
                    } else {
                        ((ProduceOne)queue).putOne(object);
                    }
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    //被中断，就该停止生产
                    isRunning = false;
                }
            }else if(queue instanceof ProduceBatch){
                try {
                    Object[] objects = produceBatch();
                    if(objects==null) {
                        //正常结束，用于结束线程
                        isRunning = false;
                    } else {
                        ((ProduceBatch)queue).putAll(objects);
                    }
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    //被中断，就该停止生产
                    isRunning = false;
                }
            }else {
                throw new RuntimeException(" queue type error, please implement ProduceOne or ProduceBatch");
            }
        }
        //通知writer结束
        interruptWriter();
    }
}
