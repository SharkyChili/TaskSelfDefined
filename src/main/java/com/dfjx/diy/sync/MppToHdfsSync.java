package com.dfjx.diy.sync;

import com.dfjx.diy.conf.Conf;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;
import com.dfjx.diy.queue.TakeAllQueue;
import com.dfjx.diy.sync.reader.MppReaderTask;
import com.dfjx.diy.sync.writer.HdfsWriterTask;

import java.util.concurrent.BlockingQueue;

public class MppToHdfsSync extends AbstractSync {

    @Override
    public void init(Conf conf) {
        ReaderParam readerParam = conf.getReaderParam();
        WriterParam writerParam = conf.getWriterParam();

        BlockingQueue<Object> queue = new TakeAllQueue<>(1000);
        this.queue = queue;

        MppReaderTask mppReaderTask = new MppReaderTask();
        mppReaderTask.init(readerParam,queue);
        this.readerTask = mppReaderTask;

        HdfsWriterTask hdfsWriterTask = new HdfsWriterTask();
        hdfsWriterTask.init(writerParam,queue);
        this.writerTask = hdfsWriterTask;
    }

    @Override
    public void start() {
        new Thread(this.readerTask).start();
        new Thread(this.writerTask).start();
    }
}
