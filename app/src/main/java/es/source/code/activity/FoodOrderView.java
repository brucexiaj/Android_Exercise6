package es.source.code.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.model.User;
import es.source.code.util.MyFragment;

public class FoodOrderView extends AppCompatActivity {

    private Logger log = Logger.getLogger("FoodOrderView");
    private final String[] columns = {"未下单菜", "已下单菜"};
    private MyPagerAdapter myPagerAdapter;
    private User user = new User();
    private String infoFromFoodView = "unOrdered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        getSupportActionBar().hide();
        TabLayout tabLayout = findViewById(R.id.tab_layout_food_view);
        ViewPager viewPager = findViewById(R.id.view_pager_food_view);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();
        //获取MainScreen传递过来的User信息
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("userInfoFromMainScreen");
        //获取FoodView传来的信息
        String tempInfo = intent.getStringExtra("infoFromFoodView");

        if (null != tempInfo && !"".equals(tempInfo)) {
            infoFromFoodView = tempInfo;
        }
        tabLayout.getTabAt("ordered".equals(infoFromFoodView) ? 1 : 0).select();

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
            bundle.putString("activityName", "FoodOrderView");
            if (null != user) {
                bundle.putSerializable("userInfo", user);
            }
            myFragment.setArguments(bundle);
            myFragment.getView();
            bundle.putString("location", 0 == position ? "0" : "1");
            return myFragment;
        }

        @Override
        public int getCount() {
            return columns.length;
        }
    }


}
