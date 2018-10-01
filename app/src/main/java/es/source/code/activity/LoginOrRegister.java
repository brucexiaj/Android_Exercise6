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
import es.source.code.model.User;
import es.source.code.util.SharedPreferenceUtil;

public class LoginOrRegister extends Activity {
    private Logger log = Logger.getLogger("LoginOrRegister");
    private static final String pattern = "^[0-9a-zA-Z]+$";
    private static SharedPreferenceUtil spUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);
        //获取并校验登录名和密码
        EditText nameText = findViewById(R.id.login_name_edittext);
        EditText pwText = findViewById(R.id.login_pw_edittext);
        Button loginButton = findViewById(R.id.login_button);
        Button returnButton = findViewById(R.id.return_button);
        Button registerButton = findViewById(R.id.register_button);
        ProgressBar pb = findViewById(R.id.login_progress);
        this.loginButtonListener(this, loginButton, nameText, pwText, pb, log);
        this.registerButtonListener(this, registerButton, nameText, pwText);
        this.returnButtonListener(this, returnButton);
        //判断是否有用户
        spUtil = new SharedPreferenceUtil(LoginOrRegister.this);
        if (!spUtil.isRecordExist("userName")) { //没有用户记录
            loginButton.setVisibility(View.INVISIBLE);
        } else { //有用户记录
            registerButton.setVisibility(View.INVISIBLE);
            nameText.setText(spUtil.getRecordByName("userName"));
        }
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
                //存入user
                final User loginUser = new User();
                loginUser.setOldUser(true);
                loginUser.setPassword(password);
                loginUser.setUserName(name);
                //登录信息注入SharedPreferences
                spUtil.addStringRecord("userName", name);
                spUtil.addIntRecord("loginState", 1);
                //进度条
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
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userInfo", loginUser);
                            intent.putExtras(bundle);
                            context.startActivity(intent);//  开始跳转
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    //返回button监听器
    private static void returnButtonListener(final Context context, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到MainScreen
                Intent intent = new Intent(context, MainScreen.class);
                intent.putExtra("InfoFromLogin", "Return");
                context.startActivity(intent);//  开始跳转
                //判断是否有用户记录
                if (!spUtil.isRecordExist("userName")) {
                    spUtil.addIntRecord("loginState", 0);
                }
            }
        });
    }

    //注册button监听器
    private static void registerButtonListener(final Context context, Button button,
                                               final EditText nameText, final EditText pwText) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检测用户名和密码
                String name = nameText.getText().toString();
                String password = pwText.getText().toString();
                //校验输入
                if (null != name) {
                    if (!Pattern.matches(pattern, name)) {
                        nameText.setError("输入内容不符合规则");
                        return;
                    }
                }
                if (null != password) {
                    if (!Pattern.matches(pattern, password)) {
                        pwText.setError("输入内容不符合规则");
                        return;
                    }
                }
                //存入User
                User registerUser = new User();
                registerUser.setUserName(name);
                registerUser.setPassword(password);
                registerUser.setOldUser(false);
                //注册信息注入SharedPreferences
                spUtil.addStringRecord("userName", name);
                spUtil.addIntRecord("loginState", 1);
                Intent intent = new Intent(context, MainScreen.class);
                intent.putExtra("InfoFromLogin", "RegisterSuccess");
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", registerUser);
                intent.putExtras(bundle);
                context.startActivity(intent);//  开始跳转
            }
        });
    }
}
