package de.crissi98.chat;

import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.Message;
import de.crissi98.chat.model.NewMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private static final Logger LOG = LoggerFactory.getLogger(MessageResource.class);

    @Inject
    DatabaseService service;

    @GET
    @Path("/chatId/{id}")
    public List<Message> getMessages(@PathParam("id") int chatId) {
        LOG.info("Get messages for chatID {}", chatId);
        return service.getMessagesForChatId(chatId);
    }

    @POST
    @Path("/sendMessage")
    public Response addMessageToChat(NewMessageRequest messageRequest) {
        service.addMessageToChat(messageRequest, System.currentTimeMillis());
        LOG.info("Added message {}", messageRequest);
        return Response.noContent().build();
    }

}
