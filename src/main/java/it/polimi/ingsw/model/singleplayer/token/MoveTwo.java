package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

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

        Tokens[] vec= l.getTokensvet();
        Tokens temp= vec[0];
        vec[0]=vec[6];
        vec[6]= temp;
    }

    @Override
    public String getID() {
        return ID;
    }
}
