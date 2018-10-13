package com.szshuwei.x.myapplication.wifiupdatecount.bean;

public class WifiReportBean {

    private int stepTime;
    float maxNum;
    float minNum;
    float average;
    float ariance;
    float standard;

    public WifiReportBean(float maxNum, float minNum, float average, float ariance, float standard) {
        this.maxNum = maxNum;
        this.minNum = minNum;
        this.average = average;
        this.ariance = ariance;
        this.standard = standard;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public float getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(float maxNum) {
        this.maxNum = maxNum;
    }

    public float getMinNum() {
        return minNum;
    }

    public void setMinNum(float minNum) {
        this.minNum = minNum;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getAriance() {
        return ariance;
    }

    public void setAriance(float ariance) {
        this.ariance = ariance;
    }

    public float getStandard() {
        return standard;
    }

    public void setStandard(float standard) {
        this.standard = standard;
    }
}