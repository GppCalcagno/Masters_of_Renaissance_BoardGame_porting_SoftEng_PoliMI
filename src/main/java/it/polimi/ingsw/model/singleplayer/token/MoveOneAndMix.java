package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoveOneAndMix extends Tokens{

    /**
     *this method increase the faith marker of lorenzo by one and shuffle all the tokens in a new vector
     * @param l lorenzo the player
     */
    @Override
    public void effectTokens(LorenzoIlMagnifico l) throws ActiveVaticanReportException {
        l.increaseFaithMarker(l.getFaithMarker()+1);
        List<Tokens> intList = Arrays.asList(l.getTokensvet());
        Collections.shuffle(intList);
        intList.toArray(l.getTokensvet());
        l.setI(-1);
    }

}
