package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.model.game.DevCardsDeck;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class CliTest {

    @Test
    public void showExtrachest(){
        Cli cli = new Cli();

        String[][] warehouse = new String[3][3];
        Map<String, Integer> extrachest = new HashMap<>();
        cli.getPlayerBoard().setNickname("pino");
        cli.getPlayerBoard().setCurrentPlayer(cli.getPlayerBoard().getNickname());

        //niente
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Una Coins
        extrachest.put("Coins", 1);
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Due Coins
        extrachest.clear();
        extrachest.put("Coins", 2);
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Due servants
        extrachest.clear();
        extrachest.put("Servants", 2);
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showExtraChest();
        //Un servants e una coin
        extrachest.clear();
        extrachest.put("Coins", 1);
        extrachest.put("Servants", 1);
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showExtraChest();

        //coins vuota ma due servants
        extrachest.clear();
        extrachest.put("Coins", 0);
        extrachest.put("Servants", 2);
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showExtraChest();

    }

    @Test
    public void showWarehouse(){
        Cli cli = new Cli();
        cli.getPlayerBoard().setNickname("Ludovico");
        cli.getPlayerBoard().setCurrentPlayer("Ludovico");

        String[][] warehouse = new String[3][3];
        Map<String, Integer> extrachest = new HashMap<>();

        //vuoto
        cli.showWarehouse();

        //una biglia in 0
        warehouse[0][0] = "Coins";
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showWarehouse();

        //una biglia in 1
        warehouse[1][0] = "Coins";
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showWarehouse();

        //una biglia in 2
        warehouse[2][0] = "Coins";
        cli.getPlayerBoard().setWarehouse(warehouse, extrachest);
        cli.showWarehouse();

    }

    @Test
    public void showDevCardDeck() throws IOException {

        Cli cli = new Cli();
        cli.getPlayerBoard().setNickname("Ludovico");
        cli.getPlayerBoard().setCurrentPlayer("Ludovico");

        DevCardsDeck devCardsDeck = new DevCardsDeck();



    }

}