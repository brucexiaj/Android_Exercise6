package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.logging.Logger;

import es.source.code.R;

public class MainScreen extends Activity {
    private Logger log = Logger.getLogger("MainScreen");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        //获取上一个activity传过来的值
        Intent intent = getIntent();
        if (null != intent) {
            String infoFromPreActivity = intent.getStringExtra("EntryToMainScreen");
            log.info(">>>>>>>>>infoFromPreActivity:"+infoFromPreActivity);
        }
        //四个按钮的监听器
        Button makeOrderButton = findViewById(R.id.make_order);
        Button orderButton = findViewById(R.id.order);
        Button loginButton = findViewById(R.id.login);
        Button helpButton = findViewById(R.id.help);
        //点菜button
        this.makeOrderButtonListener(this, makeOrderButton);
        //订单button
        this.orderrButtonListener(this, orderButton);
        //点菜button
        this.loginButtonListener(this, loginButton);
        //点菜button
        this.helpButtonListener(this, helpButton);
    }

    //点菜button监听器
    private static void makeOrderButtonListener(final Context context, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "点菜", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //订单button监听器
    private static void orderrButtonListener(final Context context, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "订单", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //登录button监听器
    private static void loginButtonListener(final Context context, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "登录/注册", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //帮助button监听器
    private static void helpButtonListener(final Context context, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "帮助", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
