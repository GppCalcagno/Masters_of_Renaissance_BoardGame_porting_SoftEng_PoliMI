package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;

public class UpdateInitialLeaderCards extends Update {
    private static final long serialVersionUID = -8636343470594316197L;

    private List<String> leaderCardsID;

    public UpdateInitialLeaderCards(String nickname, List<String> leaderCardsID) {
        super(nickname);
        this.leaderCardsID = leaderCardsID;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().setLeaderCard(leaderCardsID);
        view.onUpdateInitialLeaderCards(leaderCardsID);
    }
}
