package com.dfjx.diy.sync;

import com.dfjx.diy.conf.Conf;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;
import com.dfjx.diy.queue.LinkedBlockingSonQueue;
import com.dfjx.diy.queue.TakeAllQueue;
import com.dfjx.diy.sync.reader.MppReaderTask;
import com.dfjx.diy.sync.reader.ReaderTask;
import com.dfjx.diy.sync.writer.HdfsWriterTask;
import com.dfjx.diy.sync.writer.WriterTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sync {
    public BlockingQueue<Object> queue;
    public final AtomicBoolean isRunning = new AtomicBoolean(true);

    public ReaderTask readerTask;
    public WriterTask writerTask;

    public Thread readerThread;
    public Thread writerThread;


    /**
     * 通过conf拿到两种参数，然后初始化线程
     * @param conf
     */
    public void init(Conf conf){
        ReaderParam readerParam = conf.getReaderParam();
        WriterParam writerParam = conf.getWriterParam();

        this.queue = determineQueue(conf);

//        ReaderTask readerTask;
//        WriterTask writerTask;
        try {
            String readerClassName = readerParam.getWorkerClass();
            Class<?> readerClass = Class.forName(readerClassName);
            readerTask = (ReaderTask)readerClass.newInstance();

            String writerClassName = writerParam.getWorkerClass();
            Class<?> writerClass = Class.forName(writerClassName);
            writerTask = (WriterTask) writerClass.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        readerTask.init(readerParam,queue,this);
        this.readerThread = new Thread(readerTask,"*************reader线程：");

        writerTask.init(writerParam,queue,this);
        this.writerThread = new Thread(writerTask,"-------------writer线程");
    }

    public void start(){
        this.readerThread.start();
        this.writerThread.start();
    }

    public void stop(){
        this.isRunning.set(false);
    }

    private BlockingQueue<Object> determineQueue(Conf conf){
        if(conf.getReaderParam().getClass().getSimpleName().contains("Mpp")
        && conf.getWriterParam().getClass().getSimpleName().contains("Hdfs")){
            //非公平锁，这样才有one--batch的意义
            return new TakeAllQueue<Object>(50);
        }
        return new LinkedBlockingSonQueue<Object>(50);
    }
}
