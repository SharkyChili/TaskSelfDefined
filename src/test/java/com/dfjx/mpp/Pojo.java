package com.dfjx.mpp;



import java.sql.ResultSet;
import java.sql.SQLException;

public class Pojo{
    private String a;
    private String b;
    private String c;

    public Pojo() {
    }

    public Pojo(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                '}';
    }


    public Object getI(int i) {
        switch (i){
            case 1:
                return a;
            case 2:
                return b;
            case 3:
                return c;
            default:
                throw new RuntimeException("参数数目不匹配");
        }
    }

    public void fromResultSet(ResultSet resultSet) {
        try {
            this.a = resultSet.getString("a");
            this.b = resultSet.getString("b");
            this.c = resultSet.getString("c");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
