package es.source.code.activity;


import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.model.User;
import es.source.code.util.EventBusMessage;
import es.source.code.util.MyFragment;
import es.source.code.service.ServerObserverService;
import es.source.code.util.ReceiveFoodInfoThread;
import es.source.code.util.SharedPreferenceUtil;
import es.source.code.util.TestMessage;

public class FoodView extends AppCompatActivity {

    private Logger log = Logger.getLogger("FoodView");
    private final String[] columns = {"冷菜", "热菜", "海鲜", "饮料"};
    private MyPagerAdapter myPagerAdapter;
    private User user = new User();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferenceUtil spUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        tabLayout = findViewById(R.id.tab_layout_food_view);
        viewPager = findViewById(R.id.view_pager_food_view);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //获取MainScreen传递过来的User信息
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("userInfoFromMainScreen");

        Intent intentService = new Intent(this, ServerObserverService.class);

        bindService(intentService, serviceConnection, BIND_AUTO_CREATE);
        spUtil = new SharedPreferenceUtil(this);

        //注册EventBus
        EventBus.getDefault().register(FoodView.this);
    }

    //添加ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ActionBar按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ordered_food:
                //进入未下单菜页面
                Intent intentUnOrdered = new Intent(FoodView.this, FoodOrderView.class);
                intentUnOrdered.putExtra("infoFromFoodView", "unOrdered");
                intentUnOrdered.putExtra("userInfoFromMainScreen", user);//取userInfoFromMainScreen是为了和MainScreen传来的信息统一
                startActivity(intentUnOrdered);
                return true;
            case R.id.view_order:
                //进入已下单菜页面
                Intent intentOrdered = new Intent(FoodView.this, FoodOrderView.class);
                intentOrdered.putExtra("infoFromFoodView", "ordered");
                intentOrdered.putExtra("userInfoFromMainScreen", user);//取userInfoFromMainScreen是为了和MainScreen传来的信息统一
                startActivity(intentOrdered);
                return true;
            case R.id.call_service:
                Toast.makeText(this, "呼叫服务", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.in_time_update:
                if ("启动实时更新".equals(item.getTitle())) {
                    //构造并且使用EventBus发送消息
                    EventBusMessage busMessage = new EventBusMessage();
                    busMessage.setIntMessage(1);
                    EventBus.getDefault().post(busMessage);
                    item.setTitle("停止实时更新");
                } else {
                    log.info(">>>>>>>>>按钮变为停止更新");
                    //构造并且使用EventBus发送消息
                    EventBusMessage busMessage = new EventBusMessage();
                    busMessage.setIntMessage(0);
                    EventBus.getDefault().post(busMessage);
                    item.setTitle("停止实时更新");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            log.info(">>>>>>>连接服务");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };



    //改为使用EventBus接受从服务器端传过来的菜品信息
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getEventBusMessage(EventBusMessage message) {
        log.info("使用EventBus从服务端发送过来的内容是：" + message.getIntMessage());
        //根据收到的信息更新菜品的库存
        if (10 == message.getIntMessage()) {
            log.info(">>>>>>>>>开始更新菜品信息");
            String foodName = message.getMessageName();
            int foodNum = message.getFoodNum();
            spUtil.updateFoodNumByFoodName(foodNum, foodName);
        }
    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyFragment myFragment;
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return columns[position];
        }
        @Override
        public Fragment getItem(int position) {
            myFragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("activityName", "FoodView");
            bundle.putInt("location", position);
            myFragment.setArguments(bundle);
            myFragment.getView();
            return myFragment;
        }
        @Override
        public int getCount() {
            return columns.length;
        }
    }


}
