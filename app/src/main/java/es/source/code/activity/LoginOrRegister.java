package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import es.source.code.R;

public class LoginOrRegister extends Activity {
    private Logger log = Logger.getLogger("LoginOrRegister");
    private static final String pattern = "^[0-9a-zA-Z]+$";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);
        //获取并校验登录名和密码
        EditText nameText = findViewById(R.id.login_name_edittext);
        EditText pwText = findViewById(R.id.login_pw_edittext);
        Button loginButton = findViewById(R.id.login_button);
        Button returnButton = findViewById(R.id.return_button);
        ProgressBar pb = findViewById(R.id.login_progress);
        this.loginButtonListener(this, loginButton, nameText, pwText, pb, log);
        this.returnButtonListener(this, returnButton);
    }

    //登录button监听器
    private static void loginButtonListener(final Context context, Button button,
                                            final EditText nameText, final EditText pwText,
                                            final ProgressBar pb, final Logger log) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String password = pwText.getText().toString();
                //校验输入
                if (null != name) {
                    log.info(">>>>>>>>>name:"+name);
                    if (!Pattern.matches(pattern, name)) {
                        log.info(">>>>>>>>>invalid input for name");
                        nameText.setError("输入内容不符合规则");
                        return;
                    }
                }
                if (null != password) {
                    log.info(">>>>>>>>>password:"+password);
                    if (!Pattern.matches(pattern, password)) {
                        log.info(">>>>>>>>>invalid input for password");
                        pwText.setError("输入内容不符合规则");
                        return;
                    }
                }
                pb.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int max = pb.getMax();
                        int pro = 0;
                        try {
                            while (pro < max) {
                                pro += 20;
                                pb.setProgress(pro);
                                Thread.sleep(400);
                            }
                            //跳到MainScreen
                            Intent intent = new Intent(context, MainScreen.class);
                            intent.putExtra("InfoFromLogin", "LoginSuccess");
                            context.startActivity(intent);//  开始跳转
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    //登录button监听器
    private static void returnButtonListener(final Context context, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到MainScreen
                Intent intent = new Intent(context, MainScreen.class);
                intent.putExtra("InfoFromLogin", "Return");
                context.startActivity(intent);//  开始跳转
            }
        });
    }
}
