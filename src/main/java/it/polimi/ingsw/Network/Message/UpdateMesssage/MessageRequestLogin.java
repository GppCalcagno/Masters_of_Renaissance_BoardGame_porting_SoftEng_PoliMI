package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Client.PingerTimerTask;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageRequestLogin extends Message {
    public MessageRequestLogin() {
        super("server", MessageType.REQUESTLOGIN);
    }

    @Override
    public  void update(ViewInterface view){
        view.askLogin();
    }
}
