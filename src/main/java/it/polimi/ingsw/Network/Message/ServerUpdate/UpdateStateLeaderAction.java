package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateStateLeaderAction extends Update {
    private static final long serialVersionUID = 2309901068199675655L;

    private String ID;

    /**
     * this attribute represents the new state of the Leader Card
     *  true -> to active
     *  false -> to discard
     */
    private boolean activated;

    public UpdateStateLeaderAction(String nickname, String ID, boolean activated) {
        super(nickname);
        this.ID = ID;
        this.activated = activated;
    }


    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().updateStateLeaderCard(ID,activated);
        view.onUpdateStateLeaderAction(ID,activated);
    }
}
