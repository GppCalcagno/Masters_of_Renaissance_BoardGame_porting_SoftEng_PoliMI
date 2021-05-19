package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdateSlotDevCards extends Message {
    private static final long serialVersionUID = 5506750615087476947L;

    private String ID;
    private int column;

    public MessageUpdateSlotDevCards(String nickname, String ID, int column) {
        super(nickname, MessageType.UPDATESLOTDEVCARDS);
        this.ID = ID;
        this.column = column;
    }

    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.updateSlotDevCard(ID,column);
        view.onUpdateSlotDevCards();
    }
}
