package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionParserTest {
    ClientController clientController= new ClientController();
    PlayerBoard playerBoard= new PlayerBoard();

    ActionParser actionParser= new ActionParser(new Cli(playerBoard, clientController), clientController,playerBoard);

    @Test
    public void ResourcesFormat(){
        assertEquals("Coins",actionParser.convertformat("coins"));

        assertEquals("Coins",actionParser.convertformat("COINS"));
        assertEquals("Coins",actionParser.convertformat("cOInS"));

    }

}