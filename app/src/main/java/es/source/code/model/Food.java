package es.source.code.model;

import java.io.Serializable;

import es.source.code.R;

public class Food implements Serializable {
    private int foodIndex;
    private String foodName;
    private float foodPrice;
    private int foodState;//0表示未点的菜，1表示已点的菜（未下单菜），2表示已下单的菜，3表示已经结账的菜
    private int foodPhoto;
    private int foodNum;
    private String memo;

    private int category;

    public Food() {
    }

    public Food(int foodIndex, String foodName, float foodPrice, int foodState, int foodPhoto,
                int foodNum, String memo, int category) {
        this.foodIndex = foodIndex;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodState = foodState;
        this.foodPhoto = foodPhoto;
        this.foodNum = foodNum;
        this.memo = memo;
        this.category = category;
    }

    public Food(int foodIndex, String foodName, float foodPrice, int foodCategory) {
        this.foodIndex = foodIndex;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodState = 0;
        this.foodPhoto = R.mipmap.logo;
        this.foodNum = 1;
        this.category = foodCategory;
        this.memo = "默认备注";
    }

    public int getFoodIndex() {
        return foodIndex;
    }

    public void setFoodIndex(int foodIndex) {
        this.foodIndex = foodIndex;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodState() {
        return foodState;
    }

    public void setFoodState(int foodState) {
        this.foodState = foodState;
    }

    public int getFoodPhoto() {
        return foodPhoto;
    }

    public void setFoodPhoto(int foodPhoto) {
        this.foodPhoto = foodPhoto;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
