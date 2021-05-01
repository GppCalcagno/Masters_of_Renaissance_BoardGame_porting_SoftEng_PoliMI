package it.polimi.ingsw.Network.message;

public abstract class Message {
    private static final long serialVersionUID = -7212814926393631108L;

    private String nickname;

    private MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
