package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateSlotDevCards extends Update {
    private static final long serialVersionUID = 5506750615087476947L;

    private String ID;
    private int column;

    public UpdateSlotDevCards(String nickname, String ID, int column) {
        super(nickname);
        this.ID = ID;
        this.column = column;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().updateSlotDevCard(ID,column);
        view.onUpdateSlotDevCards();
    }
}
