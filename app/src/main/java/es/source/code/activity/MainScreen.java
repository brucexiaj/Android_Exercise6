package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.model.User;
import es.source.code.util.SharedPreferenceUtil;

public class MainScreen extends Activity {
    private Logger log = Logger.getLogger("MainScreen");
    private int navigateImgsMax[] = {R.mipmap.make_order_no, R.mipmap.order_no, R.mipmap.login_no,
        R.mipmap.help_no};
    private int navigateImgsMin[] = {R.mipmap.login_no,
            R.mipmap.help_no};
    private String navigateNamesMax[] = {"点菜", "订单", "登录/注册", "帮助"};
    private String navigateNamesMin[] = {"登录/注册", "帮助"};
    private static final String LOGIN_OR_REGISTER = "登录/注册";
    private static final String MAKE_ORDER = "点菜";
    private static final String ORDER = "订单";
    private static final String HELP = "帮助";
    private User user = new User();
    private SharedPreferenceUtil spUtil;
    private int loginState = 0;
    private String navigateNames[];
    private int navigateImgs[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_gradview);
        GridView gridView = findViewById(R.id.main_screen_gradview);

        //处理从其它的Activity传递过来的值
        spUtil = new SharedPreferenceUtil(MainScreen.this);
        Intent intent = getIntent();
        if (null != intent) {
            //获取SCOSEntry传过来的值
            String infoFromPreActivity = spUtil.getRecordByName("EntryToMainScreen");
            log.info(">>>>>>>>>infoFromPreActivity:" + infoFromPreActivity);
            //判断数据库里的值是不是FromEntry，是则隐藏点菜、订单按钮
            if (null != infoFromPreActivity && "FromEntry".equals(infoFromPreActivity)) {
                navigateImgs = navigateImgsMin;
                navigateNames = navigateNamesMin;
            } else {
                navigateImgs = navigateImgsMax;
                navigateNames = navigateNamesMax;
            }
            //获取LoginOrRegister传过来的值
            loginState = spUtil.getIntRecordByName("loginState");
            User userFromLoginOrRegister = (User)intent.getSerializableExtra("userInfo");
//            if (null != userFromLoginOrRegister) {
//                log.info(">>>>>>>>>login or register user name:" + userFromLoginOrRegister.getUserName());
//            }
            if (1 == loginState) {
                user = userFromLoginOrRegister;
                navigateImgs = navigateImgsMax;
                navigateNames = navigateNamesMax;
            } else {
                navigateImgs = navigateImgsMin;
                navigateNames = navigateNamesMin;
            }
        }

        //适配器
        List<Map<String, Object>> gridItems = new ArrayList<Map<String, Object>>();
        for (int i = 0;i < navigateImgs.length;i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("navigateImg", navigateImgs[i]);
            item.put("navigateText", navigateNames[i]);
            gridItems.add(item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this, gridItems, R.layout.main_screen_item,
                new String[]{"navigateImg", "navigateText"},
                new int[]{R.id.imageview_navigate_img, R.id.textview_navigate_txt});

        //设置适配器
        gridView.setAdapter(simpleAdapter);

        //监听器
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainScreen.this, navigateNames[position],
//                        Toast.LENGTH_SHORT).show();
                int positionOfLogin = LOGIN_OR_REGISTER.equals(navigateNames[0]) ? 0 : 2;
                if (positionOfLogin == position) { //登录注册
                    Intent loginActivity = new Intent(MainScreen.this, LoginOrRegister.class);
                    MainScreen.this.startActivity(loginActivity);//  开始跳转
                }
                //到FoodView
                if (MAKE_ORDER.equals(navigateNames[0]) && 0 == position) {
                    Intent foodViewActivity = new Intent(MainScreen.this, FoodView.class);
                    foodViewActivity.putExtra("userInfoFromMainScreen", user);
                    MainScreen.this.startActivity(foodViewActivity);//  开始跳转
                }
                //到FoodOrderView
                if (ORDER.equals(navigateNames[01]) && 1 == position) {
                    Intent foodOrderViewActivity = new Intent(MainScreen.this, FoodOrderView.class);
                    foodOrderViewActivity.putExtra("userInfoFromMainScreen", user);
                    MainScreen.this.startActivity(foodOrderViewActivity);//  开始跳转
                }

                //到SCOSHelper
                if (HELP.equals(navigateNames[position])) {
                    Intent helpActivity = new Intent(MainScreen.this, SCOSHelper.class);
                    MainScreen.this.startActivity(helpActivity);//  开始跳转
                }


            }
        });

    }

}
