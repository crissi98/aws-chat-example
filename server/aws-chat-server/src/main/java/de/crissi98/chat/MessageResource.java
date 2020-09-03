package de.crissi98.chat;

import de.crissi98.chat.dynamo.DatabaseService;
import de.crissi98.chat.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
        return service.getMessagesForChatId(chatId);
    }

    @GET
    @Path("/add")
    public void addMessages() {
        service.addMessageItems();
        LOG.info("Test-items added for messages");
    }

}
