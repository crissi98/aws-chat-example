package de.crissi98.chat.model;

public class UserChat {

    private int id;
    private String partner;

    public UserChat() {
    }

    public UserChat(int id, String partner) {
        this.id = id;
        this.partner = partner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
