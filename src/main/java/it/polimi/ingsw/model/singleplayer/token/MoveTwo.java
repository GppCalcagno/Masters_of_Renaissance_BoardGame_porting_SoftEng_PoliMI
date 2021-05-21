package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

import java.util.List;

public class MoveTwo extends Tokens {
    private String ID;

    public MoveTwo() {
        this.ID = "T1";
    }

    /**
     *this method increase the faith marker of lorenzo by two.
     */
    public void effectTokens(LorenzoIlMagnifico l) throws  ActiveVaticanReportException {
        l.increaseFaithMarker(2);

        List<Tokens> tokensList= l.getTokensvet();
        Tokens temp= tokensList.remove(0);
        tokensList.add(6,temp);
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
