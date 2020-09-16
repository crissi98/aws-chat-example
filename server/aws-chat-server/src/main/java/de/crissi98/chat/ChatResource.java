package de.crissi98.chat;

import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.NewChatRequest;
import de.crissi98.chat.model.UserChat;
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
import java.util.Random;

@Path("/chats")
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {

    private static final Logger LOG = LoggerFactory.getLogger(ChatResource.class);

    @Inject
    DatabaseService service;

    Random random = new Random();

    @GET
    @Path("/username/{user}")
    public List<UserChat> getChatsForUser(@PathParam("user") String username) {
        LOG.info("Get chats for user {}", username);
        return service.getChatsForUser(username);
    }

    @POST
    @Path("/newChat")
    public Response newChat(NewChatRequest chatRequest) {
        int generatedChatId = random.nextInt();
        LOG.info("Adding new chat {} with id {}", chatRequest, generatedChatId);
        service.createNewChat(chatRequest, generatedChatId);
        return Response.noContent().build();
    }


}
