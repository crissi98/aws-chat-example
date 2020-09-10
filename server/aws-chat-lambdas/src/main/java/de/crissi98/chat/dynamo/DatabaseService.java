package de.crissi98.chat.dynamo;

import de.crissi98.chat.chats.send.NewChatRequest;
import de.crissi98.chat.messages.send.NewMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class DatabaseService {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseService.class);
    private static final DynamoDbEnhancedClient client = DynamoDbEnhancedClient.create();

    DynamoDbTable<Chat> chatTable;
    DynamoDbIndex<Chat> chatIndexReversed;
    DynamoDbTable<Message> messageTable;

    @PostConstruct
    void init() {
        LOG.info("Start init");
        chatTable = client.table(Chat.TABLE_NAME, TableSchema.fromBean(Chat.class));
        chatIndexReversed = chatTable.index(Chat.REVERSED_INDEX_NAME);
        messageTable = client.table(Message.TABLE_NAME, TableSchema.fromBean(Message.class));
        LOG.info("Finished init");
    }

    public List<Chat> getChatsForUser(String username) {
        LOG.info("Querying chats for {}", username);
        List<Chat> chats1 = chatTable.query(queryUsername(username))
                .items()
                .stream()
                .collect(Collectors.toList());
        List<Chat> chats2 = chatIndexReversed.query(queryUsername(username))
                .stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
        List<Chat> result = new ArrayList<>(chats1);
        result.addAll(chats2);
        LOG.info("{} chats found", result.size());
        return result;
    }

    public List<Message> getMessagesForChatId(int chatId) {
        LOG.info("Querying messages for chat {}", chatId);
        List<Message> result = messageTable.query(queryMessages(chatId))
                .items()
                .stream()
                .collect(Collectors.toList());
        LOG.info("{} messages found", result.size());
        return result;
    }

    public void createNewChat(NewChatRequest chatRequest, int generatedChatId) {
        Chat chat = new Chat(chatRequest.getMember1(), chatRequest.getMember2(), generatedChatId);
        chatTable.putItem(chat);
    }

    public void addMessageToChat(NewMessageRequest messageRequest, long generatedTimestamp) {
        Message message = new Message(messageRequest.getChatId(), generatedTimestamp, messageRequest.getMessage(),
                messageRequest.getSender());
        messageTable.putItem(message);
    }

    public void addMessageItems() {
        LOG.info("Adding data for chats");
        messageTable.putItem(new Message(1, System.currentTimeMillis() - 10_000L, "Dieter", "Moin Meister"));
        messageTable.putItem(new Message(1, System.currentTimeMillis(), "Hans", "Servus"));
    }

    public void addChatItems() {
        LOG.info("Adding data for chats");
        chatTable.putItem(new Chat("Hans", "Dieter", 1));
        chatTable.putItem(new Chat("Hans", "Peter", 2));
        chatTable.putItem(new Chat("Dieter", "Peter", 3));
    }

    private QueryEnhancedRequest queryUsername(String username) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(username)
                        .build()))
                .build();
    }

    private QueryEnhancedRequest queryMessages(int chatId) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(chatId)
                        .build()))
                .build();
    }

}
