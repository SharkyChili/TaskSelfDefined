package com.dfjx.diy.sync;

import com.dfjx.diy.conf.Conf;

public interface DataSyncInterface {


    /**
     * 通过conf拿到两种参数，然后初始化线程
     * @param conf
     */
    void init(Conf conf);

    void start();
}
