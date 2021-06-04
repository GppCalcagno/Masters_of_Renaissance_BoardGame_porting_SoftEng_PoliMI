package it.polimi.ingsw.Network.Message.ServerUpdate;

import it.polimi.ingsw.Network.Message.Update;
import it.polimi.ingsw.View.ViewInterface;

public class UpdateSinglePlayerGame extends Update {
    private static final long serialVersionUID = -2807318014979699841L;


    private int blackCrossToken;
    private String [][][] devCardDeck;

    /**
     * This attribute indicates the extracted token's ID
     */
    private String ID;

    private String tokenColor;

    public UpdateSinglePlayerGame(int blackCrossToken, String id, String[][][] devCardDeck, String tokenColor) {
        super("Lorenzo");
        this.blackCrossToken = blackCrossToken;
        this.ID = id;
        this.devCardDeck=devCardDeck;
        this.tokenColor = tokenColor;
    }

    @Override
    public void update(ViewInterface view) {
        view.getPlayerBoard().singlePlayerUpdate(devCardDeck,blackCrossToken,ID, tokenColor);
        view.onUpdateSinglePlayerGame();
    }
}
