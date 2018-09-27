package es.source.code.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.util.MyFragment;

public class FoodOrderView extends AppCompatActivity {

    private Logger log = Logger.getLogger("FoodOrderView");
    private final String[] columns = {"未下单菜", "已下单菜"};
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        TabLayout tabLayout = findViewById(R.id.tab_layout_food_view);
        ViewPager viewPager = findViewById(R.id.view_pager_food_view);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //Toast.makeText(FoodOrderView.this, tab.getText(), Toast.LENGTH_SHORT).show();
//                if ("未下单菜".equals(tab.getText())) {
//                    View view = LayoutInflater.from(FoodOrderView.this).inflate(R.layout.food_order_view_item, null);
//                    ListView listView = view.findViewById(R.id.food_order_view_listview);
//                    Toast.makeText(FoodOrderView.this, "h"+(listView==null), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
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
