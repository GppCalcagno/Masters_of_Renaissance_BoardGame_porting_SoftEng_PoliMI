package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageUpdateSlotDevCards extends Message {
    private static final long serialVersionUID = 5506750615087476947L;

    private String ID;
    private int column;

    public MessageUpdateSlotDevCards(String nickname, String ID, int column) {
        super(nickname, MessageType.UPDATESLOTDEVCARDS);
        this.ID = ID;
        this.column = column;
    }

    public String getID() {
        return ID;
    }

    public int getColumn() {
        return column;
    }


    @Override
    public void update(PlayerBoard playerBoard) {
        super.update(playerBoard);
    }
}
