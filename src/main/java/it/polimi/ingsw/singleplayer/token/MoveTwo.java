package it.polimi.ingsw.singleplayer.token;

import it.polimi.ingsw.singleplayer.LorenzoIlMagnifico;
import it.polimi.ingsw.singleplayer.token.Tokens;

public class MoveTwo extends Tokens {

    /**
     *this method increase the faith marker of lorenzo by two
     */
    public void effectTokens(LorenzoIlMagnifico l){
        l.increaseFaithMarker(l.getFaithMarker()+2);
        System.out.println("MoveTwo");
    }
}
