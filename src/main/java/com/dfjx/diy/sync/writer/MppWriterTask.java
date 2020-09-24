package com.dfjx.diy.sync.writer;

import com.alibaba.fastjson.JSONObject;
import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.writer.MppWriterParam;
import com.dfjx.diy.sync.Sync;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        fieldList = Stream.of(fields.split("\\,"))
                .collect(Collectors.toList());

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
//            System.out.println("sql: "+ sql);
            System.out.println(Thread.currentThread().getName() + " : " + "mpp拿到PreparedStatement成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

//    public static void main(String[] args) {
//        String dwttab = new MppWriterTask().buildSql("dwttab", "dept,emp_id");
//        System.out.println(dwttab);
//    }


    private String buildSql(String tablename, String fields){
//        String sql = "insert into wayne(a, b, c) values(?, ?, ?);";
        String[] strings = fields.split("\\,");
        String locations = Stream.of(fields.split("\\,"))
                .map(str -> "?")
                .collect(Collectors.joining(", "));

        String sql = "insert into " +
                tablename + "(" +
                fields + ")" +
                " values(" +
                locations +
                ");";
        return sql;
    }



    @Override
    void consume(Object object) {
        try {
            JSONObject jsonObject = (JSONObject) object;
            System.out.println(jsonObject.toJSONString());
            for (int i = 0; i < fieldList.size(); i++) {
                Object value = jsonObject.get(underlineToCamel(fieldList.get(i)));
                System.out.println("ps.setObject");
                System.out.println(i+1 + " : " + underlineToCamel(fieldList.get(i)) + " : " + value);
                ps.setObject(i+1, value);
            }
            ps.executeUpdate();
            System.out.println(Thread.currentThread().getName() + "mpp写入成功 " + jsonObject.toJSONString());
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

    private String underlineToCamel(String param){
        if(param == null || "".equals(param)){
            return "";
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if('_' == c){
                if(++i < len){
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
