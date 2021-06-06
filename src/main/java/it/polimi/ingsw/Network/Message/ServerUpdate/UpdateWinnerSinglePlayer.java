package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateWinnerSinglePlayer extends Update {
    private static final long serialVersionUID = 229329624772076140L;

    private boolean win;
    private int finalpoint;

    public UpdateWinnerSinglePlayer(boolean win, int finalpoint) {
        super("server");
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
    public void update(ViewInterface view) {
        view.getPlayerBoard().updateWinner(win,finalpoint);
        view.onUpdateWinnerSinglePlayer();

    }
}
