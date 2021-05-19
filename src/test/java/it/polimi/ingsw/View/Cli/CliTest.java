package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.model.player.WarehouseDepots;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class CliTest {
    ClientController controller;
    /*

    @Test
    void showLeaderActionBox() throws IOException {

       PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        cli.showLeaderActionBox();

        ArrayList<String> card = new ArrayList<>();

        card.add("QWERT");
        card.add("ASDFG");

        playerBoard.setLeaderCard(card);

        cli.showLeaderActionBox();
    }

    @Test
    void showSlotDevCard() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

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
    void showWarehouse() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);
        //mostra vuota
        cli.showWarehouse();

        String[][] warehouse = new String[3][3];
        //riempi
        warehouse[0][0] = "Servants";
        warehouse[1][0] = "Coins";
        warehouse[2][1] = "Stones";

        playerBoard.setCurrentPlayer("Paolo");

        playerBoard.setWarehouse(warehouse, null);
        //mostra piena
        cli.showWarehouse();

    }

    @Test
    void showStrongbox() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        cli.showStrongbox();

        Map<String, Integer> strongbox = new HashMap<>();

        strongbox.put("Shields", 4);
        strongbox.put("Coins", 21);

        playerBoard.setCurrentPlayer("Paolo");

        playerBoard.setStrongbox(strongbox);

        cli.showStrongbox();
    }

    @Test
    void showFaithTrack() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        cli.showFaithTrack();

        //playerBoard.setFaithMarker(20);

        cli.showFaithTrack();

    }

    @Test
    void showMarketTray() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        cli.showMarketTray();

        String[][] markettray = new String[3][4];
        markettray[0][0] = "Servants";
        markettray[0][1] = "Coins";

        String remaining = "FaithMarker";

        playerBoard.setMarketTray(markettray, remaining);

        cli.showMarketTray();
    }

    @Test
    void showDevCardDeck() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        cli.showDevCardDeck();

        String[][][] deck = new String[3][4][4];
        deck[0][0][0] = "QWERTY";
        deck[1][0][0] = "ASDFGH";

        playerBoard.setDevCardDeck(deck);

        cli.showDevCardDeck();
    }

    @Test
    void showExtraChest() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        cli.showExtraChest();

        Map<String, Integer> chest = new HashMap<>();

        chest.put("Coins",2);

        playerBoard.setCurrentPlayer("paolo");

        playerBoard.setWarehouse(null, chest);

        cli.showExtraChest();

    }

    @Test
    void askNickname() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);

        //cli.askNickname();

        System.out.println(playerBoard.getNickname());

    }
    @Test
    void viewSTart() throws IOException {
        PlayerBoard playerBoard = new PlayerBoard();
        Cli cli = new Cli(playerBoard, controller);
        cli.gameStart();
    }

    */
}