package it.polimi.ingsw.Network.message;

public class MessageInsertCard extends  Message{
    private static final long serialVersionUID = -446418429850278596L;

    private int columnSlotDev;

    public MessageInsertCard(String nickname, int columnSlotDev) {
        super(nickname, MessageType.INSERTCARD);
        this.columnSlotDev = columnSlotDev;
    }

    public int getColumnSlotDev() {
        return columnSlotDev;
    }
}
