package de.crissi98.chat.messages.get;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.dynamo.Message;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("QuarkusChatListMessages")
public class GetMessagesLambda implements RequestHandler<GetMessagesRequest, List<Message>> {

    @Inject
    DatabaseService service;

    @Override
    public List<Message> handleRequest(GetMessagesRequest getMessagesRequest, Context context) {
        return service.getMessagesForChatId(getMessagesRequest.getChatId());
    }
}
