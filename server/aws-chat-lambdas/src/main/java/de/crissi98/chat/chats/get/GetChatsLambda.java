package de.crissi98.chat.chats.get;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.crissi98.chat.dynamo.Chat;
import de.crissi98.chat.dynamo.DatabaseService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("QuarkusChatListChats")
public class GetChatsLambda implements RequestHandler<GetChatsRequest, List<Chat>> {

    @Inject
    DatabaseService service;

    @Override
    public List<Chat> handleRequest(GetChatsRequest getChatsRequest, Context context) {
        return service.getChatsForUser(getChatsRequest.getUsername());
    }
}
