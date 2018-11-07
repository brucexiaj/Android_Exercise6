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

    public SharedPreferenceUtil(Context context) {
        this.context = context;
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sp.edit();
    }

    //初始化菜品的信息
    public void initialFoodData() {
        Food food1 = new Food(0 , "佛跳墙", 99.99f, 0, R.mipmap.fotiaoqiang, 1, "限量供应，先到先得", 0);
        Food food2 = new Food(1 , "夫妻肺片", 29.99f, 0, R.mipmap.fuqifeipian, 1, "偏辣", 0);
        Food food3 = new Food(2 , "水煮鱼", 19.99f, 0, R.mipmap.shuizhuyu, 1, "秋分时节的鳜鱼，味道鲜美", 0);
        Food food4 = new Food(3 , "粉蒸肉", 18.29f, 0, R.mipmap.fenzhenrou, 1, "少量肥肉", 0);
        Food food5 = new Food(4 , "盐焗鸡", 32.00f, 0, R.mipmap.yanjuji, 1, "仔公鸡", 0);
        Food food6 = new Food(5 , "宫保鸡丁", 12.99f, 0, R.mipmap.gongbaojiding, 1, "川菜", 0);
        Food food7 = new Food(6 , "地三鲜", 8.40f, 0, R.mipmap.disanxian, 1, "荤素搭配，健康美味", 0);
        Food food8 = new Food(7 , "凉拌黄瓜", 3.99f, 0, R.mipmap.liangbanhuanggua, 1, "新鲜黄瓜", 1);
        Food food9 = new Food(8 , "凉拌土豆丝", 4.40f, 0, R.mipmap.liangbantudousi, 1, "好吃", 1);
        Food food10 = new Food(9 , "红烧海参", 45.00f, 0, R.mipmap.hongshaohaishen, 1, "营养美味", 2);
        Food food11 = new Food(10 , "鱿鱼丝", 9.40f, 0, R.mipmap.youyusi, 1, "最接地气的海鲜", 2);
        Food food12 = new Food(11 , "可口可乐", 3.00f, 0, R.mipmap.kekoukele, 1, "百年品牌", 3);
        Food food13 = new Food(12 , "威士忌", 198.00f, 0, R.mipmap.weishiji, 1, "名酒", 3);
        addFood(food1);
        addFood(food2);
        addFood(food3);
        addFood(food4);
        addFood(food5);
        addFood(food6);
        addFood(food7);
        addFood(food8);
        addFood(food9);
        addFood(food10);
        addFood(food11);
        addFood(food12);
        addFood(food13);
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
        editor.putInt(foodIndex + "category", food.getCategory());
        int foodTotalNum = sp.getInt("foodTotalNum", 0);
        if (0 == foodTotalNum) { //没有则初始化
            editor.putInt("foodTotalNum", 1);
        } else {
            int old = this.getTotalNum();
            editor.putInt("foodTotalNum", old + 1);
        }
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
        food.setCategory(sp.getInt(foodIndex + "category", 0));
        return food;
    }

    //修改菜品的状态，点菜，退订等
    public void updateFoodState(int foodIndex, int foodState) {
        editor.putInt(foodIndex + "foodState", foodState);
        editor.commit();
    }

    //更新菜品库存
    public void updateFoodNumByFoodName(int foodNum, String foodName) {
        for (int i = 0; i < this.getTotalNum(); i++) {
            Food food = this.getFood(i);
            if (foodName.equals(food.getFoodName())) {
                editor.putInt( food.getFoodIndex() + "foodNum", foodNum);
                editor.commit();
                break;
            }
        }
    }

    //修改菜品的备注
    public void updateMemo(int foodIndex, String memo) {
        editor.putString(foodIndex + "memo", memo);
        editor.commit();
    }

    //根据菜品的状态获取菜品列表
    public List<Food> getFoodListByState(int foodState) {
        List<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < this.getTotalNum(); i++) {
            Food food = this.getFood(i);
            if (foodState == food.getFoodState()) {
                foodList.add(food);
            }
        }
        return foodList;
    }

    //获取所有的菜品
    public List<Food> getAllFood() {
        List<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < this.getTotalNum(); i++) {
            Food food = this.getFood(i);
            foodList.add(food);
        }
        return foodList;
    }

    //根据菜品种类获取菜品
    public List<Food> getFoodListByCategory(int category) {
        List<Food> foodList = new ArrayList<Food>();
        for (int i = 0; i < this.getTotalNum(); i++) {
            Food food = this.getFood(i);
            if (category == food.getCategory()) {
                foodList.add(food);
            }
        }
        return foodList;
    }



    //菜品总金额
    public String getFoodTotalPriceByState(int foodState) {
        float total = 0;
        for (int i = 0; i < this.getTotalNum(); i++) {
            Food food = this.getFood(i);
            if (foodState == food.getFoodState())
                total += food.getFoodPrice();
        }
        return String.valueOf(total);
    }

    //菜品总量
    public String getFoodTotalNumByState(int foodState) {
        int total = 0;
        for (int i = 0; i < this.getTotalNum(); i++) {
            Food food = this.getFood(i);
            if (foodState == food.getFoodState())
                total ++;
        }
        return String.valueOf(total);
    }

    //判断某条记录是否存在
    public Boolean isRecordExist(String recordName) {
        String record = sp.getString(recordName, "");
        if (null == record || "".equals(record)) {
            return false;
        }
        return true;
    }

    //获取某条记录
    public String getRecordByName(String recordName) {
        return sp.getString(recordName, "");
    }

    //获取某条记录
    public int getIntRecordByName(String recordName) {
        return sp.getInt(recordName, 0);
    }

    //存入一条字符串记录
    public void addStringRecord(String recordName, String recordValue) {
        editor.putString(recordName, recordValue);
        editor.commit();
    }

    //存入一条整型记录
    public void addIntRecord(String recordName, int recordValue) {
        editor.putInt(recordName, recordValue);
        editor.commit();
    }


    //获取菜品总数
    public  int getTotalNum() {
        return sp.getInt("foodTotalNum", 0);
    }


}
