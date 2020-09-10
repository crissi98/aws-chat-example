package de.crissi98.chat.messages.get;

import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.Message;
import io.quarkus.funqy.Funq;

import javax.inject.Inject;
import java.util.List;


public class GetMessagesFunction {

    @Inject
    DatabaseService service;

    @Funq("QuarkusChatListMessages")
    public List<Message> handleRequest(GetMessagesRequest getMessagesRequest) {
        return service.getMessagesForChatId(getMessagesRequest.getChatId());
    }
}
