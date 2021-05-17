package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.message.*;
import it.polimi.ingsw.View.Cli.Structure.*;
import it.polimi.ingsw.View.ViewInterface;

import java.io.PrintStream;
import java.util.*;

public class Cli implements ViewInterface {
    PlayerBoard playerBoard;
    private final PrintStream out;
    private Scanner input;
    private ClientController controller;


    public Cli(PlayerBoard playerBoard, ClientController controller) {
        this.playerBoard = playerBoard;
        this.controller = controller;
        out = System.out;
        input = new Scanner(System.in);
    }

    @Override
    public void askServerInfo() {
        out.println("\n");
        out.println("Enter server IP [default: 127.0.0.1]: ");
        String serverAddress = input.nextLine();

        out.println("\n");
        out.println("Enter server port [default : 1234]: ");
        int serverPort = input.nextInt();

        controller.sendMessage(new MessageConnect(serverAddress, serverPort));
    }

    @Override
    public void endturn() {

    }

    @Override
    public void askNickname() {
        out.println("\n");
        out.println("Enter your nickname: ");
        String nickname = input.nextLine();
        controller.sendMessage(new MessageLogin(nickname));
    }

    @Override
    public void askNumPlayer() {
        out.println("\n");
        out.println("Enter the number of players: ");
        int numPlayers = input.nextInt();
        controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), numPlayers));
    }

    @Override
    public void askChooseLeaderCards() {
        showLeaderActionBox();
        out.println("\n");
        out.println("Enter the first Leader card [0-3]: ");
        int i1 = input.nextInt();

        out.println("Enter the second Leader card [0-3]: ");
        int i2 = input.nextInt();

        controller.sendMessage(new MessageChooseLeaderCards(playerBoard.getNickname(), i1, i2));
    }

    @Override
    public void askChooseResourcesFirstTurn(int num) {
        if (num == 1) {
            out.println("Enter a resource: ");
            String resource = input.nextLine();
            List<String> resourceList = new ArrayList<>();
            resourceList.add(resource);
            controller.sendMessage(new MessageChooseResourcesFirstTurn(playerBoard.getNickname(), resourceList));
        }
        else {
            if (num == 2) {
                List<String> resourceList = new ArrayList<>();
                out.println("Enter the first resource: ");
                String resource1 = input.nextLine();
                resourceList.add(resource1);


                out.println("Enter the second resource: ");
                String resource2 = input.nextLine();
                resourceList.add(resource2);

                controller.sendMessage(new MessageChooseResourcesFirstTurn(playerBoard.getNickname(), resourceList));
            }
        }
    }

    @Override
    public void askChooseTurn() {
        out.println("Actions: \n0: Extract marbles from Market tray\n1: Buy a Development card\n2: Choose resources to activate a base production\n3: Activate production of a Development card\n4: Active production of the Leader card\n5: Activate or discard a Leader card");
        out.println("Enter the relative number: ");
        int action = input.nextInt();

        //controller.sendMessage(new MessageChooseTurn(playerBoard.getNickname(), action));
    }

    @Override
    public void askExtractMarble() {
        showMarketTray();
        out.println("\n");

        out.println("Do you want to extract a column or a row? [c/r]: ");
        String colrows = input.nextLine();
        char colrow = colrows.charAt(0);
        int num = 0;

        //se scrive 'r'
        if (colrow == 'r') {
            out.println("Which number of row do you want to extract? [0-2]: ");
            num = input.nextInt();
        }

        //se scrive 'c'
        if (colrow == 'r') {
            out.println("Which number of column do you want to extract? [0-3]: ");
            num = input.nextInt();
        }

        controller.sendMessage(new MessageExtractionMarbles(playerBoard.getNickname(), colrow, num));
    }

    @Override
    public void askAfterTakeMarble() {
        showMarbleBuffer();
        out.println("\n");
        showWarehouse();
        out.println("\n");

        out.println("Actions:\n0: Exchange two rows of the Warehouse depots\n1: Add or discard an extracted marble (if you discard a marble, you'll give a faith point to each other player)\n2: Choose with which resource you'll convert an extracted white marble (if yuo have the special effect of the relative Leader card)");
        out.println("Enter the relative action's number: ");
        int choice = input.nextInt();

        //controller.sendMessage(new MessageChooseAfterTakeMarble(playerBoard.getNickname(), choice));
    }

    @Override
    public void askExchange() {
        showWarehouse();
        out.println("\n");

        out.println("Enter the first row to exchange: ");
        int row1 = input.nextInt();

        out.println("Enter the second row to exchange: ");
        int row2 = input.nextInt();

        controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), row1, row2));
    }

    @Override
    public void askAddDiscardMarble() {
        showMarbleBuffer();
        out.println("\n");
        showWarehouse();
        out.println("\n");

        out.println("0: Discard this marble\n1: Add this marble to the Warehouse depots");
        out.println("Enter your choice: ");
        int choiceb = input.nextInt();

        if (choiceb == 0) {
            //controller.sendMessage(new MessageAddDiscardMarble(playerBoard.getNickname(), false, -1));
        }
        else if (choiceb == 1){
            out.println("Enter Warehouse depots' index in which you want to insert the resource [0-2]: ");
            int index = input.nextInt();
           //  controller.sendMessage(new MessageAddDiscardMarble(playerBoard.getNickname(), true, index));
        }
    }

    @Override
    public void askSelectTrasformationWhiteMarble() {
        showWhiteMarbleEffectList();
        out.println("\n");

        out.println("Enter the resource with which do you want to convert the White marble [0-1]: ");
        int choice = input.nextInt();

        //controller.sendMessage(new MessageSelectTransformationWhiteMarble(playerBoard.getNickname(), choice));
    }

    @Override
    public void askSelectDevCard() {
        boolean wantToWatch = true;

        showDevCardDeck();
        out.println("\n");

        while (wantToWatch) {
            out.println("Do you want to see a Development card? [0-1]: ");
            int choice = input.nextInt();
            if (choice == 0)
                wantToWatch = false;
            else if (choice == 1){
                out.println("Enter the Development card's ID: ");
                String card = input.nextLine();
                showDevCard(card);
            }
        }

        out.println("Enter the Development card's ID to buy: ");
        String devCard = input.nextLine();

        //controller.sendMessage(new MessageSelectDevCard(playerBoard.getNickname(), devCard));
    }

    @Override
    public void askChooseResourcesPurchaseDevCard() {
        showWarehouse();
        out.println("\n");

        Map<String,Integer> WarehouseRes = new HashMap<>();
        out.println("Enter the type of resources from the Warehouse depots [Coins, Servants, Shields, Stones]: ");
        String w = input.nextLine();

        out.println("Enter the number of resources: ");
        int numW = input.nextInt();

        WarehouseRes.put(w, numW);

        showStrongbox();
        out.println("\n");

        Map<String,Integer> StrongboxRes = new HashMap<>();
        out.println("Enter the type of resources from the Strongbox [Coins, Servants, Shields, Stones]: ");
        String s = input.nextLine();

        out.println("Enter the number of resources: ");
        int numS = input.nextInt();

        StrongboxRes.put(s, numS);

        Map<String,Integer> ExtrachestMap = new HashMap<>();
        if (!playerBoard.getExtrachest().isEmpty()) {
            showExtraChest();
            out.println("\n");

            out.println("Enter the type of resources from the Extra Chest [Coins, Servants, Shields, Stones]: ");
            String e = input.nextLine();

            out.println("Enter the number of resources: ");
            int numE = input.nextInt();

            ExtrachestMap.put(e, numE);
        }

        //controller.sendMessage(new MessageChooseResourcesPurchaseDevCard(playerBoard.getNickname(), WarehouseRes, StrongboxRes, ExtrachestMap));
    }

    @Override
    public void askInsertCard() {
        boolean wantToWatch = true;

        showSlotDevCard();
        out.println("\n");


        while (wantToWatch) {
            out.println("Do you want to see a Development card? [0-1]: ");
            int choice = input.nextInt();
            if (choice == 0)
                wantToWatch = false;
            else if (choice == 1){
                out.println("Enter the Development card's ID: ");
                String card = input.nextLine();
                showDevCard(card);
            }
        }

        out.println("Enter the slot in which do you want to insert the card [0-2]: ");
        int choice = input.nextInt();

        //controller.sendMessage(new MessageInsertCard(playerBoard.getNickname(), choice));
    }

    @Override
    public void askProductionType() {
        boolean wantToWatch = true;

        /*
        while (wantToWatch) {
            out.println("Do you want to see the Warehouse depots? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                showWarehouse();
                wantToWatch = false;
            }
        }

        wantToWatch = true;

        while (wantToWatch) {
            out.println("Do you want to see the Strongbox? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                showStrongbox();
                wantToWatch = false;
            }
        }

        if (!playerBoard.getExtrachest().isEmpty()) {
            wantToWatch = true;

            while (wantToWatch) {
                out.println("Do you want to see the Extra chest? [0-1]: ");
                if (!input)
                    wantToWatch = false;
                else {
                    showExtraChest();
                    wantToWatch = false;
                }
            }
        }

        showSlotDevCard();

        wantToWatch = true;

        while (wantToWatch) {
            out.println("Do you want to see a Development card? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                out.println("Enter the Development card's ID: ");
                showDevCard(input);
            }
        }

         */

        out.println("Productions:\n0: Activate base production\n1: Activate the production power of a Development card\n2: Activate the production power of the relative Leader card\n3: End production");
    }

    @Override
    public void askActiveBaseProduction() {
        showWarehouse();
        out.println("Enter the type of resources from the Warehouse depots [Coins, Servants, Shields, Stones]: ");

        out.println("Enter the number of resources: ");

        showStrongbox();
        out.println("Enter the type of resources from the Strongbox [Coins, Servants, Shields, Stones]: ");

        out.println("Enter the number of resources: ");

        if (!playerBoard.getExtrachest().isEmpty()) {
            showExtraChest();
            out.println("Enter the type of resources from the Extra Chest [Coins, Servants, Shields, Stones]: ");

            out.println("Enter the number of resources: ");
        }
    }

    @Override
    public void askChosenResourceBaseProduction() {
        out.println("Enter the type of the chosen resource [Coins, Servants, Shields, Stones]: ");
    }

    @Override
    public void askActiveProductionDevCard() {
        boolean wantToWatch = true;

        showSlotDevCard();

        /*
        while (wantToWatch) {
            out.println("Do you want to see a Development card? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                out.println("Enter the Development card's ID: ");
                showDevCard(input);
            }
        }

         */

        out.println("Enter the number of the Slot Development cards' column [0-2]: ");
    }

    @Override
    public void askChooseResourcesDevCardProduction() {
        showWarehouse();
        out.println("Enter the type of resources from the Warehouse depots [Coins, Servants, Shields, Stones]: ");

        out.println("Enter the number of resources: ");

        showStrongbox();
        out.println("Enter the type of resources from the Strongbox [Coins, Servants, Shields, Stones]: ");

        out.println("Enter the number of resources: ");

        if (!playerBoard.getExtrachest().isEmpty()) {
            showExtraChest();
            out.println("Enter the type of resources from the Extra Chest [Coins, Servants, Shields, Stones]: ");

            out.println("Enter the number of resources: ");
        }
    }

    @Override
    public void askActiveLeaderCardProduction() {
        boolean wantToWatch = true;

        showLeaderActionBox();

        /*
        while (wantToWatch) {
            out.println("Do you want to see a Leader card? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                out.println("Enter the Leader card's ID: ");
                showLeaderAction(input);
            }
        }

         */

        out.println("Enter the index of the Leader card [0-1]: ");

        showWarehouse();
        showStrongbox();
        if (!playerBoard.getExtrachest().isEmpty())
            showExtraChest();

        out.println("Depots:\n0: Warehouse depots\n1: Strongbox\n2: ExtraChest(if you have)");
        out.println("Enter the relative number from where you want to take the resource: ");

        out.println("Enter the type of the given resource [Coins, Servants, Shields, Stones]: ");
    }

    @Override
    public void sendEndProduction() {

    }

    @Override
    public void askEndTurnActiveLeaderCard() {
        boolean wantToWatch = true;

        showLeaderActionBox();

        /*
        while (wantToWatch) {
            out.println("Do you want to see a Leader card? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                out.println("Enter the Leader card's ID: ");
                showLeaderAction(input);
            }
        }

         */

        out.println("Do you want to activate or discard a Leader card or do you want to end your turn? [0-1]: ");
    }

    @Override
    public void askUpdateStateLeaderAction() {
        boolean wantToWatch = true;

        showLeaderActionBox();

        /*
        while (wantToWatch) {
            out.println("Do you want to see a Leader card? [0-1]: ");
            if (!input)
                wantToWatch = false;
            else {
                out.println("Enter the Leader card's ID: ");
                showLeaderAction(input);
            }
        }

         */

        out.println("Which card do you want to activate or discard [0-1]: ");

        out.println("Do you want to activate or discard this card? [0-1]: ");
    }

    //show
    @Override
    public void showMessage(String message) {
        out.println("\n");
        out.println(message);
    }

    @Override
    public void showLeaderActionBox() {
        out.println("\n");
        ViewLeaderActionBox viewLeaderActionBox = new ViewLeaderActionBox(playerBoard);
        viewLeaderActionBox.plot();
    }

    @Override
    public void showSlotDevCard() {
        out.println("\n");
        ViewSlotDevCard viewSlotDevCard = new ViewSlotDevCard(playerBoard);
        viewSlotDevCard.plot();
    }

    @Override
    public void showWarehouse() {
        out.println("\n");
        ViewWarehouse viewWarehouse = new ViewWarehouse(playerBoard);
        viewWarehouse.plot();
    }

    @Override
    public void showStrongbox() {
        out.println("\n");
        ViewStrongbox viewStrongbox = new ViewStrongbox(playerBoard);
        viewStrongbox.plot();
    }

    @Override
    public void showWhiteMarbleEffectList() {

    }

    @Override
    public void showFaithTrack() {
        out.println("\n");
        ViewFaithTrack viewFaithTrack = new ViewFaithTrack(playerBoard);
        viewFaithTrack.plot();
    }

    @Override
    public void showMarketTray() {
        out.println("\n");
        ViewMarketTray viewMarketTray = new ViewMarketTray(playerBoard);
        viewMarketTray.plot();
    }

    @Override
    public void showMarbleBuffer() {

    }

    @Override
    public void showDevCardDeck() {
        out.println("\n");
        ViewDevCardDeck viewDevCardDeck = new ViewDevCardDeck(playerBoard);
        viewDevCardDeck.plot();
    }

    @Override
    public void showExtraChest(){
        out.println("\n");
        ViewExtraChest viewExtraChest = new ViewExtraChest(playerBoard);
        viewExtraChest.plot();
    }

    @Override
    public void showDevCard(String ID) {

    }

    @Override
    public void showLeaderAction(String ID) {

    }

    @Override
    public void showPlayerState(String name) {

    }

    @Override
    public void showWinnerandVictoryPoint() {

    }

    @Override
    public void showLorenzoTrun() {

    }
}
