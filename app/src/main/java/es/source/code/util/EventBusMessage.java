package es.source.code.util;

public class EventBusMessage {
    private String messageName;//菜品的名字
    private int intMessage;//消息的状态码
    private int foodNum;//菜品的数量

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public int getIntMessage() {
        return intMessage;
    }

    public void setIntMessage(int intMessage) {
        this.intMessage = intMessage;
    }



    public EventBusMessage(String messageName, int intMessage) {
        this.messageName = messageName;
        this.intMessage = intMessage;
    }

    public EventBusMessage() {
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }
}
