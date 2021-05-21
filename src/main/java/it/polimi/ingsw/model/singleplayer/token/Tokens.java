package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

public abstract class Tokens {
    /**
     * MoveTwo=T1
     * MoveOneAndMix=T2
     * DiscardDevCards=T3
     */
    private String ID;

    /**
     * this is the constructor
     */
    public Tokens(){}

    /**
     * this method active the effect of the token
     * @param l is the computer that call it.
     */
    public void effectTokens(LorenzoIlMagnifico l) throws ActiveVaticanReportException {

    }

    public String getID() {
        return ID;
    }

    public abstract String getColor();
}
