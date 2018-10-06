package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

public class UpdateService extends IntentService {

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationManager motificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent foodDetailIntent = new Intent(this, FoodDetailed.class);
        foodDetailIntent.putExtra("foodIndex", 7);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, foodDetailIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentTitle("有新菜上架啦！")
                //设置内容
                .setContentText("新品上架：重庆豆花鱼，33，热菜")
                //设置小图标
                .setSmallIcon(R.mipmap.logo)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //首次进入时显示效果
                .setTicker("我是测试内容")
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        //向SharedPreferences添加该菜品
        SharedPreferenceUtil spUtil = new SharedPreferenceUtil(this);
        Food food = new Food(7, "重庆豆花鱼,", 33.3f, 0, R.mipmap.chongqingdouhuayu, 1, "辛辣");
        spUtil.addFood(food);
        spUtil.initialFoodData();
        //发送通知请求
        motificationManager.notify(10, mBuilder.build());


    }
}
