package it.polimi.ingsw.Network.message;

public class MessageUpdateSlotDevCards extends Message{
    private static final long serialVersionUID = 5506750615087476947L;

    private String[][] slotDevCards;

    public MessageUpdateSlotDevCards(String nickname, String[][] slotDevCards) {
        super(nickname, MessageType.UPDATESLOTDEVCARDS);
        this.slotDevCards = slotDevCards;
    }

    public String[][] getSlotDevCards() {
        return slotDevCards;
    }
}
