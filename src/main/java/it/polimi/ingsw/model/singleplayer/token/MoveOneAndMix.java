package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoveOneAndMix extends Tokens{
    private String ID;

    public MoveOneAndMix() {
        this.ID = "T2";
    }

    /**
     *this method increase the faith marker of lorenzo by one and shuffle all the tokens in a new vector
     * @param l lorenzo the player
     */
    @Override
    public void effectTokens(LorenzoIlMagnifico l) throws ActiveVaticanReportException {
        l.increaseFaithMarker(1);
        Collections.shuffle(l.getTokensvet());
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getColor() {
        return null;
    }
}
