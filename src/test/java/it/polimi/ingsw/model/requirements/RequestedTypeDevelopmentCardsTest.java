package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.player.Player;
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
    public void ReturnTrue() throws IOException, GameFinishedException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Perfetto");

        RequestedTypeDevelopmentCards reqTest= new RequestedTypeDevelopmentCards();
        reqTest.addRequirements(ColorCard.YELLOW,2);
        reqTest.addRequirements(ColorCard.GREEN,1);

        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,0,1,deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,1,2, deck.getColumnFromColor(ColorCard.GREEN)));
        assertTrue(deck.purchaseCards(player,2,2, deck.getColumnFromColor(ColorCard.BLUE)));


        assertTrue(reqTest.checkResources(player));
    }

    @Test
    public  void ReturnFalse () throws IOException, GameFinishedException {
        DevCardsDeck deck= new DevCardsDeck();
        Player player= new Player("Perfetto");

        RequestedTypeDevelopmentCards reqTest= new RequestedTypeDevelopmentCards();
        reqTest.addRequirements(ColorCard.YELLOW,2);
        reqTest.addRequirements(ColorCard.GREEN,2);

        assertTrue(deck.purchaseCards(player,0,2, deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,0,1,deck.getColumnFromColor(ColorCard.YELLOW)));
        assertTrue(deck.purchaseCards(player,1,2, deck.getColumnFromColor(ColorCard.GREEN)));
        assertTrue(deck.purchaseCards(player,2,2, deck.getColumnFromColor(ColorCard.BLUE)));


        assertFalse(reqTest.checkResources(player));
    }

}