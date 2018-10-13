package com.szshuwei.x.myapplication.wifiupdatecount.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class NumberUtil {

    public static String persenNum(int diliverNum, int queryNum) {
        NumberFormat mNumberFormat = NumberFormat.getInstance();
        mNumberFormat.setMaximumFractionDigits(2);
        return mNumberFormat.format((float) diliverNum / (float) queryNum * 100);
    }

    public static float averageNum(List<Integer> listInteger) {
        if (listInteger == null || listInteger.isEmpty()) {
            return 0.0f;
        }
        float sumNum = 0;
        for (int i = 0; i < listInteger.size(); i++) {
            sumNum += listInteger.get(i);
        }
        DecimalFormat df = new DecimalFormat("0.0000000000");//格式化小数
        return Float.parseFloat(df.format((float) sumNum / listInteger.size()));
    }

    public static float averageFloat(List<Float> listFloat) {
        if (listFloat == null || listFloat.isEmpty()) {
            return 0.0f;
        }
        float sumNum = 0;
        for (int i = 0; i < listFloat.size(); i++) {
            sumNum += listFloat.get(i);
        }
        DecimalFormat df = new DecimalFormat("0.0000000000");//格式化小数
        return Float.parseFloat(df.format((float) sumNum / listFloat.size()));
    }


    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n
    public static float arianceInteger(List<Integer> listInteger) {
        if (listInteger == null || listInteger.isEmpty()) {
            return 0.0f;
        }
        List<Float> listFloat = new ArrayList<>();
        for (Integer integer : listInteger) {
            listFloat.add(Float.valueOf(integer));
        }

        Float[] x = listFloat.toArray(new Float[0]);
        int m = x.length;
        float sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        float dAve = sum / m;//求平均值
        float dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n
    public static float arianceFloat(List<Float> listFloat) {
        if (listFloat == null || listFloat.isEmpty()) {
            return 0.0f;
        }
        Float[] x = listFloat.toArray(new Float[0]);

        int m = x.length;
        float sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        float dAve = sum / m;//求平均值
        float dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    //标准差σ=sqrt(s^2)
    public static float standardDiviationInteger(List<Integer> listInteger) {
        if (listInteger == null || listInteger.isEmpty()) {
            return 0.0f;
        }

        List<Float> listFloat = new ArrayList<>();
        for (Integer integer : listInteger) {
            listFloat.add(Float.valueOf(integer));
        }

        Float[] x = listFloat.toArray(new Float[0]);

        int m = x.length;
        float sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        float dAve = sum / m;//求平均值
        float dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return (float) Math.sqrt(dVar / m);
    }

    //标准差σ=sqrt(s^2)
    public static float standardDiviationFloat(List<Float> listInteger) {
        if (listInteger == null || listInteger.isEmpty()) {
            return 0.0f;
        }
        Float[] x = listInteger.toArray(new Float[0]);

        int m = x.length;
        float sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        float dAve = sum / m;//求平均值
        float dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return (float) Math.sqrt(dVar / m);
    }
}
