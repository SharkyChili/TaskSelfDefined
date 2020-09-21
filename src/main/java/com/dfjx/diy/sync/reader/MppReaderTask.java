package com.dfjx.diy.sync.reader;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.reader.MppReaderParam;
import com.dfjx.diy.sync.Sync;

import java.sql.*;
import java.util.concurrent.BlockingQueue;

public class MppReaderTask extends ReaderTask{

    @Override
    public void init(Param param, BlockingQueue<Object> queue, Sync sync) {
        super.init(param, queue, sync);

        MppReaderParam mppReaderParam = (MppReaderParam) param;
/*        String driver = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://localhost:5432/pgsqltest";
        String url = "jdbc:postgresql://10.1.3.40:7300/wayne";
        String username = "wayne";
        String password = "wayne";*/
        String driver = mppReaderParam.driver;
        String url = mppReaderParam.url;
        String username = mppReaderParam.username;
        String password = mppReaderParam.password;

        Connection conn = null;
        try {
            Class.forName(driver); // classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = ((MppReaderParam)this.param).sql;
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            //todo 放到内存里吧还是，以后再改
            /*int col = rs.getMetaData().getColumnCount();
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
            System.out.println("============================");*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    Object produce() {
        //todo 需要完成
        return null;
    }

    @Override
    Object[] produceBatch() {
        //不用
        return new Object[0];
    }

}
