package es.source.code.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.List;

import java.util.logging.Logger;

public class ReceiveFoodInfoThread extends Thread {

    private Logger log = Logger.getLogger("ReceiveFoodInfo");
    private Context context;
    private Messenger clientMessenger;

    public ReceiveFoodInfoThread(Context context, Messenger clientMessenger) {
        this.context = context;
        this.clientMessenger = clientMessenger;
    }

    @Override
    public void run(){
       while(true) {
           ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
           final List<ActivityManager.RunningTaskInfo> activityList = am.getRunningTasks(100);
           if (0 >= activityList.size()) {
               log.info(">>>>>>>>>程序不在运行");
           }
           for (ActivityManager.RunningTaskInfo info : activityList) {
               if (info.baseActivity.getPackageName().equals("es.source.code")) {
                   log.info(">>>>>>>>>程序在运行");
                   //携带库存信息
                   Message message = new Message();
                   message.what = 10;
                   Bundle bundle = new Bundle();
                   bundle.putString("foodName", "盐焗鸡");
                   bundle.putInt("foodNum", 12);
                   message.setData(bundle);
                   if (null != clientMessenger) {
                       try {
                           clientMessenger.send(message);
                       } catch (RemoteException e) {
                           log.warning(">>>>>>>>>向客户端发送消息失败");
                       }
                       log.info(">>>>>>>>>向客户端发送消息成功");
                   }
               }
           }
           try {
               Thread.sleep(3000);
           } catch (InterruptedException e) {
               log.warning("更新菜品信息的线程休眠失败!");
           }
       }
    }
}
