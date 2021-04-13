package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.card.ColorCard;

import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.singleplayer.token.DiscardDevCards;
import it.polimi.ingsw.singleplayer.token.MoveOneAndMix;
import it.polimi.ingsw.singleplayer.token.MoveTwo;
import it.polimi.ingsw.singleplayer.token.Tokens;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoIlMagnificoTest {

    @Test
    void drawTokens() throws IOException {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        Tokens t1 = new MoveOneAndMix();
        Tokens t2 = new MoveTwo();
        Tokens t3 = new DiscardDevCards(ColorCard.BLUE);
        Tokens t4 = new DiscardDevCards(ColorCard.GREEN);
        Tokens t5 = new DiscardDevCards(ColorCard.PURPLE);
        Tokens t6 = new DiscardDevCards(ColorCard.YELLOW);

        if(l.getTokensvet()[0].getClass().equals(t1)) assertEquals(1, l.getFaithMarker());
        else if(l.getTokensvet()[0].getClass().equals(t2)) assertEquals(2, l.getFaithMarker());
        else if(l.getTokensvet()[0].getClass().equals(t3)) assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][l.getDevelopmentDeck().getColumnFromColor(ColorCard.BLUE)][2]);
        else if(l.getTokensvet()[0].getClass().equals(t4)) assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][l.getDevelopmentDeck().getColumnFromColor(ColorCard.GREEN)][2]);
        else if(l.getTokensvet()[0].getClass().equals(t5)) assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][l.getDevelopmentDeck().getColumnFromColor(ColorCard.PURPLE)][2]);
        else if(l.getTokensvet()[0].getClass().equals(t6)) assertEquals(null, l.getDevelopmentDeck().getDevelopmentCardDeck()[2][l.getDevelopmentDeck().getColumnFromColor(ColorCard.YELLOW)][2]);

    }

    @Test
    void increaseFaithMarker() throws IOException, NegativeQuantityExceptions {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        l.increaseFaithMarker(3);

        assertEquals(3, l.getFaithMarker());

        try {
            l.increaseFaithMarker(-5);
        } catch (NegativeQuantityExceptions negativeQuantityExceptions) {
            assertTrue(true);
        }
    }
}