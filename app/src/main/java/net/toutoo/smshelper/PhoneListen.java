package net.toutoo.smshelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.app.Service;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.Date;

public class PhoneListen extends BroadcastReceiver{
    private static boolean incomingFlag = false;
    private static boolean isSendApi = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        //拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            incomingFlag = false;
            final String phoneNum = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d("PhoneReceiver", "phoneNum: " + phoneNum);
        } else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
    final PhoneStateListener listener=new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;

                    if (!isSendApi) {
                        isSendApi = true;

                        AVObject post = new AVObject("SMSMessage");
                        post.put("sender", "13888888888");
                        post.put("receiver", "null");
                        post.put("sendTime", new Date());
                        post.put("content", "收到了来电:" + incomingNumber);

                        post.saveInBackground(new SaveCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    // 保存成功
                                    System.out.println("数据保存成功...");
                                    isSendApi = false;
                                } else {
                                    // 保存失败，输出错误信息
                                    System.out.println("数据保存失败:" + e.getMessage());
                                    isSendApi = false;
                                }
                            }
                        });
                    }

                    Log.i("PhoneReceiver", "CALL IN RINGING :" + incomingNumber);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingFlag) {
                        Log.i("PhoneReceiver", "CALL IN ACCEPT :" + incomingNumber);
                    }
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (incomingFlag) {
                        Log.i("PhoneReceiver", "CALL IDLE");
                    }
                    break;
            }
        }
    };
}