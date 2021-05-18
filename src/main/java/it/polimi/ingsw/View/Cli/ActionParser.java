package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import it.polimi.ingsw.Network.Message.UpdateMesssage.MessageUpdateStateLeaderAction;

import java.util.Locale;

public class ActionParser {
    /**
     * this class take the input from the client and parse them, in order to send the right message to the server
     */


    private String[] parts;
    private ClientController controller;
    private PlayerBoard playerBoard;
    private Cli cli;
    private String line;

    /**
     * this is the constructor, it is called from the Cli
     * @param cli the cli of the player
     * @param controller the client controller
     * @param playerBoard the player's board
     * @param line the input that player just inserted
     */
    public ActionParser(Cli cli, ClientController controller, PlayerBoard playerBoard, String line){
        this.cli = cli;
        this.playerBoard = playerBoard;
        this.controller = controller;
        this.line = line;
        askChooseTurn();
    }

    /**
     * this method parse the input from the player and launch the right method for the turn that player choose to do
     */
    public void askChooseTurn() {
        parts = line.toUpperCase(Locale.ROOT).split(" ");
        if (parts[0] != "") {
            try {
                switch (parts[0]) {
                    case "EXTRACTIONMARBLE":
                        extractMarble();
                        break;
                    case "MANAGEMARBLE":
                        manageMarble();
                        break;
                    case "EXCHANGEWAREHOUSE":
                        exchangeWarehouse();
                        break;
                    case "BUYDEVCARD":
                        buyDevCard();
                        break;
                    case "PAYRESOURCES":
                        payResources();
                        break;
                    case "ACTIVEBASEPRODUCTION":
                        activeBaseProduction();
                        break;
                    case "ACTIVELEADERACTIONPROD":
                        activeLeaderActionProd();
                        break;
                    case "ACTIVEDEVCARDPRODUCTION":
                        activeDevCardProduction();
                        break;
                    case "UPDATELEADERCARD":
                        updateLeaderCard();
                        break;
                    case "ENDTURN":
                        endTurn();
                        break;
                    case "ENDPRODUCTION":
                        endProduction();
                        break;
                    case "SHOW":
                        show();
                        break;
                    case "HELP":
                        help();
                        break;
                    default:

                }
            } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("The command need more parameters, please write it correctly");
            } catch (NumberFormatException n){
                System.out.println("The command need at least a number, please insert it correctly");
            }
        }
    }


    public void extractMarble() {
        if(parts[0]=="EXTRACTIONMARBLE") {
            controller.sendMessage(new MessageExtractionMarbles(playerBoard.getNickname(), parts[1].charAt(0), Integer.parseInt(parts[2])));
        }
    }

    public void exchangeWarehouse() {
        if(parts[0]=="EXCHANGEWAREHOUSE")
            controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
    }

    public void manageMarble(){
        String resources="";
        int row=-1;
        char structure = 0;
        if(parts[0]=="MANAGEMARBLE"){
            if(parts[1]!=null) structure = parts[1].charAt(0);
            if(structure == 'W') row = Integer.parseInt(parts[2]);

            if(parts[3]!=null) resources = parts[3];

            controller.sendMessage(new MessageManageMarbles(playerBoard.getNickname(), structure, row, resources));
        }
    }

    public void buyDevCard(){
        if(parts[0]=="BUYDEVCARD"){
            controller.sendMessage(new MessageBuyDevCard(playerBoard.getNickname(), parts[1], Integer.parseInt(parts[2])));
        }
    }

    public void payResources(){
        int i=1;
        if(parts[0]=="PAYRESOURCES") {
            while (parts[i + 2] != null) {
                controller.sendMessage(new MessagePayResources(playerBoard.getNickname(), parts[i].charAt(0), parts[i + 1], Integer.parseInt(parts[i + 2])));
                i+=2;
            }
        }
    }

    public void activeBaseProduction(){
        if(parts[0]=="ACTIVEBASEPRODUCTION"){
            controller.sendMessage(new MessageActiveBaseProduction(playerBoard.getNickname(), parts[1], parts[2].charAt(0), parts[3], parts[4].charAt(0), parts[5]));
        }
    }

    public void activeLeaderActionProd(){
        if(parts[0]=="ACTIVELEADERACTIONPROD"){
            controller.sendMessage(new MessageActiveLeaderCardProduction(playerBoard.getNickname(), parts[1], parts[2].charAt(0), parts[3]));
        }
    }

    public void activeDevCardProduction(){
        if(parts[0]=="ACTIVEDEVCARDPRODUCTION"){
            controller.sendMessage(new MessageActiveProductionDevCard(playerBoard.getNickname(), Integer.parseInt(parts[1])));
        }
    }

    public void updateLeaderCard(){
        boolean state = false;
        if(parts[0]=="UPDATELEADERCARD"){
            if(Integer.parseInt(parts[2]) == 0) state = false;
            else if(Integer.parseInt(parts[2])==1) state = true;
            controller.sendMessage(new MessageUpdateStateLeaderAction(playerBoard.getNickname(), parts[1], state));
        }
    }

    public void endTurn(){
        if(parts[0]=="ENDTURN"){
            controller.sendMessage(new MessageEndTurn(playerBoard.getNickname()));
        }
    }

    public void endProduction(){
        if(parts[0]=="ENDPRODUCTION"){
            controller.sendMessage(new MessageEndProduction(playerBoard.getNickname()));
        }
    }

    public void help(){
        if(parts[0]=="HELP"){
            cli.help();
        }
    }

    /**
     * this method call the right cli's method, where it shows what player wants
     */
    public void show(){
        if(parts[0]=="SHOW"){
            switch(parts[1].toUpperCase(Locale.ROOT)){
                case "LEADERACTIONBOX":
                    cli.showLeaderActionBox();
                    break;
                case "SLOTDEVCARD":
                    cli.showSlotDevCard();
                    break;
                case "WAREHOUSE":
                    cli.showWarehouse();
                    break;
                case "STRONGBOX":
                    cli.showStrongbox();
                    break;
                case "FAITHTRACK":
                    cli.showFaithTrack();
                    break;
                case "MARKETTRAY":
                    cli.showMarketTray();
                    break;
                case "DEVCARDDECK":
                    cli.showDevCardDeck();
                    break;
                case "EXTRACHEST":
                    cli.showExtraChest();
                    break;
                case "DEVCARD":
                    cli.showDevCard(parts[2]);
                    break;
                case "LEADERACTION":
                    cli.showLeaderAction(parts[2]);
                    break;
                case "LORENZOTURN":
                    cli.showLorenzoTrun();
                    break;
                default:
                    System.out.println("Nothing to show...");
                    break;
            }
        }
    }
}
