package de.crissi98.chat.chats.send;

public class NewChatRequest {

    private String member1;
    private String member2;

    public String getMember1() {
        return member1;
    }

    public void setMember1(String member1) {
        this.member1 = member1;
    }

    public String getMember2() {
        return member2;
    }

    public void setMember2(String member2) {
        this.member2 = member2;
    }

    @Override
    public String toString() {
        return "NewChatRequest{" +
                "member1='" + member1 + '\'' +
                ", member2='" + member2 + '\'' +
                '}';
    }
}
