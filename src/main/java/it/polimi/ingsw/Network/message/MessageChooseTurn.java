package it.polimi.ingsw.Network.message;

public class MessageChooseTurn extends Message{
    int turnID;
    //0->ActiveLeaderAction
    //1-> MarketTray
    //2->PurchaseCard
    //3->ActiveProduction

    public MessageChooseTurn(String nickname,int turnID) {
        super(nickname, MessageType.CHOOSETURN);
        this.turnID=turnID;
    }

    public int getTurnID() {
        return turnID;
    }
}
