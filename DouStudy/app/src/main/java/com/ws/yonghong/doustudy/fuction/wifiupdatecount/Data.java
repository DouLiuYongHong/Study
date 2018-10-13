package com.szshuwei.x.myapplication.wifiupdatecount;

import android.net.wifi.ScanResult;
import android.os.SystemClock;

import java.util.List;

/**
 * @author Halohoop
 * 2018/10/10 21:18
 */
public class Data {
    public int index;
    public List<ScanResult> scanResults;
    public long getTs;

    @Override
    public String toString() {
        //index,getTimestamp,timestamp,mac,ssid,rssi
        StringBuffer sb = new StringBuffer();
        if (scanResults.size() == 0) {
            sb.append(index);
            sb.append(",");
            sb.append(getTs);
            sb.append(",");
            sb.append(",");
            sb.append(",");
            sb.append(",");
            sb.append("\n");
            return sb.toString();
        }
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanResult = scanResults.get(i);
            sb.append(index);
            sb.append(",");
            sb.append(getTs);
            sb.append(",");
            sb.append(scanResult.timestamp);
            sb.append(",");
            sb.append(scanResult.BSSID);
            sb.append(",");
            sb.append(scanResult.SSID);
            sb.append(",");
            sb.append(scanResult.level);
            sb.append("\n");
        }
        return sb.toString();
    }
}
