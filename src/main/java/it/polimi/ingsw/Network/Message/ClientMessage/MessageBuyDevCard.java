package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageBuyDevCard extends Message {
    private static final long serialVersionUID = 1325268486763010305L;

    private String ID;
    private int columnSlotDevCard;

    public MessageBuyDevCard(String nickname, String ID, int columnSlotDevCard) {
        super(nickname);
        this.ID = ID;
        this.columnSlotDevCard = columnSlotDevCard;
    }

    @Override
    public void action(GameController gameController) {
        gameController.selectDevCard(ID,columnSlotDevCard);
    }
}
