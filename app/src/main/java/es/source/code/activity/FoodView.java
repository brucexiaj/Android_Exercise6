package es.source.code.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.util.MyFragment;

public class FoodView extends AppCompatActivity {

    private Logger log = Logger.getLogger("MyFragment");
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
            bundle.putString("title", columns[position]);
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
