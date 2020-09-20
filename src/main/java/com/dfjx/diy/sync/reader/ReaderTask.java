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

    public void interruptWriter(){
        this.sync.writerThread.interrupt();
    }

    abstract Object produce();

    abstract Object[] produceBatch();

    public void run(){
        while(isRunning || !this.sync.readerThread.isInterrupted()) {
            if(queue instanceof ProduceOne){
                try {
                    Object object = produce();
                    if(object==null){
                        //正常结束
                        isRunning = false;
                    } else {
                        ((ProduceOne)queue).put(object);
                    }
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    //reset flag
                    resetFLag();
                }
            }else if(queue instanceof ProduceBatch){
                try {
                    Object[] objects = produceBatch();
                    if(objects==null) {
                        //正常结束
                        isRunning = false;
                    } else {
                        ((ProduceBatch)queue).putAll(objects);
                    }
                } catch (InterruptedException e) {
                    //clear interrupt flag
                    //reset flag
                    resetFLag();
                }
            }else {
                throw new RuntimeException(" queue type error, please implement ProduceOne or ProduceBatch");
            }
        }
    }


    //仅为重设flag
    //本类中使用
    private void resetFLag(){
        endOrKill();
    }

    //被终止时使用
    //子类中，调用
    public void endOrKill(){
        this.sync.readerThread.interrupt();
    }

}
