package es.source.code.util;



import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.source.code.R;


public class MyFragment extends Fragment {
    private Logger log = Logger.getLogger("MyFragment");
    private Float foodPrices[] = {9.99f, 43.00f, 24.10f, 100.00f, 22.80f, 29.99f, 10.01f};
    private String foodNames[] = {"佛跳墙", "夫妻肺片", "水煮鱼", "粉蒸肉", "盐焗鸡", "宫保鸡丁", "地三鲜"};
    private Integer foodNums[] = {1,2,3,4,5,6,7};
    private String memos[] = {"限量供应，先到先得", "偏辣", "秋分时节的鳜鱼，味道鲜美", "少量肥肉", "仔公鸡", "川菜", "荤素搭配，健康美味"};

    private String activityName = "FoodView";
    private String location = "0";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //获取传递给Fragment的参数
        if (null != getArguments()) {
            activityName = getArguments().getString("activityName");
            location = getArguments().getString("location");
        }
        if ("FoodView".equals(activityName)) {
            View view = inflater.inflate(R.layout.food_view_listview, null);
            //适配器
            List<Map<String, Object>> viewItems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < foodNames.length; i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("food_name", foodNames[i]);
                item.put("food_price", foodPrices[i]);
                viewItems.add(item);
            }
            MyAdapter myAdapter = new MyAdapter(viewItems, getActivity(), "FoodView", location);
            ListView listView = view.findViewById(R.id.food_view_listview);
            listView.setFocusable(false);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    log.info(">>>>>>>>>onItemClick");
                    //点击ListView，跳往菜品详情页面
                    Toast.makeText(getActivity(), "点击了ListView", Toast.LENGTH_SHORT).show();
                }
            });
            listView.setAdapter(myAdapter);
            return view;

        } else {
            View view = inflater.inflate(R.layout.food_order_view_listview, null);
            //适配器
            List<Map<String, Object>> viewItems = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < foodNames.length; i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("food_name", foodNames[i]);
                item.put("food_price", foodPrices[i]);
                item.put("food_num", foodNums[i]);
                item.put("food_memo", memos[i]);
                viewItems.add(item);
            }
            MyAdapter myAdapter = new MyAdapter(viewItems, getActivity(), "FoodOrderView", location);
            ListView listView = view.findViewById(R.id.food_order_view_listview);
            listView.setAdapter(myAdapter);
            TextView textViewTotalNum = view.findViewById(R.id.text_view_total_num);
            textViewTotalNum.setText(new Integer(foodNames.length).toString());
            TextView textViewTotalPrice = view.findViewById(R.id.text_view_total_price);
            textViewTotalPrice.setText(calFoodTotalPrice(foodPrices));
            Button buttonSettlement = view.findViewById(R.id.button_settlement);
            if ("1".equals(location)) {
                buttonSettlement.setText("提交订单");
            }
            return view;
        }

    }

    public static String calFoodTotalPrice(Float foodPrice[]) {
        Float total = 0.0f;
        for(Float price:foodPrice) {
            total += price;
        }
        return String.valueOf(total);
    }
}
