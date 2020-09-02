package de.crissi98.chat.dynamo;

import de.crissi98.chat.model.UserChat;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
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

    @Inject
    DynamoDbClient client;

    public List<UserChat> getChatsForUser(String username) {
        return client.query(queryUsername(username, MEMBER_1_KEY)).items()
                .stream()
                .map(memberResult -> {
                    String partner = memberResult.get(MEMBER_2_KEY).s();
                    int chatId = Integer.parseInt(memberResult.get(CHAT_ID_KEY).n());
                    return new UserChat(chatId, partner);
                })
                .collect(Collectors.toList());
    }

    public QueryRequest queryUsername(String username, String keyName) {
        QueryRequest.Builder builder = QueryRequest.builder()
                .tableName(CHATS_TABLE);
        if (MEMBER_2_KEY.equals(keyName)) {
            builder = builder.indexName(CHATS_REVERSED_INDEX);
        }
        return builder.keyConditionExpression(keyName + "= :user")
                .expressionAttributeValues(Map.of(":user", AttributeValue.builder().s(username).build()))
                .build();
    }

    public void addItems() {
        LOG.info("Adding data");
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
                        CHAT_ID_KEY, AttributeValue.builder().n("2").build()))
                .build();
        client.putItem(put);
    }
}
