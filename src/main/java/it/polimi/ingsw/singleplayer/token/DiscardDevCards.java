package it.polimi.ingsw.singleplayer.token;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.singleplayer.LorenzoIlMagnifico;

import java.util.NoSuchElementException;

public class DiscardDevCards extends Tokens {

    /**
     * every discardDevCards token has a color, that is the color of the cards that have to be deleted
     */
    private ColorCard color;

    public DiscardDevCards(ColorCard color){
        this.color = color;
    }

    /**
     * this method active one particular token, that one where Lorenzo Discard 2 Developement card.
     */
    @Override
    public void effectTokens(LorenzoIlMagnifico l) throws  NoSuchElementException {
        int i = l.getDevelopmentDeck().getDevelopmentCardDeck().length - 1;
        for (int j = 0; j < 2; j++) {
            while (i > 0 && l.getDevelopmentDeck().getDevCards(i, l.getDevelopmentDeck().getColumnFromColor(color)) == null) {
                i--;
            }
            if (i < 0) throw new NoSuchElementException();
            else l.getDevelopmentDeck().removeDevCards(i, l.getDevelopmentDeck().getColumnFromColor(color));
        }
    }

}