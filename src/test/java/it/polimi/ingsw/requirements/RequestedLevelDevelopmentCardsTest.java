package it.polimi.ingsw.requirements;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RequestedLevelDevelopmentCardsTest {

    @Test
    public void EmptySlotDevCard(){
        Player player= new Player("Ok");
        RequestedLevelDevelopmentCards reqTest= new RequestedLevelDevelopmentCards(ColorCard.BLUE,1);

        assertFalse(reqTest.checkResources(player));
    }

    @Test
    public void ReturnTrueSimple() throws IOException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Ok");

        RequestedLevelDevelopmentCards reqTest= new RequestedLevelDevelopmentCards(ColorCard.BLUE,1);
        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.BLUE)));

        assertTrue(reqTest.checkResources(player));
    }

    @Test
    public void ReturnTrueLevel2() throws IOException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Ok");

        RequestedLevelDevelopmentCards reqTest= new RequestedLevelDevelopmentCards(ColorCard.BLUE,2);
        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.BLUE)));
        assertTrue(deck.purchaseCards(player,0,1, deck.getColumnFromColor(ColorCard.BLUE)));

        assertTrue(reqTest.checkResources(player));
    }

    @Test
    public void ReturnFalseBecauseNoRequiredCard() throws IOException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Ok");

        RequestedLevelDevelopmentCards reqTest= new RequestedLevelDevelopmentCards(ColorCard.BLUE,1);
        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.YELLOW)));

        assertFalse(reqTest.checkResources(player));
    }

    @Test
    public void ReturnFalseBecauseNoRequiredLevel() throws IOException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Ok");

        RequestedLevelDevelopmentCards reqTest= new RequestedLevelDevelopmentCards(ColorCard.BLUE,2);
        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.BLUE)));

        assertFalse(reqTest.checkResources(player));
    }


}