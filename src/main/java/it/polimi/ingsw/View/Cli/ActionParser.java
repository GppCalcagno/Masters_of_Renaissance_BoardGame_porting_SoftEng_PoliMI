package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import it.polimi.ingsw.model.exceptions.MessageFormatErrorException;

import java.util.*;

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
    public void commandParser(String input) {
        String [] parts = input.toUpperCase(Locale.ROOT).split(" ");
        if(!playerBoard.isMyturn()) return;
        if (parts[0] != "") {
            try {
                switch (parts[0]) {
                    case "OTHERPLAYER": otherPlayer(parts);                         break;
                    case "DISCONNECT": cli.onDisconnect();                          break;
                    case "CHOOSELEADERCARDS": chooseleadercars(parts);              break;
                    case "CHOOSERESOURCES": chooseresources(parts);                 break;
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
                    case "HELPSHOW": helpShow(parts);                               break;
                    case "CHEAT": cheat();                                        break;
                    case "EXIT": exit();                                            break;
                    default:
                        System.out.println(Color.ANSI_RED.escape() +"The command doesn't exist!" + Color.RESET);
                }
            } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(Color.ANSI_RED.escape() +"The command need more parameters, please write it correctly" + Color.RESET);
            } catch (NumberFormatException n){
                System.out.println(Color.ANSI_RED.escape() +"The command need at least a number, please insert it correctly"+ Color.RESET);
            } catch (MessageFormatErrorException e) {
                System.out.println(e.getMessage());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(Color.ANSI_RED.escape() + "You have inserted to much blank spaces." + Color.RESET);
            }
        }
    }

    private void otherPlayer(String[] parts){
        controller.sendMessage(new MessageReqOtherPlayer(playerBoard.getNickname(),parts[1]));
    }


    private void chooseresources(String[] parts) {
        List<String> resources= new ArrayList<>();
        for(int i=1;i<parts.length;i++){
                resources.add(convertformat(parts[i]));
        }
        controller.sendMessage(new MessageChooseResourcesFirstTurn(playerBoard.getNickname(),resources));
    }

    private void chooseleadercars(String[] parts) {
        controller.sendMessage(new MessageChooseLeaderCards(playerBoard.getNickname(), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
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
        char structure;

        structure = parts[1].charAt(0);
        if(parts.length>2){
            row = Integer.parseInt(parts[2]);
            if(parts.length>3)
                resources = parts[3];
        }
        controller.sendMessage(new MessageManageMarbles(playerBoard.getNickname(), structure, row, resources));
    }

    public void buyDevCard(String[] parts){
        boolean flag = false;
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                for(int k=0; k<4; k++){
                    if(playerBoard.getDevCardDeck()[i][j][k]!=null && playerBoard.getDevCardDeck()[i][j][k].equals(parts[1])) flag = true;
                }
            }
        }
        if(flag) controller.sendMessage(new MessageBuyDevCard(playerBoard.getNickname(), parts[1], Integer.parseInt(parts[2])));
        else System.out.println(Color.ANSI_RED.escape() + "This card have been alredy bought! " + Color.ANSI_BRIGHTWHITE.escape());
    }

    public void payResources(String[] parts) throws MessageFormatErrorException {

        int i=1;
        if((parts.length-1)%3!=0) throw new MessageFormatErrorException(Color.ANSI_RED.escape() +"Command format Error" + Color.RESET);
        Map<String,Integer> warehouse=new HashMap<>();
        Map<String,Integer> strongBox=new HashMap<>();
        Map<String,Integer> extraChest=new HashMap<>();

            while (i+3<=parts.length) {
                char structure= parts[i].charAt(0);
                int old=0;
                if(isRightResource(parts[i+1])){
                    String resources= convertformat(parts[i+1]);
                    switch (structure){
                        case 'W':
                            if(warehouse.containsKey(resources))
                                old=warehouse.get(resources);
                            warehouse.put(resources,old+ Integer.parseInt(parts[i+2]));
                            break;
                        case 'S':
                            if(strongBox.containsKey(resources))
                                old=strongBox.get(resources);
                            strongBox.put(resources,old+ Integer.parseInt(parts[i+2]));
                            break;
                        case 'E':
                            if(extraChest.containsKey(resources))
                                old=extraChest.get(resources);
                            extraChest.put(resources,old+ Integer.parseInt(parts[i+2]));
                            break;
                        default: throw new MessageFormatErrorException(Color.ANSI_RED.escape() +"Type of Storage not Correct" +Color.RESET);
                    }
                }
                else
                    throw new MessageFormatErrorException(Color.ANSI_RED.escape() + "Type of Resources not Correct" + Color.RESET);
                i=i+3;
            }//fine while
        controller.sendMessage(new MessagePayResources(playerBoard.getNickname(),warehouse,strongBox,extraChest));
        }

    public void activeBaseProduction(String[] parts)  {
        controller.sendMessage(new MessageActiveBaseProduction(playerBoard.getNickname(), convertformat(parts[1]), parts[2].charAt(0), convertformat(parts[3]), parts[4].charAt(0), convertformat(parts[5])));
    }

    public void activeLeaderActionProd(String[] parts){
            controller.sendMessage(new MessageActiveLeaderCardProduction(playerBoard.getNickname(), parts[1], parts[2].charAt(0), convertformat(parts[3])));
    }

    public void activeDevCardProduction(String[] parts){
            controller.sendMessage(new MessageActiveProductionDevCard(playerBoard.getNickname(), Integer.parseInt(parts[1])));
    }

    public void updateLeaderCard(String[] parts){
        controller.sendMessage(new MessageUpdateStateLeaderActionClient(playerBoard.getNickname(), parts[1], Integer.parseInt(parts[2])));
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

    public void helpShow(String[] parts){ cli.helpShow();}
    /**
     * this method call the right cli's method, where it shows what player wants
     * @param parts
     */
    public void show(String[] parts){
            switch(parts[1].toUpperCase(Locale.ROOT)){
                case "LEADERACTIONBOX": cli.showLeaderActionBox();                              break;
                case "SLOTDEVCARD": cli.showSlotDevCard(playerBoard.getSlotDevCard());          break;
                case "WAREHOUSE":  cli.showWarehouse();                                         break;
                case "STRONGBOX":   cli.showStrongbox(playerBoard.getStrongbox());              break;
                case "FAITHTRACK": cli.showFaithTrack();                                        break;
                case "MARKETTRAY": cli.showMarketTray();                                        break;
                case "DEVCARDDECK": cli.showDevCardDeck();                                      break;
                case "EXTRACHEST": cli.showExtraChest();                                        break;
                case "DEVCARD": cli.showDevCard(parts[2]);                                      break;
                case "LEADERCARD": cli.showLeaderAction(parts[2]);                              break;
                case "LORENZOTURN": cli.showLorenzoTurn();                                      break;
                case "MARBLEBUFFER": cli.showMarbleBuffer();                                    break;
                case "OTHERPLAYERS": cli.showOtherPlayer();                                     break;
                default:
                    System.out.println(Color.ANSI_RED.escape() +"Nothing to show..." + Color.RESET);
                    break;
        }
    }

    public boolean isRightResource (String resource) {
        String[] allResources = {"COINS", "SERVANTS", "SHIELDS", "STONES"};
        for (String r : allResources){
            if (resource.equals(r))
                return true;
        }
        return false;
    }


    public String convertformat(String name){
        //da tutto maiuscolo a prima maiuscola e altre minuscile
        name=name.toLowerCase(Locale.ROOT);
        char[] vet= name.toCharArray();
        vet[0]= Character.toUpperCase(vet[0]);
        return  String.copyValueOf(vet);
    }

    public void cheat() {
        controller.sendMessage(new MessageFake(playerBoard.getNickname()));
    }

    public void exit(){
        System.exit(0);
    }
}
