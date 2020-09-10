package de.crissi98.chat.messages.get;

public class GetMessagesRequest {

    private int chatId;

    public GetMessagesRequest() {
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }
}
