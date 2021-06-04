package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageChooseLeaderCards extends Message {
    private static final long serialVersionUID = -7664166014343608100L;

    private int i1;
    private int i2;

    public MessageChooseLeaderCards(String nickname, int i1, int i2) {
        super(nickname);
        this.i1 = i1;
        this.i2 = i2;
    }


    @Override
    public void action(GameController gameController) {
        gameController.chooseInitialLeaderCards(i1,i2);

    }
}
