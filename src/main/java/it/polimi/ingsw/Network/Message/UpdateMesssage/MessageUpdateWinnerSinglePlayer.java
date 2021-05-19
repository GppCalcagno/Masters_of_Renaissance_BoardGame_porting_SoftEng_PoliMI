package it.polimi.ingsw.Network.Message.UpdateMesssage;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.View.ViewInterface;

public class MessageUpdateWinnerSinglePlayer extends Message {
    private static final long serialVersionUID = 229329624772076140L;

    private boolean win;
    private int finalpoint;

    public MessageUpdateWinnerSinglePlayer(boolean win, int finalpoint) {
        super("server", MessageType.UPDATEWINNERSINGLEPLAYER);
        this.win = win;
        this.finalpoint= finalpoint;
    }

    public boolean isWin() {
        return win;
    }

    public int getFinalpoint() {
        return finalpoint;
    }


    @Override
    public void update(PlayerBoard playerBoard, ViewInterface view) {
        playerBoard.updateWinner(win,finalpoint);
    }
}
