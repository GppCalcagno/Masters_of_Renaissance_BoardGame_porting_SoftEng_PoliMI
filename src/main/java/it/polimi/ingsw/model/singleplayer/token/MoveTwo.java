package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

public class MoveTwo extends Tokens {

    /**
     *this method increase the faith marker of lorenzo by two.
     */
    public void effectTokens(LorenzoIlMagnifico l) throws  ActiveVaticanReportException {
        l.increaseFaithMarker(2);
    }
}