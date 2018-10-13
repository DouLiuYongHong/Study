package com.szshuwei.x.myapplication.wifiupdatecount.bean;

import java.util.List;

public class WifiCountBean {

    public static String COUNT_ACCEPT = "countAccept";
    public static String PROPORTION = "proportion";
    public static String STEP_TIME = "stepTime";
    private int stepTime;
    private int countAccept;
    private float proportion;


    private List<Integer> listCountAccept = null;
    private List<Float> listProportion = null;

    public WifiCountBean() {
    }

    public List<Integer> getListCountAccept() {
        return listCountAccept;
    }

    public void setListCountAccept(List<Integer> listCountAccept) {
        this.listCountAccept = listCountAccept;
    }

    public List<Float> getListProportion() {
        return listProportion;
    }

    public void setListProportion(List<Float> listProportion) {
        this.listProportion = listProportion;
    }

    public WifiCountBean(String stepTime, String countAccept, String proportion) {
        this.stepTime = Integer.valueOf(stepTime);
        this.countAccept = Integer.valueOf(countAccept);
        this.proportion = Float.valueOf(proportion);
    }


    public int getCountAccept() {
        return countAccept;
    }

    public void setCountAccept(int countAccept) {
        this.countAccept = countAccept;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }
}