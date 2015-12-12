package net.toutoo.smshelper;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by SimMan on 15/12/12.
 */
public class SMSHelperApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "AppId", "AppKey");
    }
}