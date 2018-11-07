package es.source.code.enums;

public enum FoodCategory {
    COLD(0, "冷菜"),
    HOT(1, "热菜"),
    SEA(2, "海鲜"),
    DRINK(3, "酒水");

    private int value;
    private String key;


    FoodCategory(int value, String key) {
        this.value = value;
        this.key = key;
    }
}
