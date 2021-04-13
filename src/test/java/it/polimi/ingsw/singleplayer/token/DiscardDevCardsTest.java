package it.polimi.ingsw.singleplayer.token;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.singleplayer.LorenzoIlMagnifico;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DiscardDevCardsTest {

    @Test
    void effectTokens() throws IOException, NegativeQuantityExceptions, NoSuchElementException {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        Tokens t = new DiscardDevCards(ColorCard.BLUE);
        Tokens t1 = new DiscardDevCards(ColorCard.YELLOW);
        Tokens t2 = new DiscardDevCards(ColorCard.PURPLE);
        Tokens t3 = new DiscardDevCards(ColorCard.GREEN);



        t.effectTokens(l);

        assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][d.getColumnFromColor(ColorCard.BLUE)][3]);
        assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][d.getColumnFromColor(ColorCard.BLUE)][2]);
        assertNotEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][d.getColumnFromColor(ColorCard.BLUE)][1]);


    }
}