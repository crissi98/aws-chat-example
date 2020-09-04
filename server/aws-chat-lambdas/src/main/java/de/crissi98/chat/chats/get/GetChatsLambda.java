package de.crissi98.chat.chats.get;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.UserChat;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("QuarkusChatListChats")
public class GetChatsLambda implements RequestHandler<GetChatsRequest, List<UserChat>> {

    @Inject
    DatabaseService service;

    @Override
    public List<UserChat> handleRequest(GetChatsRequest getChatsRequest, Context context) {
        return service.getChatsForUser(getChatsRequest.getUsername());
    }
}
