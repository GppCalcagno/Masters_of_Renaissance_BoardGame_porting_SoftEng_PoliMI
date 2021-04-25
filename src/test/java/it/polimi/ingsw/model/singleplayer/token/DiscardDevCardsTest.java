package it.polimi.ingsw.model.singleplayer.token;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DiscardDevCardsTest {

    @Test
    void effectTokens() throws IOException, NoSuchElementException {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        Tokens t = new DiscardDevCards(ColorCard.BLUE);
        Tokens t1 = new DiscardDevCards(ColorCard.YELLOW);
        Tokens t2 = new DiscardDevCards(ColorCard.PURPLE);
        Tokens t3 = new DiscardDevCards(ColorCard.GREEN);


        try {
            t.effectTokens(l);
        } catch (ActiveVaticanReportException e) {
            e.printStackTrace();
        }

        assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][d.getColumnFromColor(ColorCard.BLUE)][3]);
        assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][d.getColumnFromColor(ColorCard.BLUE)][2]);
        assertNotEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][d.getColumnFromColor(ColorCard.BLUE)][1]);


    }
}