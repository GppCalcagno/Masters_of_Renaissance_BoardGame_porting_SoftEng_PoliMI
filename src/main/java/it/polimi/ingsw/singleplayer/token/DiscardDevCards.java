package it.polimi.ingsw.singleplayer.token;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.singleplayer.LorenzoIlMagnifico;

public class DiscardDevCards extends Tokens {

    /**
     * every discardDevCards token has a color, that is the color of the cards that have to be deleted
     */
    private ColorCard color;

    public DiscardDevCards(ColorCard color){
        this.color = color;
    }

    /**
     * this method active one particular token, that one where Lorenzo Discard 2 Developement card
     */
    @Override
    public void effectTokens(LorenzoIlMagnifico l){
        int i=l.getDevelopmentDeck().getDevelopmentCardDeck().length;
        for(int j=0; j<2; j++) {
            while (l.getDevelopmentDeck().getDevCards(i, l.getDevelopmentDeck().getColumnFromColor(color)) == null) {
                i--;
            }
            l.getDevelopmentDeck().removeDevCards(i, l.getDevelopmentDeck().getColumnFromColor(color));
        }
        System.out.println("Discard dev cards" + color);
    }

}