package it.polimi.ingsw.Network.message;

public class MessageChooseTurn extends Message{
    int turnID;
    /*  0->extractMarble
        1-> selectDevCard
        2->chooseResForBaseprod
        3->ActiveProdDevCard
        4->activeprodLeaderCard
        5->Update stateLeadercard
     */

    public MessageChooseTurn(String nickname,int turnID) {
        super(nickname, MessageType.CHOOSETURN);
        this.turnID=turnID;
    }

    public int getTurnID() {
        return turnID;
    }
}
