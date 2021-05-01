package it.polimi.ingsw.Network.message;

public class MessageAddDiscardMarble extends Message {
    private static final long serialVersionUID = 396356886903614801L;

    private boolean choice;

    private int indexwarehouse;

    public MessageAddDiscardMarble(String nickname, MessageType messageType, boolean choice, int indexwarehouse) {
        super(nickname, messageType);
        this.choice = choice;
        this.indexwarehouse = indexwarehouse;
    }

    public boolean isChoice() {
        return choice;
    }

    public int getIndexwarehouse() {
        return indexwarehouse;
    }
}
