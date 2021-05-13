package it.polimi.ingsw.Network.message;

public class MessageChooseProductionType extends Message{
    int choice;
    /*  0 -> askChooseResourcesBaseProduction
        1 -> askActiveProductionDevCard
        2 -> ActiveLeaderCardProduction
        3 -> End Production
    */

    public MessageChooseProductionType(String nickname, int productionType) {
        super(nickname, MessageType.CHOOSEPRODUCTIONTYPE);
        choice= productionType;
    }

    public int getChoice() {
        return choice;
    }
}
