package de.crissi98.chat.chats.send;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.crissi98.chat.dynamo.DatabaseService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Random;

@Named("QuarkusChatAddChat")
public class CreateChatLambda implements RequestHandler<NewChatRequest, String> {

    @Inject
    DatabaseService service;

    private final Random random = new Random();

    @Override
    public String handleRequest(NewChatRequest chatRequest, Context context) {
        service.createNewChat(chatRequest, random.nextInt());
        return "OK";
    }
}
