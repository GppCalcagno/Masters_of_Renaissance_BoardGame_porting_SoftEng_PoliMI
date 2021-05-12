package it.polimi.ingsw.Network.message;

public class MessageChooseAfterTakeMarble extends Message{
    int choice;

        /*  seleziona una tra le seguenti mosse:
           0->askExchange;
           1->askAddDiscardMarble;
           2-> askSetDefaulMarbleLeaderEffect;
        */

    public MessageChooseAfterTakeMarble(String nickname, int choice) {
        super(nickname, MessageType.CHOOSEAFTERTAKEMARBLE);
        this.choice=choice;
    }

    public int getChoice() {
        return choice;
    }
}
