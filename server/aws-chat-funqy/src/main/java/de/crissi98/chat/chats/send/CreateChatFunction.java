package de.crissi98.chat.chats.send;

import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.NewChatRequest;
import io.quarkus.funqy.Funq;

import javax.inject.Inject;
import java.util.Random;


public class CreateChatFunction {

    @Inject
    DatabaseService service;

    private final Random random = new Random();

    @Funq("QuarkusChatAddChat")
    public String handleRequest(NewChatRequest chatRequest) {
        service.createNewChat(chatRequest, random.nextInt());
        return "OK";
    }
}
