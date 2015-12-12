package net.toutoo.smshelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.Date;
import java.util.List;


public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("收到短信");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Object[] pdus=(Object[])intent.getExtras().get("pdus");
            SmsMessage[] message=new SmsMessage[pdus.length];
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<pdus.length;i++){
                message[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                sb.append("接收到短信来自:\n");
                sb.append(message[i].getDisplayOriginatingAddress()+"\n");
                sb.append("内容:"+message[i].getDisplayMessageBody()+"\n");
                String date = new Date(message[i].getTimestampMillis()).toLocaleString();
                sb.append("time"+date+"\n");
//                sb.append("getSubId"+message[i].getSubId());
//                sendSMS("18589087820", message[i].getDisplayMessageBody());
            }

            System.out.println(sb.toString());

            AVObject post = new AVObject("SMSMessage");
            post.put("sender", message[0].getDisplayOriginatingAddress());
            post.put("receiver", "null");
            post.put("sendTime", new Date(message[0].getTimestampMillis()));
            post.put("content", message[0].getDisplayMessageBody());

            post.saveInBackground(new SaveCallback() {
                public void done(AVException e) {
                    if (e == null) {
                        // 保存成功
                        System.out.println("数据保存成功...");
                    } else {
                        // 保存失败，输出错误信息
                        System.out.println("数据保存失败:" + e.getMessage());
                    }
                }
            });
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）

        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }
}