package com.dfjx.mpp;



public class MPPTest {
    public static void main(String args[]) {
        MppDemo op = new MppDemo();
        op.getAll();
        op.insert(new Pojo("a","b","c"));
        op.insert(new Pojo("d","e","f"));
        op.getAll();
//        op.update(new Pojo("g","e","h"));
//        op.delete("a");
//        op.getAll();
    }
}