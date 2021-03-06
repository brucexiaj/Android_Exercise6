package es.source.code.util;



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.model.Food;
import es.source.code.model.User;


public class MyFragment extends Fragment {
    private Logger log = Logger.getLogger("MyFragment");
    private String activityName = "FoodView";
    private int location = 0;
    private User user = null;
    private List<Food> viewItems = new ArrayList<Food>();
    private List<Food> viewItemsOrdered = new ArrayList<Food>();//已下单菜品
    private SharedPreferenceUtil spUtil;
    private List<Food> foodList = new ArrayList<>();
    private ProgressBar progressBar;
    private Button buttonSettlement;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        spUtil = new SharedPreferenceUtil(getActivity());
        //获取传递给Fragment的参数
        if (null != getArguments()) {
            activityName = getArguments().getString("activityName");
            location = getArguments().getInt("location");
            user = (User)getArguments().getSerializable("userInfo");
        }
        //点菜
        if ("FoodView".equals(activityName)) {
            //获取初始的菜品信息
            viewItems = spUtil.getFoodListByCategory(location);
            View view = inflater.inflate(R.layout.food_view_listview, null);
            MyAdapter myAdapter = new MyAdapter(viewItems, getActivity(), "FoodView", location);
            ListView listView = view.findViewById(R.id.food_view_listview);
            listView.setFocusable(false);
            //点击ListView，跳往菜品详情页面
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    log.info(">>>>>>>>>onItemClick");
                    Intent intent = new Intent(getActivity(), FoodDetailed.class);
                    //采用了数据存储之后，只需要传当前菜品的索引到下一个页面
                    intent.putExtra("foodIndex", viewItems.get(i).getFoodIndex());
                    startActivity(intent);
                }
            });
            listView.setAdapter(myAdapter);
            return view;

        } else { //查看订单
            View view = inflater.inflate(R.layout.food_order_view_listview, null);
            ListView listView = view.findViewById(R.id.food_order_view_listview);
            TextView textViewTotalNum = view.findViewById(R.id.text_view_total_num);
            TextView textViewTotalPrice = view.findViewById(R.id.text_view_total_price);
            buttonSettlement = view.findViewById(R.id.button_settlement);
            progressBar = view.findViewById(R.id.progressbar_settlement);
            spUtil = new SharedPreferenceUtil(getActivity());
            textViewTotalNum.setText("菜品总数:" + spUtil.getFoodTotalNumByState(location + 1));
            textViewTotalPrice.setText("总金额:" + spUtil.getFoodTotalPriceByState(location + 1));
            MyAdapter myAdapter;
            viewItemsOrdered = spUtil.getFoodListByState(location + 1); //获取初始的菜品信息
            myAdapter = new MyAdapter(viewItemsOrdered, getActivity(), "FoodOrderView", location);
            if (0 == location) { //未下单菜
                buttonSettlement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            foodList = spUtil.getFoodListByState(1);
                            for (Food food : foodList) {
                                spUtil.updateFoodState(food.getFoodIndex(), 2);
                            }
                            Toast.makeText(getActivity(), "提交订单成功", Toast.LENGTH_LONG).show();
                    }
                });
            } else { //已下单菜
                buttonSettlement.setText("结账");
                buttonSettlement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != user && null != user.getOldUser() && true == user.getOldUser()) {
                                Toast.makeText(getActivity(), "您好，老顾客，本次你可享受7折优惠", Toast.LENGTH_LONG).show();

                        }
                        //使用Async模拟结账功能
                        new SettlementAsyncTask().execute(spUtil.getFoodTotalPriceByState(2));
                    }
                });
            }
            listView.setAdapter(myAdapter);
            return view;
        }

    }


    //结账异步线程
    class SettlementAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params) {
            int pro = 10;
            int max = progressBar.getMax();
            while(pro <= max) {
                try {
                    progressBar.setProgress(pro);
                    Thread.sleep(600);
                    pro += 10;
                } catch (InterruptedException e) {
                    log.info(">>>>>>>>settle thread exception!");
                }
            }
            buttonSettlement.setClickable(false);
            return params[0];
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "您本次的消费总额是：" + s, Toast.LENGTH_LONG).show();
        }
    }
}
