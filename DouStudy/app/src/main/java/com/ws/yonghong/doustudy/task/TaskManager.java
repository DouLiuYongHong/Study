package com.ws.yonghong.doustudy.task;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by wyouflf on 15/6/10.
 * 任务控制中心, http, image, db, view注入等接口的入口.
 * 需要在在application的onCreate中初始化: TaskManager.Ext.init(this);
 */
public final class TaskManager {

    private TaskManager() {
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static TaskController task() {
        return Ext.taskController;
    }

    public static class Ext {
        private static boolean debug = false;
        private static Application app;
        private static TaskController taskController;

        private Ext() {
        }

        public static void init(Application app) {
            TaskControllerImpl.registerInstance();
            if (Ext.app == null) {
                Ext.app = app;
            }
        }

        public static void setTaskController(TaskController taskController) {
            if (Ext.taskController == null) {
                Ext.taskController = taskController;
            }
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }
    }
}
