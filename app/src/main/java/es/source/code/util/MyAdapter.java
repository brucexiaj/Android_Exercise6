package es.source.code.util;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
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
import es.source.code.model.Food;

public class MyAdapter extends BaseAdapter {
    List<Food> list;
    Context context;
    String activityName = "FoodView";
    int location = 0;
    private Logger log = Logger.getLogger("MyAdapter");
    private SharedPreferenceUtil spUtil;


    public MyAdapter(List<Food> list, Context context, String activityName, int location) {
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
        spUtil = new SharedPreferenceUtil(context);
        final FoodViewItem foodViewItem;
        final Food food = list.get(position);
        //点菜页面
        if ("FoodView".equals(activityName)) {
            if (null == view) {
                foodViewItem = new FoodViewItem();
                view = LayoutInflater.from(context).inflate(R.layout.food_view_item, null);
                //log.info(">>>>>>>>>is null:" + view);
                foodViewItem.textViewFoodName = view.findViewById(R.id.food_name);
                foodViewItem.textViewFoodPrice = view.findViewById(R.id.food_price);
                foodViewItem.orderFoodButton = view.findViewById(R.id.button_order_food);
                foodViewItem.textViewFoodNum = view.findViewById(R.id.text_view_food_num);
                view.setTag(foodViewItem);
            } else {
                foodViewItem = (FoodViewItem) view.getTag();
            }
            foodViewItem.textViewFoodName.setText(food.getFoodName());
            foodViewItem.textViewFoodPrice.setText(String.valueOf(food.getFoodPrice()));
            foodViewItem.textViewFoodNum.setText(String.valueOf(food.getFoodNum()));
            switch (food.getFoodState()) {
                case 0 : foodViewItem.orderFoodButton.setText("点菜");
                         foodViewItem.orderFoodButton.setBackgroundColor(Color.GREEN);
                         break;
                case 1 : foodViewItem.orderFoodButton.setText("退点");
                         foodViewItem.orderFoodButton.setBackgroundColor(Color.YELLOW);
                         break;
                case 2 : foodViewItem.orderFoodButton.setText("已下单");
                         foodViewItem.orderFoodButton.setBackgroundColor(Color.GRAY);
                         break;
            }
            foodViewItem.orderFoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food currentfood = list.get(position);
                    if (0 == currentfood.getFoodState()) {
                        //点菜成功，给出提示，同时修改这个菜品的状态
                        foodViewItem.orderFoodButton.setText("退点");
                        foodViewItem.orderFoodButton.setBackgroundColor(Color.YELLOW);
                        Toast.makeText(context, "点菜成功!", Toast.LENGTH_SHORT).show();
                        spUtil.updateFoodState(food.getFoodIndex(), 1);
                        currentfood.setFoodState(1);
                    } else if (1 == currentfood.getFoodState()) {
                        //退点成功，给出提示，同时修改这个菜品的状态
                        foodViewItem.orderFoodButton.setText("点菜");
                        foodViewItem.orderFoodButton.setBackgroundColor(Color.GREEN);
                        Toast.makeText(context, "退点成功!", Toast.LENGTH_SHORT).show();
                        spUtil.updateFoodState(food.getFoodIndex(), 0);
                        currentfood.setFoodState(0);
                    } else {
                        Toast.makeText(context, "已下单，不可操作！", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            return view;

        } else {
            //订单页面
            if (null == view) {
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
            foodViewItem.textViewFoodNameFoodOrderView.setText(food.getFoodName());
            foodViewItem.textViewFoodPriceFoodOrderView.setText(String.valueOf(food.getFoodPrice()));
            foodViewItem.textViewNumFoodOrderView.setText(String.valueOf(food.getFoodNum()));
            foodViewItem.textViewMemoFoodOrderView.setText(food.getMemo());
            //log.info(">>>>>>>>>location:"+location);
            if(1 == location) { //已下单菜页面，需要删除退订按钮
                foodViewItem.buttonUnsubscribe.setVisibility(View.GONE);
            } else {
                foodViewItem.buttonUnsubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spUtil.updateFoodState(food.getFoodIndex(), 0);
                        foodViewItem.buttonUnsubscribe.setVisibility(View.GONE);
                        Toast.makeText(context, "退订成功", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return view;
        }

    }

    public final static class FoodViewItem {
        //FoodView页面的组件
        public TextView textViewFoodName;
        public TextView textViewFoodPrice;
        public Button orderFoodButton;
        private TextView textViewFoodNum;

        //FoodOrderView页面的组件
        public TextView textViewFoodNameFoodOrderView;
        public TextView textViewFoodPriceFoodOrderView;
        public TextView textViewNumFoodOrderView;
        public TextView textViewMemoFoodOrderView;
        public Button buttonUnsubscribe;
    }





}