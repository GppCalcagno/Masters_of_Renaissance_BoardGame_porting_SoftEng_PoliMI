package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdateMarketTray extends Message {
    private static final long serialVersionUID = 1842562788535859189L;
    private char direction;
    private int num;

    public MessageUpdateMarketTray(String nickname, char direction, int num) {
        super(nickname, MessageType.UPDATEMARKETTRAY);
        this.direction=direction;
        this.num=num;
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.updateMarketTray(direction,num);
        view.onUpdateMarketTray();
    }
}
