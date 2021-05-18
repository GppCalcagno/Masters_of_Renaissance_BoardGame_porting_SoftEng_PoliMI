package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;

public class MessageNumPlayer extends Message {
    int num;


    public MessageNumPlayer(String nickname, int num) {
        super(nickname, MessageType.NUMPLAYERS);
        this.num=num;
    }

    @Override
    public void action(GameController gameController){
        //missing method
    }

}
