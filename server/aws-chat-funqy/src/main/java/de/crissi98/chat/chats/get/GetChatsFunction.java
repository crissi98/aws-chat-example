package de.crissi98.chat.chats.get;

import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.UserChat;
import io.quarkus.funqy.Funq;

import javax.inject.Inject;
import java.util.List;

public class GetChatsFunction {

    @Inject
    DatabaseService service;

    @Funq("QuarkusChatListChats")
    public List<UserChat> handleRequest(GetChatsRequest getChatsRequest) {
        return service.getChatsForUser(getChatsRequest.getUsername());
    }
}
