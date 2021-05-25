package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageActivatedDevCardProduction extends Message {
    private static final long serialVersionUID = 5954266390601357447L;

    private String ID;

    public MessageActivatedDevCardProduction(String nickname, String ID) {
        super(nickname, MessageType.UPDATEACTIVATEDDEVCARDPRODUCTION);
        this.ID = ID;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setActivedDevCardProd(ID);
        view.onUpdateActivatedDevCardProduction(ID);
    }
}
