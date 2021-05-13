package it.polimi.ingsw.Network.message;

public class MessageAddDiscardLeaderCard extends Message {
    private static final long serialVersionUID = 834631015175905858L;

    /**
     * This attribute is 0 if the player wants to discard the leader card, 1 to add
     */
    private boolean addOrDiscard;

    private String ID;

    public MessageAddDiscardLeaderCard(String nickname, boolean addOrDiscard, String id) {
        super(nickname, MessageType.UPDATESTATELEADERACTION);
        this.addOrDiscard = addOrDiscard;
        ID = id;
    }

    public boolean isAddOrDiscard() {
        return addOrDiscard;
    }

    public String getID() {
        return ID;
    }
}
