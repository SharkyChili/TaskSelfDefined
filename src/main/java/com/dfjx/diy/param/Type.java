package com.dfjx.diy.param;

public enum  Type {
    Reader("Reader"),Writer("Writer");

    String type;

    Type(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
