package es.source.code.util;

public class EventBusMessage {
    private String messageName;
    private int intMessage;

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
}
