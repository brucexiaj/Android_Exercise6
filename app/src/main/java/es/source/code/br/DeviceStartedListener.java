package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.logging.Logger;

import es.source.code.service.UpdateService;

public class DeviceStartedListener extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    private Logger log = Logger.getLogger("ServerObserverService");
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收到设备开机服务，启动UpdateService
        if (intent.getAction().equals(ACTION_BOOT)) {
            log.info(">>>>>>>>>接收到开机广播");
            Intent updateService = new Intent(context, UpdateService.class);
            context.startService(updateService);
        }
    }
}
