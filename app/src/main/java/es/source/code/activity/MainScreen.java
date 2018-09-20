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
        //四个按钮的监听器
        Button makeOrderButton = findViewById(R.id.make_order);
        Button orderButton = findViewById(R.id.order);
        Button loginButton = findViewById(R.id.login);
        Button helpButton = findViewById(R.id.help);

        Intent intent = getIntent();
        if (null != intent) {
            //获取SCOSEntry传过来的值
            String infoFromPreActivity = intent.getStringExtra("EntryToMainScreen");
            log.info(">>>>>>>>>infoFromPreActivity:" + infoFromPreActivity);
            //判断传过来的值是不是FromEntry，不是则隐藏点菜、订单按钮
            if (null != infoFromPreActivity && !"FromEntry".equals(infoFromPreActivity)) {
                makeOrderButton.setVisibility(View.INVISIBLE);
                orderButton.setVisibility(View.INVISIBLE);
            }
            //获取登录注册页面传过来的值
            String infoFromLogin = intent.getStringExtra("InfoFromLogin");
            if (null != infoFromLogin) {
                if ("LoginSuccess".equals(infoFromLogin)) {
                    makeOrderButton.setVisibility(View.VISIBLE);
                    orderButton.setVisibility(View.VISIBLE);
                }
                if ("Return".equals(infoFromLogin)) {
                    makeOrderButton.setVisibility(View.INVISIBLE);
                    orderButton.setVisibility(View.INVISIBLE);
                }
            }
        }

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
                Intent loginActivity = new Intent(context, LoginOrRegister.class);
                context.startActivity(loginActivity);//  开始跳转
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
