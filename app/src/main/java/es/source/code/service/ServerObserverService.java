package es.source.code.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import android.support.annotation.Nullable;
import android.util.EventLog;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.logging.Logger;

import es.source.code.activity.SCOSHelper;
import es.source.code.util.EventBusMessage;
import es.source.code.util.ReceiveFoodInfoThread;
import es.source.code.util.TestMessage;
import org.greenrobot.eventbus.ThreadMode;


public class ServerObserverService extends Service {

    private Context context = this;
    private Logger log = Logger.getLogger("ServerObserverService");
    private ReceiveFoodInfoThread rfi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //注册EventBus
        EventBus.getDefault().register(ServerObserverService.this);
       return null;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getEventBusMessage(EventBusMessage message) {
        log.info("使用EventBus从客户端发送过来的内容是：" + message.getIntMessage());
        rfi = new ReceiveFoodInfoThread(context);
        if (1 == message.getIntMessage()) {
            rfi.start();
        }
        if (0 == message.getIntMessage()) {
            rfi.stop();
        }
    }



}
