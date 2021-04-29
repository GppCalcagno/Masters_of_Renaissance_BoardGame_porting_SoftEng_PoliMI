package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Servants;
import it.polimi.ingsw.model.producible.Shields;
import it.polimi.ingsw.model.producible.Stones;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {

    @Test
    void selectDevCard() throws IOException, NegativeQuantityExceptions {
       TurnController t = new TurnController();

        Player p = new Player("Casseruola");
        t.getGame().addPlayersList(p);
        t.getGame().setCurrentPlayer();

        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Coins(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Servants(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Shields(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Stones(), 100);

        assertTrue(t.selectDevCard(2,0));
        assertTrue(t.selectDevCard(2,1));
        assertTrue(t.selectDevCard(2,2));
    }

    @Test
    void insertCard() throws IOException, NegativeQuantityExceptions {
        TurnController t = new TurnController();

        Player p = new Player("Casseruola");
        t.getGame().addPlayersList(p);
        t.getGame().setCurrentPlayer();

        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Coins(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Servants(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Shields(), 100);
        t.getGame().getCurrentPlayer().getStrongbox().updateResources(new Stones(), 100);

        t.selectDevCard(2,0);
        assertTrue(t.insertCard(0));
        t.selectDevCard(2,1);
        assertTrue(t.insertCard(1));
        t.selectDevCard(2,2);
        assertTrue(t.insertCard(2));
        t.selectDevCard(1,0);
        assertTrue(t.insertCard(2));
    }
}