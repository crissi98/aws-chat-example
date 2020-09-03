package de.crissi98.chat.dynamo;

import de.crissi98.chat.model.Message;
import de.crissi98.chat.model.UserChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ApplicationScoped
public class DatabaseService {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseService.class);

    public static final String CHATS_TABLE = "Chats";
    public static final String CHATS_REVERSED_INDEX = "ChatsReversedMembers";

    public static final String MEMBER_1_KEY = "member1";
    public static final String MEMBER_2_KEY = "member2";
    public static final String CHAT_ID_KEY = "chatId";

    public static final String MESSAGE_TABLE = "ChatMessages";

    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String MESSAGE_KEY = "message";
    public static final String SENDER_KEY = "sender";

    @Inject
    DynamoDbClient client;

    public List<UserChat> getChatsForUser(String username) {
        var resultMapMember1 = client.query(queryUsername(username, MEMBER_1_KEY)).items();
        var resultMapMember2 = client.query(queryUsername(username, MEMBER_2_KEY)).items();
        List<UserChat> result = processChatResults(resultMapMember1, MEMBER_2_KEY);
        result.addAll(processChatResults(resultMapMember2, MEMBER_1_KEY));
        return result;
    }

    public List<Message> getMessagesForChatId(int chatId) {
        var resultMapMessages = client.query(queryMessages(chatId)).items();
        return processMessageResults(resultMapMessages);
    }

    public void addMessageItems() {
        LOG.info("Adding data for chats");
        PutItemRequest put = PutItemRequest.builder()
                .tableName(MESSAGE_TABLE)
                .item(Map.of(MESSAGE_KEY, AttributeValue.builder().s("Moin Meister").build(),
                        TIMESTAMP_KEY, AttributeValue.builder().n(String.valueOf(System.currentTimeMillis() - 10_000L)).build(),
                        CHAT_ID_KEY, AttributeValue.builder().n("1").build(),
                        SENDER_KEY, AttributeValue.builder().s("Dieter").build()))
                .build();
        client.putItem(put);

        put = PutItemRequest.builder()
                .tableName(MESSAGE_TABLE)
                .item(Map.of(MESSAGE_KEY, AttributeValue.builder().s("Servus").build(),
                        TIMESTAMP_KEY, AttributeValue.builder().n(String.valueOf(System.currentTimeMillis() - 10_000L)).build(),
                        CHAT_ID_KEY, AttributeValue.builder().n("1").build(),
                        SENDER_KEY, AttributeValue.builder().s("Hans").build()))
                .build();
        client.putItem(put);
    }

    public void addChatItems() {
        LOG.info("Adding data for messages");
        PutItemRequest put = PutItemRequest.builder()
                .tableName(CHATS_TABLE)
                .item(Map.of(MEMBER_1_KEY, AttributeValue.builder().s("Hans").build(),
                        MEMBER_2_KEY, AttributeValue.builder().s("Dieter").build(),
                        CHAT_ID_KEY, AttributeValue.builder().n("1").build()))
                .build();
        client.putItem(put);

        put = PutItemRequest.builder()
                .tableName(CHATS_TABLE)
                .item(Map.of(MEMBER_1_KEY, AttributeValue.builder().s("Hans").build(),
                        MEMBER_2_KEY, AttributeValue.builder().s("Peter").build(),
                        CHAT_ID_KEY, AttributeValue.builder().n("2").build()))
                .build();
        client.putItem(put);

        put = PutItemRequest.builder()
                .tableName(CHATS_TABLE)
                .item(Map.of(MEMBER_1_KEY, AttributeValue.builder().s("Dieter").build(),
                        MEMBER_2_KEY, AttributeValue.builder().s("Peter").build(),
                        CHAT_ID_KEY, AttributeValue.builder().n("3").build()))
                .build();
        client.putItem(put);
    }

    private QueryRequest queryUsername(String username, String keyName) {
        QueryRequest.Builder builder = QueryRequest.builder()
                .tableName(CHATS_TABLE);
        if (MEMBER_2_KEY.equals(keyName)) {
            builder = builder.indexName(CHATS_REVERSED_INDEX);
        }
        return builder.keyConditionExpression(keyName + "= :user")
                .expressionAttributeValues(Map.of(":user", AttributeValue.builder().s(username).build()))
                .build();
    }

    private QueryRequest queryMessages(int chatId) {
        return QueryRequest.builder()
                .tableName(MESSAGE_TABLE)
                .keyConditionExpression(CHAT_ID_KEY + "= :cId")
                .expressionAttributeValues(Map.of(":cId", AttributeValue.builder().n(String.valueOf(chatId)).build()))
                .build();
    }

    private List<UserChat> processChatResults(List<Map<String, AttributeValue>> chatResult, String partnerKey) {
        return chatResult.stream()
                .map(chatData -> {
                    String partner = chatData.get(partnerKey).s();
                    int chatId = Integer.parseInt(chatData.get(CHAT_ID_KEY).n());
                    return new UserChat(chatId, partner);
                })
                .collect(Collectors.toList());
    }

    private List<Message> processMessageResults(List<Map<String, AttributeValue>> messagesResult) {
        return messagesResult.stream()
                .map(messageData -> {
                    String message = messageData.get(MESSAGE_KEY).s();
                    String sender = messageData.get(SENDER_KEY).s();
                    long timestamp = Long.parseLong(messageData.get(TIMESTAMP_KEY).n());
                    return new Message(sender, message, timestamp);
                })
                .collect(Collectors.toList());
    }
}
