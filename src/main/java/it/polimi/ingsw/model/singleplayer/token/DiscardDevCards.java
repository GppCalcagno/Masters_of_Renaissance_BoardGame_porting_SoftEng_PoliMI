package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;

import java.util.List;
import java.util.NoSuchElementException;

public class DiscardDevCards extends Tokens {
    private String ID;

    /**
     * every discardDevCards token has a color, that is the color of the cards that have to be deleted
     */
    private ColorCard color;

    public DiscardDevCards(ColorCard color){
        this.color = color;
        this.ID = "T3";
    }

    /**
     * this method active one particular token, that one where Lorenzo Discard 2 Developement card.
     */
    @Override
    public void effectTokens(LorenzoIlMagnifico l) {
        int i=2;
        for(int j=0; j<2; j++) {
            while (i > 0 && l.getDevelopmentDeck().getDevCards(i, l.getDevelopmentDeck().getColumnFromColor(color)) == null) {
                i--;
            }
            if (i > 0) l.getDevelopmentDeck().removeDevCards(i, l.getDevelopmentDeck().getColumnFromColor(color));
        }

        List<Tokens> tokensList= l.getTokensvet();
        Tokens temp= tokensList.remove(0);
        tokensList.add(6,temp);

    }

    @Override
    public String getID() {
        return ID;
    }

    public String getColor() {
        return color.toString();
    }
}