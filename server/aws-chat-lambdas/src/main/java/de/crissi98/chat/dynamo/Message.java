package de.crissi98.chat.dynamo;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Message {

    public static final String TABLE_NAME = "ChatMessages";

    private int chatId;
    private long timestamp;
    private String message;
    private String sender;

    public Message() {
    }

    public Message(int chatId, long timestamp, String message, String sender) {
        this.chatId = chatId;
        this.timestamp = timestamp;
        this.message = message;
        this.sender = sender;
    }

    @DynamoDbPartitionKey
    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    @DynamoDbSortKey
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
