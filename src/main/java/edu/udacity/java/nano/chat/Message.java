package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {
    private String username;
    private String message;
    private MessageType type;
    //private String type;
    private String onlineCount;

    public String getMessage() {
        return message;
    }

    public String getOnlineCount() {
        return onlineCount;
    }

    public Enum getMessageType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public Message() {}
    public Message(String message) { this.message = message; }
    public enum MessageType { SPEAK, ENTER, LEAVE };

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOnlineCount(String onlineCount) {
        this.onlineCount = onlineCount;
    }

    public void setMessageType(MessageType type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
