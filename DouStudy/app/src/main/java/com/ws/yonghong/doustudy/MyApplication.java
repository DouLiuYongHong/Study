package com.ws.yonghong.doustudy;

        import android.app.Application;

        import com.ws.yonghong.doustudy.task.TaskManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TaskManager.Ext.init(this);
        TaskManager.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
    }
}
