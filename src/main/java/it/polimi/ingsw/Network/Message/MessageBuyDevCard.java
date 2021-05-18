package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.controller.GameController;

public class MessageBuyDevCard extends Message{

    private static final long serialVersionUID = 1325268486763010305L;

    private String ID;
    private int columnSlotDevCard;

    public MessageBuyDevCard(String nickname, String ID, int columnSlotDevCard) {
        super(nickname, MessageType.BUYDEVCARD);
        this.ID = ID;
        this.columnSlotDevCard = columnSlotDevCard;
    }

    public String getID() {
        return ID;
    }

    public int getColumnSlotDevCard() {
        return columnSlotDevCard;
    }

    @Override
    public void action(GameController gameController) {

    }
}
