package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.player.WarehouseDepots;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class CliTest {
    ClientController controller;
    PlayerBoard playerBoard;

    @Test
    public void showExtrachest(){
        controller = new ClientController();
        playerBoard = new PlayerBoard();

        Cli cli = new Cli(playerBoard, controller);
        playerBoard.setNickname("Ludovico");
        playerBoard.setCurrentPlayer("Ludovico");

        String[][] warehouse = new String[3][3];
        Map<String, Integer> extrachest = new HashMap<>();

        //niente
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Una Coins
        extrachest.put("Coins", 1);
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Due Coins
        extrachest.clear();
        extrachest.put("Coins", 2);
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Due servants
        extrachest.clear();
        extrachest.put("Servants", 2);
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Un servants e una coin
        extrachest.clear();
        extrachest.put("Coins", 1);
        extrachest.put("Servants", 1);
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showExtraChest();

        //coins vuota ma due servants
        extrachest.clear();
        extrachest.put("Coins", 0);
        extrachest.put("Servants", 2);
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showExtraChest();

    }

    @Test
    public void showWarehouse(){
        controller = new ClientController();
        playerBoard = new PlayerBoard();

        Cli cli = new Cli(playerBoard, controller);
        playerBoard.setNickname("Ludovico");
        playerBoard.setCurrentPlayer("Ludovico");

        String[][] warehouse = new String[3][3];
        Map<String, Integer> extrachest = new HashMap<>();

        //vuoto
        cli.showWarehouse();

        //una biglia in 0
        warehouse[0][0] = "Coins";
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showWarehouse();

        //una biglia in 1
        warehouse[1][0] = "Coins";
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showWarehouse();

        //una biglia in 2
        warehouse[2][0] = "Coins";
        playerBoard.setWarehouse(warehouse, extrachest);
        cli.showWarehouse();

    }

    @Test
    public void showDevCardDeck() throws IOException {
        controller = new ClientController();
        playerBoard = new PlayerBoard();

        Cli cli = new Cli(playerBoard, controller);
        playerBoard.setNickname("Ludovico");
        playerBoard.setCurrentPlayer("Ludovico");

        DevCardsDeck devCardsDeck = new DevCardsDeck();



    }


}