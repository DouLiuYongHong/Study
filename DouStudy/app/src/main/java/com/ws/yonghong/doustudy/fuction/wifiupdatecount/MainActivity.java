package com.szshuwei.x.myapplication.wifiupdatecount;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shuwei.location.executor.BgSerialTaskExecutor;
import com.szshuwei.x.myapplication.wifiupdatecount.bean.WifiCountBean;
import com.szshuwei.x.myapplication.wifiupdatecount.bean.WifiReportBean;
import com.szshuwei.x.myapplication.wifiupdatecount.contans.Contants;
import com.szshuwei.x.myapplication.wifiupdatecount.util.CSVReader;
import com.szshuwei.x.myapplication.wifiupdatecount.util.CvsUtil;
import com.szshuwei.x.myapplication.wifiupdatecount.util.FileUtil;
import com.szshuwei.x.myapplication.wifiupdatecount.util.NumberUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private WifiManager mWifiManager;
    private WifiManager.WifiLock mHalohoop;
    private View mBtnStart;
    private View mBtnStop;
    private Handler mHandler;
    private Counter mCounter;
    private TextView mTvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
        mBtnStop = findViewById(R.id.btn_stop);
        mTvNotes = findViewById(R.id.tv_notes);
        mBtnStop.setOnClickListener(this);
        mBtnStop.setVisibility(View.INVISIBLE);

        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mHalohoop = mWifiManager.createWifiLock("Halohoop");
        mHalohoop.acquire();
        BgSerialTaskExecutor executer = new BgSerialTaskExecutor("halohoop");
        mHandler = executer.getHandler();
        mCounter = new Counter();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 100);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHalohoop != null) {
            if (mHalohoop.isHeld()) {
                mHalohoop.release();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mBtnStart.setEnabled(false);
                mBtnStop.setEnabled(true);
                //开始获取逻辑
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final int getTimes = 3600;//3600次，相当于五个小时
                        for (int i = 0; i < getTimes; i++) {
                            final int times = i + 1;
                            mCounter.startCount();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "获取" + times + "次完成", Toast.LENGTH_SHORT).show();
                                    mTvNotes.setText("获取" + times + "次完成");
                                }
                            });
                            SystemClock.sleep(5000);
                        }
                        mCounter.writeResult();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "写入" + getTimes + "次完成", Toast.LENGTH_SHORT).show();
                                mTvNotes.setText("写入" + getTimes + "次完成");
                            }
                        });
                        mCounter.getReport();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "生成报告完成", Toast.LENGTH_SHORT).show();
                                mTvNotes.setText("生成报告完成");
                                mBtnStart.setEnabled(true);
                            }
                        });
                        final boolean isSuc = mCounter.writeReportRateDataFinal();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuc) {
                                    Toast.makeText(MainActivity.this, "生成Cvs概率报告完成", Toast.LENGTH_SHORT).show();
                                    mTvNotes.setText("生成报告完成");
                                    mBtnStart.setEnabled(true);
                                } else {
                                    Toast.makeText(MainActivity.this, "生成Cvs概率报告失败", Toast.LENGTH_SHORT).show();
                                    mTvNotes.setText("生成报告失败");
                                    mBtnStart.setEnabled(true);
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.btn_stop:
                mBtnStart.setEnabled(true);
                mBtnStop.setEnabled(false);
                break;
            default:
                break;
        }
    }


    class Counter {

        private final ArrayList<Data> mList;

        public Counter() {
            index = 0;
            mList = new ArrayList<>();
        }
        int index;

        void startCount(){
            Data data = new Data();
            data.index = ++index;
            data.scanResults = mWifiManager.getScanResults();
            data.getTs = SystemClock.elapsedRealtime();
            mList.add(data);
        }
        void writeResult() {
            final ArrayList<Data> list = mList;
            ///adb pull mnt/sdcard/wifiupdatecount .
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator
                    + "wifiupdatecount");
            dir.mkdirs();
            File file = new File(dir, "counter.csv");
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedWriter bufw = null;
            try {
                bufw = new BufferedWriter(new FileWriter(file, true));
                for (int i = 0; i < list.size(); i++) {
                    Data data = list.get(i);
                    if (i == 0) {
                        //先写标题
                        bufw.write("index,getTimestamp,timestamp,mac,ssid,rssi");
                        bufw.newLine();
                    }
                    bufw.write(data.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufw != null) {
                    try {
                        bufw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @SuppressLint("NewApi")
        List<ReportData> getReportList(int step) {
            final ArrayList<Data> list = mList;
            List<ReportData> reportDataList = new ArrayList<>();
            for (int i = 0; i < list.size(); i += step) {
                if (0 == i) {
                    continue;
                }
                int index = i;
                int preIndex = i - step;
                Data data = list.get(index);
                Data preData = list.get(preIndex);
                final long preGetTs = preData.getTs;
                List<ScanResult> scanResults = data.scanResults;
                List<ScanResult> preScanResults = preData.scanResults;
                Map<String, ScanResult> preMacsArrayMap = new ArrayMap<>();
                for (int k = 0; k < preScanResults.size(); k++) {
                    ScanResult scanResult = preScanResults.get(k);
                    preMacsArrayMap.put(scanResult.BSSID, scanResult);
                }
                long stepTime = step * 5000L;
                int countAccept = 0;
                for (int j = 0; j < scanResults.size(); j++) {
                    ScanResult scanResult = scanResults.get(j);
                    if (!preMacsArrayMap.containsKey(scanResult.BSSID)) {
                        continue;
                    }
                    long ts = scanResult.timestamp / 1000;
                    long distanceTimestamp = ts - preGetTs;
                    if (distanceTimestamp >= 0) {
                        if (distanceTimestamp < stepTime) {
                            ///mnt/sdcard/wifiupdatecount
                            countAccept++;
                        } else {
                            //eat it
                        }
                    } else {
                        //eat it
                    }
                }
                ReportData reportData = new ReportData();
                reportData.preIndex = preIndex;
                reportData.index = index;
                reportData.step = step;
                reportData.stepTime = stepTime;
                reportData.countAccept = countAccept;
                reportData.proportion = countAccept == 0 ? 0f : ((float) countAccept) / ((float) scanResults.size());
                reportDataList.add(reportData);
            }
            return reportDataList;
        }

        void getReport() {
            for (int i = 1; i <= 10; i++) {
                final int step = i;
                final int steptime = i * 5000;

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator
                        + "wifiupdatecount");
                dir.mkdirs();
                File file = new File(dir, "report_" + steptime + ".csv");
                if (file.exists()) {
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedWriter bufw = null;
                try {
                    bufw = new BufferedWriter(new FileWriter(file, true));
                    List<ReportData> reportList = getReportList(step);
                    for (int j = 0; j < reportList.size(); j++) {
                        if (j == 0) {
                            //先写标题
                            bufw.write("preIndex,index,step,stepTime,countAccept,proportion");
                            bufw.newLine();
                        }
                        ReportData reportData = reportList.get(j);
                        String line = reportData.toString();
                        bufw.write(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufw != null) {
                        try {
                            bufw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        public boolean writeReportRateDataFinal() {
            List<WifiCountBean> listListBean = readCvsToListData();
            List<WifiReportBean> listWifiReportBean = new ArrayList<>();

            if (listListBean == null || listListBean.isEmpty()) {
                Toast.makeText(MainActivity.this, "没有要生成Cvs概率的数据", Toast.LENGTH_SHORT).show();
                return false;
            }

            for (WifiCountBean mBean : listListBean) {
                float maxNum = (float) Collections.max(mBean.getListCountAccept());
                float minNum = (float) Collections.min(mBean.getListCountAccept());
                float average = NumberUtil.averageNum(mBean.getListCountAccept());
                float ariance = NumberUtil.arianceInteger(mBean.getListCountAccept());
                float standard = NumberUtil.standardDiviationInteger(mBean.getListCountAccept());
                WifiReportBean mWifiReportBean = new WifiReportBean(maxNum, minNum, average, ariance, standard);
                mWifiReportBean.setStepTime(mBean.getStepTime());

                float maxNumProportion = Collections.max(mBean.getListProportion());
                float minNumProportion = Collections.min(mBean.getListProportion());
                float averageProportion = NumberUtil.averageFloat(mBean.getListProportion());
                float arianceProportion = NumberUtil.arianceFloat(mBean.getListProportion());
                float standardProportion = NumberUtil.standardDiviationFloat(mBean.getListProportion());
                WifiReportBean mWifiReportBean2 = new WifiReportBean(maxNumProportion, minNumProportion, averageProportion, arianceProportion, standardProportion);
                mWifiReportBean2.setStepTime(mBean.getStepTime());
                listWifiReportBean.add(mWifiReportBean);
                listWifiReportBean.add(mWifiReportBean2);

            }
            FileUtil.judeFileExistsCreate(Contants.FOLDER_NAME, Contants.FILE_NAME_WIFI_COUNT_REPORT);
            CvsUtil.writeCsvFile(Contants.FOLDER_NAME, Contants.FILE_NAME_WIFI_COUNT_REPORT, listWifiReportBean);
            return true;
        }

        private List<WifiCountBean> readCvsToListData() {
            List<WifiCountBean> listBean = new ArrayList<>();
            for (int i = 1; i < 11; i++) {
                WifiCountBean mWifiCountBean = new WifiCountBean();
                List<Integer> listCountAccept = new ArrayList<>();
                List<Float> listProportion = new ArrayList<>();

                int steptime = i * 5000;
                BufferedReader bReader = null;
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator
                        + "wifiupdatecount");
                if (!dir.exists()) {
                    Log.i(TAG, "dir文件夹不存在...");
                    continue;
                }
                File file = new File(dir, "report_" + steptime + ".csv");
                if (!file.exists()) {
                    Log.i(TAG, "csv文件不存在...");
                    continue;
                }
                try {
                    bReader = new BufferedReader(new FileReader(file));
                    CSVReader csvReader = new CSVReader(bReader);//这里以“|”为分隔符，可在CSVReader类中修改
                    String[] next = {};
                    int posNum = 1;
                    int acceptPos = 0;
                    int proportionPos = 0;
                    int stepPos = 0;
                    while (true) {
                        next = csvReader.readNext();
                        if (next != null) {
                            if (next.length == 0) {
                                break;
                            }
                            if (posNum == 1) {
                                for (int count = 0; count < next.length; count++) {
                                    if (next[count].equals(WifiCountBean.COUNT_ACCEPT)) {
                                        acceptPos = count;
                                    }
                                    if (next[count].equals(WifiCountBean.PROPORTION)) {
                                        proportionPos = count;
                                    }
                                    if (next[count].equals(WifiCountBean.STEP_TIME)) {
                                        stepPos = count;
                                    }
                                }
                            } else {
                                if (Integer.valueOf(next[acceptPos]) != 0) {
                                    listCountAccept.add(Integer.valueOf(next[acceptPos]));
                                    listProportion.add(Float.valueOf(next[proportionPos]));
                                    mWifiCountBean.setStepTime(Integer.valueOf(next[stepPos]));
                                }
                            }
                            posNum++;
                        } else {
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    Log.i(TAG, "FileNotFoundException");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i(TAG, "IOException");
                    e.printStackTrace();
                } finally {

                    if (bReader != null) {
                        try {
                            bReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            Log.i(TAG, "i=" + i);
                            mWifiCountBean.setListCountAccept(listCountAccept);
                            mWifiCountBean.setListProportion(listProportion);
                            if (!mWifiCountBean.getListCountAccept().isEmpty()) {
                                listBean.add(mWifiCountBean);
                            }
                        }
                    }
                }
            }
            return listBean;
        }

        void stopCount() {
            //TODO
        }

    }
}
