package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import it.polimi.ingsw.Network.Message.UpdateMesssage.MessageUpdateStateLeaderAction;
import it.polimi.ingsw.model.exceptions.MessageFormatErrorException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActionParser {
    /**
     * this class take the input from the client and parse them, in order to send the right message to the server
     */


    private ClientController controller;
    private PlayerBoard playerBoard;
    private Cli cli;

    /**
     * this is the constructor, it is called from the Cli
     * @param cli the cli of the player
     * @param controller the client controller
     * @param playerBoard the player's board
     */
    public ActionParser(Cli cli, ClientController controller, PlayerBoard playerBoard){
        this.cli = cli;
        this.playerBoard = playerBoard;
        this.controller = controller;
    }

    /**
     * this method parse the input from the player and launch the right method for the turn that player choose to do
     */
    public void commandParcer(String input) {
        String [] parts = input.toUpperCase(Locale.ROOT).split(" ");
        if (parts[0] != "") {
            try {
                switch (parts[0]) {
                    case "EXTRACTIONMARBLE": extractMarble(parts);                  break;
                    case "MANAGEMARBLE":  manageMarble(parts);                      break;
                    case "EXCHANGEWAREHOUSE": exchangeWarehouse(parts);             break;
                    case "BUYDEVCARD": buyDevCard(parts);                           break;
                    case "PAYRESOURCES": payResources(parts);                       break;
                    case "ACTIVEBASEPRODUCTION": activeBaseProduction(parts);       break;
                    case "ACTIVELEADERACTIONPROD": activeLeaderActionProd(parts);   break;
                    case "ACTIVEDEVCARDPRODUCTION": activeDevCardProduction(parts); break;
                    case "UPDATELEADERCARD": updateLeaderCard(parts);               break;
                    case "ENDTURN": endTurn(parts);                                 break;
                    case "ENDPRODUCTION": endProduction(parts);                     break;
                    case "SHOW": show(parts);                                       break;
                    case "HELP": help(parts);                                       break;
                    default:
                }
            } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("The command need more parameters, please write it correctly");
            } catch (NumberFormatException n){
                System.out.println("The command need at least a number, please insert it correctly");
            } catch (MessageFormatErrorException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void extractMarble(String[] parts) {
            controller.sendMessage(new MessageExtractionMarbles(playerBoard.getNickname(), parts[1].charAt(0), Integer.parseInt(parts[2])));
    }

    public void exchangeWarehouse(String[] parts) {
            controller.sendMessage(new MessageExchangeWarehouse(playerBoard.getNickname(), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
    }

    public void manageMarble(String[] parts){
        String resources=null;
        int row=-1;
        int structure = -1;

        structure = Integer.parseInt(parts[1]);
        if(parts.length>1){
            row = Integer.parseInt(parts[2]);
            if(parts.length>2)
                resources = parts[3];
        }
        controller.sendMessage(new MessageManageMarbles(playerBoard.getNickname(), structure, row, resources));
    }

    public void buyDevCard(String[] parts){
            controller.sendMessage(new MessageBuyDevCard(playerBoard.getNickname(), parts[1], Integer.parseInt(parts[2])));
    }

    public void payResources(String[] parts) throws MessageFormatErrorException {
        int i=1;
        if((parts.length-1)%3!=0) throw new MessageFormatErrorException("Command format Error");

        Map<String,Integer> warehouse=new HashMap<>();
        Map<String,Integer> strongBox=new HashMap<>();
        Map<String,Integer> extraChest=new HashMap<>();

            while (parts[i + 2] != null) {
                char structure= parts[i].charAt(0);
                int old=0;
                if(isRightResource(parts[i+1])){
                    switch (structure){
                        case 'w':
                            if(warehouse.containsKey(parts[i+1]))
                                old=warehouse.get(parts[i+1]);
                            warehouse.put(parts[i+1],old+ Integer.parseInt(parts[i+2]));
                            break;
                        case 's':
                            if(strongBox.containsKey(parts[i+1]))
                                old=strongBox.get(parts[i+1]);
                            strongBox.put(parts[i+1],old+ Integer.parseInt(parts[i+2]));
                            break;
                        case 'e':
                            if(extraChest.containsKey(parts[i+1]))
                                old=extraChest.get(parts[i+1]);
                            extraChest.put(parts[i+1],old+ Integer.parseInt(parts[i+2]));
                            break;
                        default: throw new MessageFormatErrorException("Type of Storage not Correct");
                    }
                }
                else
                    throw new MessageFormatErrorException("Type of Resources not Correct");
                i+=3;
            }//fine while
        controller.sendMessage(new MessagePayResources(playerBoard.getNickname(),warehouse,strongBox,extraChest));
        }

    public void activeBaseProduction(String[] parts){
        controller.sendMessage(new MessageActiveBaseProduction(playerBoard.getNickname(), parts[1], parts[2].charAt(0), parts[3], parts[4].charAt(0), parts[5]));
    }

    public void activeLeaderActionProd(String[] parts){
            controller.sendMessage(new MessageActiveLeaderCardProduction(playerBoard.getNickname(), parts[1], parts[2].charAt(0), parts[3]));
    }

    public void activeDevCardProduction(String[] parts){
            controller.sendMessage(new MessageActiveProductionDevCard(playerBoard.getNickname(), Integer.parseInt(parts[1])));
    }

    public void updateLeaderCard(String[] parts){
        boolean state = false;
        if(Integer.parseInt(parts[2])==1) state = true;
        controller.sendMessage(new MessageUpdateStateLeaderAction(playerBoard.getNickname(), parts[1], state));
    }

    public void endTurn(String[] parts){
        controller.sendMessage(new MessageEndTurn(playerBoard.getNickname()));
    }

    public void endProduction(String[] parts){
        controller.sendMessage(new MessageEndProduction(playerBoard.getNickname()));
    }

    public void help(String[] parts){
        cli.help();
    }

    /**
     * this method call the right cli's method, where it shows what player wants
     * @param parts
     */
    public void show(String[] parts){
        if(parts[0]=="SHOW"){
            switch(parts[1].toUpperCase(Locale.ROOT)){
                case "LEADERACTIONBOX": cli.showLeaderActionBox();  break;
                case "SLOTDEVCARD": cli.showSlotDevCard();          break;
                case "WAREHOUSE":  cli.showWarehouse();             break;
                case "STRONGBOX":   cli.showStrongbox();            break;
                case "FAITHTRACK": cli.showFaithTrack();            break;
                case "MARKETTRAY": cli.showMarketTray();            break;
                case "DEVCARDDECK": cli.showDevCardDeck();          break;
                case "EXTRACHEST": cli.showExtraChest();            break;
                case "DEVCARD": cli.showDevCard(parts[2]);          break;
                case "LEADERACTION": cli.showLeaderAction(parts[2]);break;
                case "LORENZOTURN": cli.showLorenzoTrun();          break;
                default:
                    System.out.println("Nothing to show...");
                    break;
            }
        }
    }

    public boolean isRightResource (String resource) {
        String[] allResources = {"Coins", "Servants", "Shields", "Stones"};
        for (String r : allResources){
            if (resource.equals(r))
                return true;
        }
        return false;
    }
}
