package com.dfjx.mpp;

import java.sql.*;

public class MppDemo {
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

    /**
     * @return int 成功插入数据条数
     * @method insert(Student student) 往表中插入数据
     */
    public int insert(Pojo pojo) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into wayne (a,b,c) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, pojo.getA());
            pstmt.setString(2, pojo.getB());
            pstmt.setString(3, pojo.getC());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @return int 成功删除表中数据条数
     * @method delete(Student student) 删除表中数据
     */
    public int delete(String a) {
        Connection conn = getConn();
        int i = 0;
        String sql = "delete from wayne where a='" + a + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @return int 成功更改表中数据条数
     * @method update(Student student) 更改表中数据
     */
    public int update(Pojo pojo) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update wayne set a='" + pojo.getA() + "' where b='" + pojo.getB() + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @return Integer 查询并打印表中数据
     * @method Integer getAll() 查询并打印表中数据
     */
    public Integer getAll() {
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
        return null;
    }


}
