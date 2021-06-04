package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateDevCardDeck extends Update {
    private static final long serialVersionUID = -1087115289891404959L;

    private String cardID;

    public UpdateDevCardDeck(String nickname, String cardID) {
        super(nickname);
        this.cardID=cardID;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().removeCardfromDevCardDeck(cardID);
        view.onUpdateDevCardDeck(cardID);
    }
}
