package it.polimi.ingsw.Network.message;

public class MessageChosenResourceBaseProduction extends Message{
    private static final long serialVersionUID = -5229293564538828139L;

    private String resource;

    public MessageChosenResourceBaseProduction(String nickname, String resources) {
        super(nickname, MessageType.ACTIVESBASEPRODUCTION);
        this.resource = resources;
    }

    public String getResource() {
        return resource;
    }
}
