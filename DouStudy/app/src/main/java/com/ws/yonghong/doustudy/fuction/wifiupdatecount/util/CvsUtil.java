package com.szshuwei.x.myapplication.wifiupdatecount.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class CvsUtil {

    private static String TAG = "CvsUtil";

    public static void writeCsvFile(String folder, String fileName, List<?> mlistBlueResult) {
        writeCsvFile(folder, fileName, mlistBlueResult, 0);
    }

    public static void writeCsvFile(String folder, String fileName, List<?> mlistBlueResult, int countNum) {

        if (mlistBlueResult.isEmpty()) {
            return;
        }
        try {
            String basePath = FileUtil.folderPath;
            File file = new File(basePath  + File.separator + fileName + "-" + countNum + ".csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            // 添加头部名称
            StringBuffer keyBuffer = new StringBuffer();
            StringBuffer valueBuffer = new StringBuffer();
            //添加有关的目录文件
            Field[] fields = mlistBlueResult.get(0).getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                keyBuffer.append(fields[i].getName());
                if (i != fields.length - 1) {
                    keyBuffer.append(",");
                }
            }

            if (file != null && file.length() == 0) {
                bw.write(keyBuffer.toString());
                bw.newLine();
            }

            for (int j = 0; j < mlistBlueResult.size(); j++) {
                for (int i = 0; i < fields.length; i++) {
                    valueBuffer.append(getFieldValueByName(fields[i].getName(), mlistBlueResult.get(j)));
                    if (i != fields.length - 1) {
                        valueBuffer.append(",");
                    }
                }
                bw.write(valueBuffer.toString());
                bw.newLine();
                valueBuffer.setLength(0);
            }
            bw.close();
            Log.i("TAG", "csv文件创建成功。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据属性名获取属性值
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     */
    private static Object[] getFiledValues(Object o) {
        String[] fieldNames = getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }

}