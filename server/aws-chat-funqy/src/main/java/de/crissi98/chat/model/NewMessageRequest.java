package de.crissi98.chat.model;


public class NewMessageRequest {

    private int chatId;
    private String sender;
    private String message;

    public NewMessageRequest() {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NewMessageRequest{" +
                "chatId=" + chatId +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
