package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.logging.Logger;
import es.source.code.R;
import es.source.code.util.SharedPreferenceUtil;

public class SCOSEntry extends Activity {
    private Logger log = Logger.getLogger("SCOSEntry");
    float x1 = 0.0f, x2 = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        log.info(">>>>>>>>>enter onCreate");
        //向左侧滑动监听器
        RelativeLayout relativeLayout = findViewById(R.id.entry_relative_layout);
        relativeLayout.setClickable(true);
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                log.info(">>>>>>>>>enter onTouch");
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    x1 = event.getX();
                }
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    x2 = event.getX();
                    if (50 < x1 - x2) {
                        //跳转到主屏幕
                        Intent mainScreenActivity = new Intent(SCOSEntry.this, MainScreen.class);
                        mainScreenActivity.putExtra("EntryToMainScreen", "FromEntry");
                        startActivity(mainScreenActivity);//  开始跳转
                    }
                }
                return true;
            }
        };
        relativeLayout.setOnTouchListener(onTouchListener);

        //初始化菜品的信息
        SharedPreferenceUtil spUtil = new SharedPreferenceUtil(SCOSEntry.this);
        spUtil.initialFoodData();
    }


}
