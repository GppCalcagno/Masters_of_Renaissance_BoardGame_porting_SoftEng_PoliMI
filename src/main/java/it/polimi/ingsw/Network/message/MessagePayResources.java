package it.polimi.ingsw.Network.message;

public class MessagePayResources extends Message{

    private static final long serialVersionUID = -4554480045749065772L;

    private char c;
    private String resources;
    private int numOf;

    public MessagePayResources(String nickname, char c, String resources, int numOf) {
        super(nickname, MessageType.PAYRESOURCES);
        this.c = c;
        this.resources = resources;
        this.numOf = numOf;
    }

    public char getC() {
        return c;
    }

    public int getNumOf() {
        return numOf;
    }

    public String getResources() {
        return resources;
    }
}

