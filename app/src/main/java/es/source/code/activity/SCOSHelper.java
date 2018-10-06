package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.util.EventBusMessage;
import es.source.code.util.SendMailUtil;

public class SCOSHelper extends Activity {

    private Logger log = Logger.getLogger("SCOSHelper");
    private String columnNames[] = {"使用协议", "关于系统", "电话帮助", "短信帮助", "邮件帮助"};
    private int columnImgs[] = {R.mipmap.user_protocal, R.mipmap.about_us, R.mipmap.phone, R.mipmap.message, R.mipmap.mail};
    private static final String PHONE = "5545";
    private static final String MESSAGE_CONTENT = "test scos helper";
    private Handler handler;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusMessage message){
        log.info(">>>>>>>>>收到邮件发送的提示：" + message.getMessageName() + message.getIntMessage());
        if (1 == message.getIntMessage()) {
           // Looper.prepare();
            Toast.makeText(SCOSHelper.this, "求助邮件发送成功", Toast.LENGTH_LONG).show();
           // Looper.loop();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scos_helper_gridview);
        GridView gridView = findViewById(R.id.scos_helper_gridview);

        //构造适配器
        List<Map<String, Object>> gridItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < columnNames.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("columnName", columnNames[i]);
            item.put("columnImg", columnImgs[i]);
            gridItems.add(item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this, gridItems, R.layout.scos_helper_item,
                new String[]{"columnImg", "columnName"},
                new int[]{R.id.imageview_help_img, R.id.textview_help_txt});
        //设置适配器
        gridView.setAdapter(simpleAdapter);

        //Handler
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message message) {
//                super.handleMessage(message);
//                if (1 == message.what) {
//                    Toast.makeText(SCOSHelper.this, "求助邮件发送成功", Toast.LENGTH_LONG).show();
//                }
//            }
//        };

        EventBus.getDefault().register(SCOSHelper.this);


        //监听器
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PHONE));
                        try {
                            startActivity(callIntent);
                        } catch (SecurityException e) {
                            Toast.makeText(SCOSHelper.this, "请授予应用拨打电话的权限", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 3:
                        SmsManager smsManager = SmsManager.getDefault();
                        try {
                            smsManager.sendTextMessage(PHONE, null, MESSAGE_CONTENT, null, null);
                        } catch (Exception e) {
                            Toast.makeText(SCOSHelper.this, "请授予应用发送短信的权限", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(SCOSHelper.this, "求助短信发送成功", Toast.LENGTH_LONG).show();
                        break;
                    case 4:

                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    new SendMailUtil().sendMail();
                                } catch (Exception e) {
                                    Toast.makeText(SCOSHelper.this, "发送邮件异常，请重试", Toast.LENGTH_LONG).show();
                                }
                                EventBus.getDefault().post(new EventBusMessage("mailResult", 1));
                            }
                        }.start();
                        break;
                    default:
                        break;
                }
            }
        });

    }


}
