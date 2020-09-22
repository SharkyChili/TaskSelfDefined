package com.dfjx.diy.sync.reader;

import com.alibaba.fastjson.JSONObject;
import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.reader.MppReaderParam;
import com.dfjx.diy.sync.Sync;

import java.sql.*;
import java.util.concurrent.BlockingQueue;

public class MppReaderTask extends ReaderTask{
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

    @Override
    public void init(Param param, BlockingQueue<Object> queue, Sync sync) {
        super.init(param, queue, sync);
//        this.param = param;

        MppReaderParam mppReaderParam = (MppReaderParam) this.param;
/*        String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://localhost:5432/pgsqltest";
        String url = "jdbc:postgresql://10.1.3.40:7300/wayne";
        String username = "wayne";
        String password = "wayne";*/
        String driver = mppReaderParam.driver;
        String url = mppReaderParam.url;
        String username = mppReaderParam.username;
        String password = mppReaderParam.password;
        String sql = mppReaderParam.sql;

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



        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println(Thread.currentThread().getName() + " : " + "mpp执行语句，拿到rs成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    Object produce() {
        try {
            int columnCount = rs.getMetaData().getColumnCount();
            if(rs.next()){
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i < columnCount + 1; i++) {
                    String dbField = rs.getMetaData().getColumnLabel(i);
                    String key = underlineToCamel(dbField);
                    Object col = rs.getObject(i);
                    jsonObject.put(key,col);
                }
                System.out.println(Thread.currentThread().getName() + " : " + "mpp produce:" + jsonObject.toJSONString());
                return jsonObject;
            }

            System.out.println(Thread.currentThread().getName() + " : " + "mpp produce: 无更多数据，结束");
            return null;

        } catch (SQLException e) {
            System.out.println(Thread.currentThread().getName() + " : " + "mpp produce: 异常："+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    Object[] produceBatch() {
        //不用
        return new Object[0];
    }

    public void close(){
        try {
            if(rs!=null){ rs.close(); }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(pstmt!=null){pstmt.close();}
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
