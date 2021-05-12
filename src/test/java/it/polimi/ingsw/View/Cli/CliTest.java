package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Structure.ViewLeaderActionBox;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    @Test
    void showMessage() {
    }

    @Test
    void showLeaderActionBox() {
       PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        cli.showLeaderActionBox();

        ArrayList<String> card = new ArrayList<>();

        card.add("QWERT");
        card.add("ASDFG");

        playerBoard.setLeaderCard(card);

        cli.showLeaderActionBox();
    }

    @Test
    void showSlotDevCard() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        //mostra vuota
        cli.showSlotDevCard();

        //riempi
        String[][] slot = new String[3][3];
        slot[0][0] = "QWERTY";
        slot[1][0] = "ASDFGH";
        slot[1][2] = "ZXCVBN";

        playerBoard.setSlotDevCard(slot);
        //mostra piena
        cli.showSlotDevCard();
    }

    @Test
    void showWarehouse() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);
        //mostra vuota
        cli.showWarehouse();

        String[][] warehouse = new String[3][3];
        //riempi
        warehouse[0][0] = "Servants";
        warehouse[1][0] = "Coins";
        warehouse[2][1] = "Stones";

        playerBoard.setWarehouse(warehouse, null);
        //mostra piena
        cli.showWarehouse();

    }

    @Test
    void showStrongbox() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        cli.showStrongbox();

        Map<String, Integer> strongbox = new HashMap<>();

        strongbox.put("Shields", 4);
        strongbox.put("Coins", 21);

        playerBoard.setStrongbox(strongbox);

        cli.showStrongbox();
    }

    @Test
    void showFaithTrack() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        cli.showFaithTrack();

        playerBoard.setFaithMarker(20);

        cli.showFaithTrack();

    }

    @Test
    void showMarketTray() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        cli.showMarketTray();

        String[][] markettray = new String[3][4];
        markettray[0][0] = "Servants";
        markettray[0][1] = "Coins";

        String remaining = "FaithMarker";

        playerBoard.setMarketTray(markettray, remaining);

        cli.showMarketTray();
    }

    @Test
    void showDevCardDeck() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        cli.showDevCardDeck();

        String[][][] deck = new String[3][4][4];
        deck[0][0][0] = "QWERTY";
        deck[1][0][0] = "ASDFGH";

        playerBoard.setDevCardDeck(deck);

        cli.showDevCardDeck();
    }

    @Test
    void showExtraChest() {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard);

        cli.showExtraChest();

        Map<String, Integer> chest = new HashMap<>();

        chest.put("Coins",2);

        playerBoard.setWarehouse(null, chest);

        cli.showExtraChest();

    }
}