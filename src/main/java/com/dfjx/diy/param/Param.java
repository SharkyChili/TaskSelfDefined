package com.dfjx.diy.param;


import java.lang.reflect.Field;

public abstract class Param {
    public abstract String getWorkerClass();

    public void overWrite(Param param) {
        try {
            Field[] declaredFields = param.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                String newFieldValue = (String)field.get(param);

                if(newFieldValue !=null && !"".equals(newFieldValue)){
                    Field originField = this.getClass().getDeclaredField(fieldName);
                    originField.setAccessible(true);
                    originField.set(this,newFieldValue);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
