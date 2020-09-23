package com.dfjx.diy.sync.writer;

import com.alibaba.fastjson.JSONObject;
import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.writer.MppWriterParam;
import com.dfjx.diy.sync.Sync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class MppWriterTask extends WriterTask {
    PreparedStatement ps;
    Connection conn;
    String sql;
    List<String> fieldList;

    @Override
    public  void init(Param param, BlockingQueue<Object> queue, Sync sync){
        super.init(param,queue,sync);

        MppWriterParam mppWriterParam = (MppWriterParam)this.param;
        String driver = mppWriterParam.driver;
        String url = mppWriterParam.url;
        String username = mppWriterParam.username;
        String password = mppWriterParam.password;
        String tablename = mppWriterParam.tablename;
        String fields = mppWriterParam.fields;



        conn = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
            System.out.println(Thread.currentThread().getName() + " : " + "mpp获取连接成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = buildSql(tablename,fields);

        try {
            ps = conn.prepareStatement(sql);
            System.out.println(Thread.currentThread().getName() + " : " + "mpp执行语句，拿到rs成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    private String buildSql(String tablename, String fields){
        //todo
        return null;

    }



    @Override
    void consume(Object object) {
        try {
            JSONObject jsonObject = (JSONObject) object;
            String[] values = fieldList.stream().map(
                    field -> jsonObject.getString(field)
            ).toArray(String[]::new);
            ps.execute(sql,values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    void consumeBatch(Object[] objects) {
        //do nothing
    }

    @Override
    public void close() {
        try {
            if(ps!=null){ps.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(conn!=null){conn.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
