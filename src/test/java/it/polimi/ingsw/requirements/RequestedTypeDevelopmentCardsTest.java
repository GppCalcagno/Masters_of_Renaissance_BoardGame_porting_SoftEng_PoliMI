package it.polimi.ingsw.requirements;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestedTypeDevelopmentCardsTest {

    @Test
    public void EmptyRequirements(){
        Player player= new Player("Ok");
        RequestedTypeDevelopmentCards reqTest= new RequestedTypeDevelopmentCards();

        assertTrue(reqTest.checkResources(player));
    }

    @Test
    public void EmptySlotDevCard(){
        Player player= new Player("Ok");
        RequestedTypeDevelopmentCards reqTest= new RequestedTypeDevelopmentCards();
        reqTest.addRequirements(ColorCard.YELLOW,2);

        assertFalse(reqTest.checkResources(player));
    }


    @Test
    public void ReturnTrue() throws IOException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Perfetto");

        RequestedTypeDevelopmentCards reqTest= new RequestedTypeDevelopmentCards();
        reqTest.addRequirements(ColorCard.YELLOW,2);

        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,0,1,deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,1,2, deck.getColumnFromColor(ColorCard.GREEN)));


        assertTrue(reqTest.checkResources(player));
    }

    @Test
    public  void ReturnFalse () throws IOException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Perfetto");

        RequestedTypeDevelopmentCards reqTest= new RequestedTypeDevelopmentCards();
        reqTest.addRequirements(ColorCard.YELLOW,2);
        reqTest.addRequirements(ColorCard.GREEN,2);

        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,0,1,deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,1,2, deck.getColumnFromColor(ColorCard.GREEN)));

        assertFalse(reqTest.checkResources(player));
    }

}