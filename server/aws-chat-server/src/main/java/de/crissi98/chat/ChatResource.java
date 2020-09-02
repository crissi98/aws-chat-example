package de.crissi98.chat;

import de.crissi98.chat.model.UserChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/chats")
//@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {

    private static final Logger LOG = LoggerFactory.getLogger(ChatResource.class);

    @GET
    @Path("/username/{user}")
    public List<UserChat> getChatsForUser(@PathParam("user") String username) {
        LOG.info("Get chats for user {}", username);
        if ("Hans".equals(username)) {
            return List.of(new UserChat(1, "Dieter"), new UserChat(2, "Peter"));
        }
        throw new NotFoundException("User " + username + " is unknown");
    }


}
