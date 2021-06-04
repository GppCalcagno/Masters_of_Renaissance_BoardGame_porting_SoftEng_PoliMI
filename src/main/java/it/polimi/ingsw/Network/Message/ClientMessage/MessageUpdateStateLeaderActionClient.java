package it.polimi.ingsw.Network.Message.ClientMessage;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Message.Message;

public class MessageUpdateStateLeaderActionClient extends Message {
    private static final long serialVersionUID= 4527898940305604078L;
    private String ID;
    private int choice;

    public MessageUpdateStateLeaderActionClient(String nickname,String ID,int choice) {
        super(nickname);
        this.ID=ID;
        this.choice=choice;
    }


    @Override
    public void action(GameController gameController){
        gameController.updateLeaderCard(ID,choice);
    }
}
