package es.source.code.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.source.code.R;

public class MyAdapter extends BaseAdapter {
    List<Map<String, Object>> list;
    Context context;
    String activityName = "FoodView";
    String location = "0";
    private Logger log = Logger.getLogger("MyAdapter");


    public MyAdapter(List<Map<String, Object>> list, Context context, String activityName, String location) {
        this.list = list;
        this.context = context;
        this.activityName = activityName;
        this.location = location;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        log.info(">>>>>>>>>getView");
        final FoodViewItem foodViewItem;
        //点菜页面
        if ("FoodView".equals(activityName)) {
            if (null == view) {
                foodViewItem = new FoodViewItem();
                view = LayoutInflater.from(context).inflate(R.layout.food_view_item, null);
                log.info(">>>>>>>>>is null:" + view);
                foodViewItem.textViewFoodName = view.findViewById(R.id.food_name);
                foodViewItem.textViewFoodPrice = view.findViewById(R.id.food_price);
                foodViewItem.orderFoodButton = view.findViewById(R.id.button_order_food);
                view.setTag(foodViewItem);
            } else {
                foodViewItem = (FoodViewItem) view.getTag();
            }
            foodViewItem.textViewFoodName.setText((String) list.get(position).get("food_name"));
            foodViewItem.textViewFoodPrice.setText(String.valueOf(list.get(position).get("food_price")));
            foodViewItem.orderFoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点菜成功，给出提示
                    foodViewItem.orderFoodButton.setText("退点");
                    foodViewItem.orderFoodButton.setBackgroundColor(Color.YELLOW);
                    Toast.makeText(context, "点菜成功!", Toast.LENGTH_SHORT).show();
                }
            });
            return view;

        } else {
            //订单页面
            if (null == view) {
//                log.info(">>>>>>>>>view:"+position);
                foodViewItem = new FoodViewItem();
                view = LayoutInflater.from(context).inflate(R.layout.food_order_view_item, null);
                foodViewItem.textViewFoodNameFoodOrderView = view.findViewById(R.id.text_view_food_name);
                foodViewItem.textViewFoodPriceFoodOrderView = view.findViewById(R.id.text_view_food_price);
                foodViewItem.textViewNumFoodOrderView = view.findViewById(R.id.text_view_num);
                foodViewItem.textViewMemoFoodOrderView = view.findViewById(R.id.text_view_memo);
                foodViewItem.buttonUnsubscribe = view.findViewById(R.id.button_unsubscribe);
                view.setTag(foodViewItem);
            } else {
                foodViewItem = (FoodViewItem) view.getTag();
            }
            foodViewItem.textViewFoodNameFoodOrderView.setText((String) list.get(position).get("food_name"));
            foodViewItem.textViewFoodPriceFoodOrderView.setText(String.valueOf(list.get(position).get("food_price")));
            foodViewItem.textViewNumFoodOrderView.setText(list.get(position).get("food_num").toString());
            foodViewItem.textViewMemoFoodOrderView.setText((String)list.get(position).get("food_memo"));
            log.info(">>>>>>>>>location:"+location);
            if("0".equals(location)) { //未下单菜页面，需要删除退订按钮
                foodViewItem.buttonUnsubscribe.setVisibility(View.GONE);
            }
            return view;
        }

    }

    public final static class FoodViewItem {
        //FoodView页面的组件
        public TextView textViewFoodName;
        public TextView textViewFoodPrice;
        public Button orderFoodButton;

        //FoodOrderView页面的组件
        public TextView textViewFoodNameFoodOrderView;
        public TextView textViewFoodPriceFoodOrderView;
        public TextView textViewNumFoodOrderView;
        public TextView textViewMemoFoodOrderView;
        public Button buttonUnsubscribe;
    }


}