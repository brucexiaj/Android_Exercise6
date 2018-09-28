package es.source.code.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.model.Food;
import es.source.code.R;

public class SharedPreferenceUtil {

    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private int foodTotalNum = 7;

    public SharedPreferenceUtil(Context context) {
        this.context = context;
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sp.edit();
    }

    //初始化菜品的信息
    public void initialFoodData() {
        Food food1 = new Food(0 , "佛跳墙", 99.99f, 0, R.mipmap.fotiaoqiang, 1, "限量供应，先到先得");
        Food food2 = new Food(1 , "夫妻肺片", 29.99f, 0, R.mipmap.fuqifeipian, 1, "偏辣");
        Food food3 = new Food(2 , "水煮鱼", 19.99f, 0, R.mipmap.shuizhuyu, 1, "秋分时节的鳜鱼，味道鲜美");
        Food food4 = new Food(3 , "粉蒸肉", 18.29f, 0, R.mipmap.fenzhenrou, 1, "少量肥肉");
        Food food5 = new Food(4 , "盐焗鸡", 32.00f, 0, R.mipmap.yanjuji, 1, "仔公鸡");
        Food food6 = new Food(5 , "宫保鸡丁", 12.99f, 0, R.mipmap.gongbaojiding, 1, "川菜");
        Food food7 = new Food(6 , "地三鲜", 8.40f, 0, R.mipmap.disanxian, 1, "荤素搭配，健康美味");
        addFood(food1);
        addFood(food2);
        addFood(food3);
        addFood(food4);
        addFood(food5);
        addFood(food6);
        addFood(food7);

    }

    //将菜品的信息存入数据库
    public void addFood(Food food) {
        int foodIndex = food.getFoodIndex();
        editor.putString(foodIndex + "foodName", food.getFoodName());
        editor.putFloat(foodIndex + "foodPrice", food.getFoodPrice());
        editor.putInt(foodIndex + "foodPhoto", food.getFoodPhoto());
        editor.putString(foodIndex + "memo", food.getMemo());
        editor.putInt(foodIndex + "foodState", food.getFoodState());
        editor.putInt(foodIndex + "foodNum", food.getFoodNum());
        editor.commit();
    }

    //根据索引从数据库取出商品信息
    public Food getFood(int foodIndex) {
        Food food =  new Food();
        food.setFoodIndex(foodIndex);
        food.setFoodName(sp.getString(foodIndex + "foodName", ""));
        food.setFoodNum(sp.getInt(foodIndex + "foodNum", 0));
        food.setFoodPhoto(sp.getInt(foodIndex + "foodPhoto", 0));
        food.setFoodState(sp.getInt(foodIndex + "foodState", 0));
        food.setFoodPrice(sp.getFloat(foodIndex + "foodPrice", 0 ));
        food.setMemo(sp.getString(foodIndex + "memo", ""));
        return food;
    }

    //修改菜品的状态，点菜，退订等
    public void updateFoodState(int foodIndex, int foodState) {
        editor.putInt(foodIndex + "foodState", foodState);
        editor.commit();
    }

    //修改菜品的备注
    public void updateMemo(int foodIndex, String memo) {
        editor.putString(foodIndex + "memo", memo);
        editor.commit();
    }

    //根据菜品的状态获取菜品列表
    public List<Map<String, Object>> getFoodMapListByState(int foodState) {
        List<Map<String, Object>> foodList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < foodTotalNum; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            Food food = this.getFood(i);
            if (foodState == food.getFoodState()) {
                item.put("food_name", food.getFoodName());
                item.put("food_price", food.getFoodPrice());
                item.put("food_num", food.getFoodNum());
                item.put("food_memo", food.getMemo());
                foodList.add(item);
            }
        }
        return foodList;
    }

    //根据菜品的状态获取菜品列表
    public List<Food> getFoodListByState(int foodState) {
        List<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < foodTotalNum; i++) {
            Food food = this.getFood(i);
            if (foodState == food.getFoodState()) {
                foodList.add(food);
            }
        }
        return foodList;
    }

    public int getFoodTotalNum() {
        return foodTotalNum;
    }

    public String getFoodTotalPrice() {
        return String.valueOf(99.99f + 29.99f + 19.99f + 18.29f + 32.00f + 12.99f + 8.40f);
    }

}