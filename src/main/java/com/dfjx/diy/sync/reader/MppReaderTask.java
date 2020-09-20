package com.dfjx.diy.sync.reader;

import com.dfjx.diy.param.Param;

import java.sql.*;

public class MppReaderTask extends ReaderTask{



    public void run() {
        Connection conn = getConn();
        String sql = "select * from wayne";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //todo
        while(){
            queue.put(new Object());
        }

        //结束之后中断写线程
        interruptWriter();
    }


    /**
     * @return Connection
     * @method getConn() 获取数据库的连接
     */
    public Connection getConn() {
        /*<dependency>
            <groupId>postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>9.1-901-1.jdbc4</version>
        </dependency>*/
        String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://localhost:5432/pgsqltest";
        String url = "jdbc:postgresql://10.1.3.40:7300/wayne";
        String username = "wayne";
        String password = "wayne";
        Connection conn = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

}
