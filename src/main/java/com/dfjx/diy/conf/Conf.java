package com.dfjx.diy.conf;

import com.dfjx.diy.param.Param;
import com.dfjx.diy.param.Type;
import com.dfjx.diy.param.reader.ReaderParam;
import com.dfjx.diy.param.writer.WriterParam;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Conf {

    public ReaderParam readerParam;

    public WriterParam writerParam;

    public ReaderParam getReaderParam() {
        return readerParam;
    }

    public void setReaderParam(ReaderParam readerParam) {
        this.readerParam = readerParam;
    }

    public WriterParam getWriterParam() {
        return writerParam;
    }

    public void setWriterParam(WriterParam writerParam) {
        this.writerParam = writerParam;
    }


    String sourceType = "";
    Map<String, String> sourceAttrMap = new HashMap<>();
    String targetType = "";
    Map<String, String> targetAttrMap = new HashMap<>();

    public Param buildParam(String sType, Map<String, String> attrMap, Type type) {
        //利用反射找到类，然后填充属性
        if (sType == null || "".equals(sType)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(sType.length());
        sb.append("com.dfjx.diy.param.");
        sb.append(type.getType().toLowerCase());
        sb.append(".");

        for (int i = 0; i < sType.length(); i++) {
            sb.append(i == 0 ? Character.toUpperCase(sType.charAt(i)) : sType.charAt(i));
        }
        sb.append(type.getType());
        sb.append("Param");

        Class<?> aClass;
        Param param = null;
        try {
            aClass = Class.forName(sb.toString());
            param = (Param) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            for (Map.Entry<String, String> entry : attrMap.entrySet()) {
                Field field = param.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(param, entry.getValue());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return param;
    }
}
