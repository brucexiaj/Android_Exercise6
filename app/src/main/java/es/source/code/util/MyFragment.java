package es.source.code.util;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.source.code.R;

public class MyFragment extends Fragment {

    private Logger log = Logger.getLogger("MyFragment");
    private float foodPrices[] = {9.99f, 43.00f, 24.10f, 100.00f, 22.80f, 29.99f, 10.01f};
    private String foodNames[] = {"佛跳墙", "夫妻肺片", "水煮鱼", "粉蒸肉", "盐焗鸡", "宫保鸡丁", "地三鲜"};

    private View view;
    public ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_view_listview, null);
        //适配器
        List<Map<String, Object>> viewItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < foodNames.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("food_name", foodNames[i]);
            item.put("food_price", foodPrices[i]);
            viewItems.add(item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getActivity(), viewItems, R.layout.food_view_item,
                new String[]{"food_name", "food_price"},
                new int[]{R.id.food_name, R.id.food_price}
        );


        listView = view.findViewById(R.id.food_view_listview);
        listView.setFocusable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                log.info(">>>>>>>>>onItemClick");
                Toast.makeText(getActivity(), "tongpochunvmo", Toast.LENGTH_LONG).show();
            }
        });
        listView.setAdapter(simpleAdapter);

        return view;
    }


}
