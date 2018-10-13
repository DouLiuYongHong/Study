package com.szshuwei.x.myapplication.wifiupdatecount;

/**
 * @author Halohoop
 * 2018/10/10 23:02
 */
public class ReportData {
    public int preIndex;
    public int index;
    public int step;
    public long stepTime;
    public int countAccept;
    public float proportion;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(preIndex);
        sb.append(",");
        sb.append(index);
        sb.append(",");
        sb.append(step);
        sb.append(",");
        sb.append(stepTime);
        sb.append(",");
        sb.append(countAccept);
        sb.append(",");
        sb.append(proportion);
        sb.append("\n");
        return sb.toString();
    }
}
