package es.source.code.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.util.MyFragment;

public class FoodView extends AppCompatActivity {

    private Logger log = Logger.getLogger("FoodView");
    private final String[] columns = {"冷菜", "热菜", "海鲜", "饮料"};
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
                Toast.makeText(this, "已点菜品", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.view_order:
                Toast.makeText(this, "查看订单", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.call_service:
                Toast.makeText(this, "呼叫服务", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
