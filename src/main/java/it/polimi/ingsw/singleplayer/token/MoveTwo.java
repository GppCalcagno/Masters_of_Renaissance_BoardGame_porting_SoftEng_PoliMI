package it.polimi.ingsw.singleplayer.token;

import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.singleplayer.LorenzoIlMagnifico;
import it.polimi.ingsw.singleplayer.token.Tokens;

import java.io.IOException;

public class MoveTwo extends Tokens {

    /**
     *this method increase the faith marker of lorenzo by two.
     */
    public void effectTokens(LorenzoIlMagnifico l) throws NegativeQuantityExceptions {
        l.increaseFaithMarker(l.getFaithMarker()+2);
    }
}
