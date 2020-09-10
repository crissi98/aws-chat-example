package de.crissi98.chat.dynamo;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Chat {

    public static final String TABLE_NAME = "Chats";
    public static final String REVERSED_INDEX_NAME = "ChatsReversedMembers";

    private String member1;
    private String member2;
    private int chatId;

    public Chat() {
    }

    public Chat(String member1, String member2, int chatId) {
        this.member1 = member1;
        this.member2 = member2;
        this.chatId = chatId;
    }

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = REVERSED_INDEX_NAME)
    public String getMember1() {
        return member1;
    }
    public void setMember1(String member1) {
        this.member1 = member1;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = REVERSED_INDEX_NAME)
    public String getMember2() {
        return member2;
    }
    public void setMember2(String member2) {
        this.member2 = member2;
    }

    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

}
