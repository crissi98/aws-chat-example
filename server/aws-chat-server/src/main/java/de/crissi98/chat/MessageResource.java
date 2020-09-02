package de.crissi98.chat;

import de.crissi98.chat.model.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/messages")
//@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    @GET
    @Path("/chatId/{id}")
    public List<Message> getMessages(@PathParam("id") int chatId) {
        if (chatId == 1) {
            return List.of(new Message("Dieter", "Moin Meister", System.currentTimeMillis() - 10_000L),
                    new Message("Hans", "Grüß dich", System.currentTimeMillis()))
                    .stream()
                    .sorted(Comparator.comparing(Message::getTimestamp))
                    .collect(Collectors.toList());
        }
        throw new NotFoundException("Chat with id " + chatId + " does not exist");
    }

}
