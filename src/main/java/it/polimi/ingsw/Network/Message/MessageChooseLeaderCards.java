package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Controller.GameController;

public class MessageChooseLeaderCards extends Message{
    private static final long serialVersionUID = -7664166014343608100L;

    private int i1;
    private int i2;

    public MessageChooseLeaderCards(String nickname, int i1, int i2) {
        super(nickname, MessageType.CHOOSELEADERCARDS);
        this.i1 = i1;
        this.i2 = i2;
    }

    public int getI1() {
        return i1;
    }

    public int getI2() {
        return i2;
    }

    @Override
    public void action(GameController gameController) {

    }
}
