package it.polimi.ingsw.Network.message;

public class MessageActiveProductionDevCard extends Message{
    private static final long serialVersionUID = -4418737464195662873L;

    private int column;

    public MessageActiveProductionDevCard(String nickname, MessageType messageType, int column) {
        super(nickname, messageType);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
