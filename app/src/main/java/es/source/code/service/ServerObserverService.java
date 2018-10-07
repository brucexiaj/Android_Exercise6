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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.logging.Logger;

import es.source.code.activity.SCOSHelper;
import es.source.code.util.EventBusMessage;
import es.source.code.util.ReceiveFoodInfoThread;
import es.source.code.util.TestMessage;


public class ServerObserverService extends Service {

    private Context context = this;
    private Logger log = Logger.getLogger("ServerObserverService");
    private ReceiveFoodInfoThread rfi;
    private Handler cMessageHandler = new MessageHandler();
    private Messenger serverMessenger = new Messenger(cMessageHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       return serverMessenger.getBinder();
    }


    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            log.info(">>>>>>>>>收到客户端传过来的数据是：" + msg.what);
            rfi = new ReceiveFoodInfoThread(context, msg.replyTo);
            if (1 == msg.what) {
                rfi.start();
            }
            if (0 == msg.what) {
                rfi.stop();
            }
        }
    }

}