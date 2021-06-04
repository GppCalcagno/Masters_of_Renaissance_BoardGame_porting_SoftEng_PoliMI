package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateMarketTray extends Update {
    private static final long serialVersionUID = 1842562788535859189L;
    private char direction;
    private int num;

    public UpdateMarketTray(String nickname, char direction, int num) {
        super(nickname);
        this.direction=direction;
        this.num=num;
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().updateMarketTray(direction,num);
        view.onUpdateMarketTray();
    }
}
