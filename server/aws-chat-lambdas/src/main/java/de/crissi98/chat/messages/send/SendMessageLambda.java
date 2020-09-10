package de.crissi98.chat.messages.send;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.crissi98.chat.dynamo.DatabaseService;

import javax.inject.Inject;
import javax.inject.Named;

@Named("QuarkusChatAddMessage")
public class SendMessageLambda implements RequestHandler<NewMessageRequest, String> {

    @Inject
    DatabaseService service;

    @Override
    public String handleRequest(NewMessageRequest messageRequest, Context context) {
        service.addMessageToChat(messageRequest, System.currentTimeMillis());
        return "OK";
    }
}
