package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.model.Food;
import es.source.code.model.ServerFood;
import es.source.code.util.InternetUtil;
import es.source.code.util.SharedPreferenceUtil;

public class UpdateService extends IntentService {

    private Logger log = Logger.getLogger("UpdateService");
    private static final String FOOD_URL = "http://192.168.43.183:8080/SCOSServer/food";

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //从服务器获取菜品信息
        String jsonResult = InternetUtil.getRequest(FOOD_URL);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
        log.info(">>>>>>>>>>从服务器获取的菜品信息的长度是:"+jsonResult.length());
        //跳到MainScreen
        Intent mainScreenIntent = new Intent(this, MainScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainScreenIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //初始化通知栏
        NotificationManager motificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setAutoCancel(true).setSmallIcon(R.mipmap.logo).setContentIntent(pendingIntent);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.food_notification);
        if (null == jsonResult) {
            //自定义通知的布局
            remoteViews.setTextViewText(R.id.text_view_notifi_title, "无法连接服务器获取菜品信息，请检查网络！");
        } else {
            try {
                long startTime = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject(jsonResult);
                int newFoodNum = (int)jsonObject.get("totalNum");
                //将菜品信息存入SharedPreference
                SharedPreferenceUtil spUtil = new SharedPreferenceUtil(this);
                JSONArray jsonArray = jsonObject.getJSONArray("foodList");
                int startIndex = spUtil.getTotalNum();
                for (int i = 0;i < jsonArray.length();i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String foodName = obj.getString("name");
                    float foodPrice = Float.parseFloat(obj.getString("price"));
                    int foodCategory = obj.getInt("category");
                    Food food = new Food(startIndex++, foodName, foodPrice, foodCategory);
                    spUtil.addFood(food);
                }
                long endTime = System.currentTimeMillis();
                log.info("解析这些菜品耗时：" + (endTime - startTime));
                //自定义通知的布局;
                remoteViews.setTextViewText(R.id.text_view_notifi_title, "有新菜上架啦！");
                remoteViews.setTextViewText(R.id.text_view_notifi_content, "新品上架：" + newFoodNum);
            } catch (JSONException e) {
                log.info(">>>>>>>>>" + e);
            }


//
//
//            long startTime = System.currentTimeMillis();
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            try {
//                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
//                Document document = documentBuilder.parse(new ByteArrayInputStream(jsonResult.getBytes()));
//                Element rootElement = document.getDocumentElement();
//                //获取根元素的count属性
//                int countOfBooks = Integer.parseInt(rootElement.getAttribute("totalNum"));
//                System.out.println(countOfBooks);
//                NodeList nodeList = document.getElementsByTagName("food");
//                for(int i = 0; i < nodeList.getLength(); i++){
//                    Node node = nodeList.item(i);
//                    NodeList childNodeList = node.getChildNodes();
//                    for (int j = 0;j < childNodeList.getLength();j++) {
//                        Node childNode = childNodeList.item(j);
//                        String nodeName = childNode.getNodeName();
//                        if ("name".equals(nodeName)) {
//                            String foodName = childNode.getTextContent();
//                            //log.info("菜名：" + foodName);
//                        } else if ("price".equals(nodeName)) {
//                            float foodPrice = Float.parseFloat(childNode.getTextContent());
//                        } else {
//                            int foodCategory = Integer.parseInt(childNode.getTextContent());
//                        }
//
//
//                    }
//
//                }
//                long endTime = System.currentTimeMillis();
//                log.info("解析这些菜品耗时：" + (endTime - startTime));
//            } catch(Exception e) {
//                e.printStackTrace();
//            }









        }
        mBuilder.setContent(remoteViews);
        //发送通知请求
        motificationManager.notify(0, mBuilder.build());
        //使用MediaPlayer播放提示音
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION));
            mediaPlayer.prepare();
        } catch (IOException e) {

        }
        mediaPlayer.start();
    }
}
