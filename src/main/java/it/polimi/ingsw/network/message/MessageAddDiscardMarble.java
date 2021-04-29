package it.polimi.ingsw.network.message;

public class MessageAddDiscardMarble extends Message {
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
