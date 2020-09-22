package com.dfjx.diy.sync.writer;

import com.dfjx.diy.param.writer.WriterParam;
import com.dfjx.diy.queue.ConsumeBatch;
import com.dfjx.diy.queue.ConsumeOne;
import com.dfjx.diy.sync.Task;

import java.util.concurrent.TimeUnit;

public abstract class WriterTask extends Task {
    @Override
    public void run() {
        //未结束 && 大小不为0   true || true 正常情况
        //结束   && 大小不为0   false|| true 此时为reader通知结束或者系统强行结束，需要优雅退出的话，就重新进入循环（补充：消费让queue结束才能结束循环）
        //未结束 && 大小为0     true || false 阻塞，等待reader读取数据
        //结束 && 大小为0       false || false 跳出循环，结束
        while(this.sync.isRunning.get() || queue.size() != 0){
            if(queue instanceof ConsumeOne){
                Object object = null;
                try {
                    object = ((ConsumeOne)queue).takeOne(1, TimeUnit.SECONDS);
//                    consume(object);
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    System.out.println(Thread.currentThread().getName() + " : " + "writer 被中断，需优雅退出");
                }
                if(object!=null){
                    consume(object);
                }
            }else if(queue instanceof ConsumeBatch){
                Object[] objects = new Object[0];
                try {
                    objects = ((ConsumeBatch) queue).takeAll(1, TimeUnit.SECONDS);
//                    consumeBatch(objects);
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    System.out.println(Thread.currentThread().getName() + " : " + "writer 被中断，需优雅退出");
                }
                if(objects != null && objects.length!=0){
                    consumeBatch(objects);
                }
            }else {
                throw new RuntimeException(Thread.currentThread().getName() + " : " + " queue type error, please implement ConsumeOne or ConsumeBatch");
            }
        }
        //writer真正结束
        close();
    }

    abstract void consume(Object object);

    abstract void consumeBatch(Object[] objects);
}
