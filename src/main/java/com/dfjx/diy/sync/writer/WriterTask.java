package com.dfjx.diy.sync.writer;

import com.dfjx.diy.param.writer.WriterParam;
import com.dfjx.diy.queue.ConsumeBatch;
import com.dfjx.diy.queue.ConsumeOne;
import com.dfjx.diy.sync.Task;

public abstract class WriterTask extends Task {
    @Override
    public void run() {
        //未被中断 && 大小不为0   true || true 正常情况
        //被中断   && 大小不为0   false|| true 此时为reader通知结束或者系统强行结束，需要优雅退出的话，就重新设置flag进入循环
        //未被中断 && 大小为0     true || false 阻塞，等待被中断进入第四种状态
        //被中断 && 大小为0       false || false 跳出循环，结束
        while(!this.sync.writerThread.isInterrupted() || queue.size() != 0){
            if(queue instanceof ConsumeOne){
                try {
                    Object object = ((ConsumeOne)queue).takeOne();
                    consume(object);
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    //reset flag
                    System.out.println("writer 被中断，需优雅退出");
                    this.sync.writerThread.interrupt();
                }
            }else if(queue instanceof ConsumeBatch){
                try {
                    Object[] objects = ((ConsumeBatch) queue).takeAll();
                    consumeBatch(objects);
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    //reset flag
                    System.out.println("writer 被中断，需优雅退出");
                    this.sync.writerThread.interrupt();
                }
            }else {
                throw new RuntimeException(" queue type error, please implement ConsumeOne or ConsumeBatch");
            }
        }
        //writer真正结束
        close();
    }

    abstract void consume(Object object);

    abstract void consumeBatch(Object[] objects);
}
