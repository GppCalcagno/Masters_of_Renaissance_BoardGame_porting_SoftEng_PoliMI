package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import it.polimi.ingsw.View.Cli.Structure.*;
import it.polimi.ingsw.View.ViewInterface;

import java.io.PrintStream;
import java.lang.management.ThreadInfo;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Cli implements ViewInterface {
    private PlayerBoard playerBoard;
    private ActionParser parser;
    private final PrintStream out;
    private ClientController controller;
    private Thread inThread;

    /**
     * @param playerBoard playerìs board
     * @param controller client controller
     */
    public Cli(PlayerBoard playerBoard, ClientController controller) {
        parser= new ActionParser(this,controller,playerBoard);
        this.playerBoard = playerBoard;
        this.controller = controller;
        out = System.out;
        inThread= new Thread(new InputReader(parser));
    }

    @Override
    public void start(){
        new ViewStart();
        askServerInfo();
    }

    /**
     * when a player connect, the server ip and the port are requested in order to set the connection with the server
     */
    @Override
    public void askServerInfo() {
        Scanner in= new Scanner(System.in);
        out.print("Please enter server IP [\"D\" for default: 127.0.0.1]: ");
        String serverAddress = in.nextLine();
        if(serverAddress.equals("D"))
            serverAddress="127.0.0.1";
        in.reset();

        boolean repeat=true;
        int serverPort=0;
        while (repeat)
        try {
            out.print("Please enter server port [\"D\" for default : 1234]: ");
            String bufferPort= in.nextLine();
            serverPort=1234;
            if(!bufferPort.equals("D"))
                serverPort= Integer.parseInt(bufferPort);
            repeat=false;
        }catch (NumberFormatException | InputMismatchException e){out.println("Please write a Number");}


        controller.connect(serverAddress, serverPort);
    }

    @Override
    public void askLogin() {
        Scanner in= new Scanner(System.in);
        out.print("Please enter your nickname: ");
        String nickname = in.nextLine();
        out.println("Welcome : " + nickname + "\n");
        playerBoard.setNickname(nickname);
        controller.sendMessage(new MessageLogin(nickname));
    }

    /**
     * how many players want to play. this is decided by the first player that connects himself to the server
     */
    @Override
    public void askNumPlayer() {
        Scanner in= new Scanner(System.in);
        int numPlayers = 0;
        boolean repeat=true;
        while(repeat){
            try{
                out.print("Please enter the number of players: ");
                numPlayers = Integer.parseInt(in.nextLine());
                repeat=false;
            }catch (NumberFormatException | InputMismatchException e){ out.println("Please write a Number");}
        }
        controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), numPlayers));
    }

    @Override
    public void onUpdateStartGame() {
        clearboard();

        out.println("Game is started. You have been assigned 4 leader cards. You have to choose only 2. Use the command: \n" +
                Color.ANSI_YELLOW.escape()+"\tCHOOSELEADERCARDS <int position> <int position>" +  Color.RESET+ " to select the ones you prefer\n"+
                Color.ANSI_YELLOW.escape()+"\tSHOW LEADERCARD <String ID>" +  Color.RESET+ " to see the card\n"+
                "Also, being the "+(playerBoard.getplayernumber()+1)+" player, you are entitled to "+playerBoard.getNumInitialResources()+" starting resourcesUse the command: \n"+
                Color.ANSI_YELLOW.escape()+"\tCHOOSERESOURCES <String Resources> <String Resources>" +  Color.RESET+ " to seelect the Resources (Stones,Shields,Servants,Coins)\n"
                );
        showLeaderActionBox();

        inThread.start();
        if(playerBoard.isMyturn()){
            out.println("it's your turn!");
        }
        else
        {
            out.println("it's "+playerBoard.getCurrentPlayer()+ " turn!");
            out.println("Wait until is your turn...");
        }
    }

    @Override
    public void onUpdateCurrPlayer() {
        if(playerBoard.isMyturn()) {
            if (playerBoard.getLeaderCards().size() == 4) {
                out.println("Your game is started, first of all you have to choose \n" +
                        Color.ANSI_YELLOW.escape() + "\tCHOOSERESOURCES <String Resources> <String Resources>" + Color.RESET + " to seelect the Resources (Stones,Shields,Servants,Coins)");
            } else {
                out.println("Is your turn, please insert a command, type HELP to see all commands\n");
                out.println(Color.ANSI_YELLOW.escape() + "\t EXTRACTIONMARBLE <r/c> <num>" + Color.RESET +
                        "\n -- <r/c> select row or column of the marketTray" +
                        "\n -- <num> the number or the row or the column\n" +
                        Color.ANSI_YELLOW.escape() + "\t BUYDEVCARD <ID> <positon>" + Color.RESET +
                        "\n -- <ID> is the id of the devCard" +
                        "\n -- <position> where you want to stored the card in your SlotDevCard\n" +
                        Color.ANSI_YELLOW.escape() + "\n ACTIVEDEVCARDPRODUCTION <ID>" + Color.RESET +
                        "\n -- <ID> the id of one of yours devCard that can be activated to do a production\n");
            }
        }
        else System.out.println("Is " + playerBoard.getCurrentPlayer() + "'s turn.");
    }

    @Override
    public void onUpdateInitialLeaderCards(List<String> leaderCard) {
        if(playerBoard.isMyturn()){
            System.out.println("You selected : " + leaderCard.get(0) + " and " + leaderCard.get(1));
            showLeaderActionBox();
        }
        else System.out.println(playerBoard.getCurrentPlayer() + " selected these leaderCard : " + leaderCard.get(0) + " and " + leaderCard.get(1));
    }

    @Override
    public void onUpdateActivatedDevCardProduction(String devCard) {
        if(playerBoard.isMyturn()){
            out.println("You activated : " + devCard);
            out.println("Now you can activate another production between :" +
                    Color.ANSI_YELLOW.escape()+"\t ACTIVEBASEPRODUCTION <resource wanted> <W/S/E> <resource> <W/S/E> <resource>" + Color.RESET +
                    "\n -- <resource wanted> is the resource you want to be produced" +
                    "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                    "\n -- <resource> the resource you want to be as payment for the production\n" +
                    Color.ANSI_YELLOW.escape()+"\nACTIVELEADERACTIONPROD <ID> <W/S/E> <resource> " +Color.RESET + "     only if you have a leader card with as effect an extraproduction" +
                    "\n -- <ID> the id of the leader card" +
                    "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                    "\n -- <resource> the resource you want to be as payment for the production\n" +
                    Color.ANSI_YELLOW.escape()+"\n ACTIVEDEVCARDPRODUCTION <ID>" + Color.RESET +
                    "\n -- <ID> the id of one of yours devCard that can be activated to do a production\n");
            out.println("Or you can type :" +
                    Color.ANSI_YELLOW.escape()+"\t ENDPRODUCTION  "+Color.RESET+"      to finish your production but not the turn so you can do other actions\n" +
                    Color.ANSI_YELLOW.escape()+"\t ENDTURN "+Color.RESET+"    to finish your turn\n");
        }
        else System.out.println(playerBoard.getCurrentPlayer() + " activated " + devCard);
    }

    @Override
    public void onUpdateError(String error) {
        System.out.println(Color.ANSI_RED.escape()+error+Color.RESET);
    }


    @Override
    public void onUpdateDevCardDeck(String devCard) {
        if(playerBoard.isMyturn()){
            System.out.println("You selected : " + devCard);
            System.out.println("Now you have to pay it. \n" +
                    Color.ANSI_YELLOW.escape() +"\tPAYRESOURCES <W/S/E> <resource> <number> "+Color.RESET+"   repeat all parameters for every different resource or every different structure the resource come from" +
                    "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                    "\n -- <resource> the resource needed for the payment" +
                    "\n -- <number> how many resources");
        }
        else System.out.println(playerBoard.getCurrentPlayer() + " bought : " + devCard);
    }

    @Override
    public void onUpdateFaithMarker() {
            new ViewFaithTrack(playerBoard).plot();
    }


    @Override
    public void onUpdateMarketTray() {
        if(playerBoard.isMyturn()) {
            System.out.println("You changed the marketTray");
            if (!playerBoard.getMarbleBuffer().isEmpty()) {
                showMarbleBuffer();
                System.out.println("You have to manage marbles you extracted\n" +
                        Color.ANSI_YELLOW.escape() + "\t MANAGEMARBLE <W/E/D> <row W> <resource>" + Color.RESET +
                        "\n -- <W/E/D> is where you want to store the marble: warehouse (W), extrachest (E), discard (D)" +
                        "\n -- <row W> if you selected to store the marble in the warehouse you have to write in which row" +
                        "\n -- <resource> if you have multiple leader card that transform a white marble in a resource please insert which resource you want");
            }
        }
        else System.out.println(playerBoard.getCurrentPlayer() + " changed the marketTray");
    }

    @Override
    public void onUpdatePlayerState(String nickname, boolean state) {
        if(state) System.out.println(nickname + " is now connected");
        else System.out.println(nickname + "in now disconnected");
    }

    @Override
    public void onUpdateUpdateResources() {
        if (playerBoard.isMyturn()) System.out.println("Your resources have been updated\n");
        showStrongbox();
        showWarehouse();
    }

    @Override
    public void onUpdateSinglePlayerGame() {
        System.out.println("Lorenzo played : " + playerBoard.getLastTokenUsed());
    }

    @Override
    public void onUpdateSlotDevCards() {
        System.out.println("You add a DevCard in your SlotDevCard");
    }

    @Override
    public void onUpdateStateLeaderAction(String leaderCard, boolean state) {
        if(playerBoard.isMyturn()) {
            if(state) System.out.println("You activated your leaderCard "+ leaderCard);
            else System.out.println("You discarded your leaderCard " + leaderCard);
        }
        else{
            if(state) System.out.println(playerBoard.getCurrentPlayer() + " activated his leaderCard " + leaderCard);
            else System.out.println(playerBoard.getCurrentPlayer() + " discard his leaderCard " + leaderCard);
        }
    }

    @Override
    public void onUpdateStrongbox() {
        if (playerBoard.isMyturn()) System.out.println("You update your strongbox ");
    }

    @Override

    public void onUpdateWarehouse() {
        if(playerBoard.isMyturn()) System.out.println("You update your warehouse");
        if(!playerBoard.getMarbleBuffer().isEmpty()){
            System.out.println("Please continue on manage your marbles...");
            showMarbleBuffer();
        }
    }

    @Override
    public void onUpdateWinnerMultiplayer() {
        System.out.println("The winner is : " + playerBoard.getPlayerWinner() + " with : " + playerBoard.getPlayersPoints().get(playerBoard.getPlayerWinner()) + " points!");
    }

    @Override
    public void onUpdateWinnerSinglePlayer() {
        if(playerBoard.getPlayerWinner().equals(playerBoard.getCurrentPlayer())) System.out.println("You WIN with " + playerBoard.getPlayersPoints());
        else System.out.println("Lorenzo win, try again...");
    }


    /**
     * this method print the message it receives
     * @param message message to print
     */
    @Override
    public void showMessage(String message) {
        out.println(message);
    }

    @Override
    public void showLeaderActionBox() {
        ViewLeaderActionBox viewLeaderActionBox = new ViewLeaderActionBox(playerBoard);
        viewLeaderActionBox.plot();
    }

    @Override
    public void showSlotDevCard() {
        ViewSlotDevCard viewSlotDevCard = new ViewSlotDevCard(playerBoard);
        viewSlotDevCard.plot();
    }

    @Override
    public void showWarehouse() {
        ViewWarehouse viewWarehouse = new ViewWarehouse(playerBoard);
        viewWarehouse.plot();
        if(!playerBoard.getExtrachest().isEmpty()) showExtraChest();
    }

    @Override
    public void showStrongbox() {
        ViewStrongbox viewStrongbox = new ViewStrongbox(playerBoard);
        viewStrongbox.plot();
    }

    @Override
    public void showFaithTrack() {
        ViewFaithTrack viewFaithTrack = new ViewFaithTrack(playerBoard);
        viewFaithTrack.plot();
    }

    @Override
    public void showMarketTray() {
        ViewMarketTray viewMarketTray = new ViewMarketTray(playerBoard);
        viewMarketTray.plot();
    }


    @Override
    public void showDevCardDeck() {
        ViewDevCardDeck viewDevCardDeck = new ViewDevCardDeck(playerBoard);
        viewDevCardDeck.plot();
    }

    @Override
    public void showExtraChest(){
        ViewExtraChest viewExtraChest = new ViewExtraChest(playerBoard);
        viewExtraChest.plot();
    }

    @Override
    public void showDevCard(String ID) {
        playerBoard.searchDevCard(ID).toString();
    }

    @Override
    public void showLeaderAction(String ID) {
        playerBoard.searchLeaderCard(ID).showCli();
    }

    @Override
    public void showMarbleBuffer(){
        out.println("There are " + playerBoard.getMarbleBuffer().size() + " remaining marbles to manage : ");
        for(String marble : playerBoard.getMarbleBuffer()) out.println(marble);
    }

    @Override
    public void showLorenzoTrun() {
        System.out.println(playerBoard.getLastTokenUsed().toString());
    }

    /**
     * this method print the allowed command that the player can write from his cli
     */
    public void help(){
        System.out.println("Please insert a command:\n " +
                Color.ANSI_YELLOW.escape()+"\t EXTRACTIONMARBLE <r/c> <num>" + Color.RESET +
                "\n -- <r/c> select row or column of the marketTray" +
                "\n -- <num> the number or the row or the column\n" +
                Color.ANSI_YELLOW.escape()+"\t MANAGEMARBLE <W/E/D> <row W> <resource>" + Color.RESET +
                "\n -- <W/E/D> is where you want to store the marble: warehouse (W), extrachest (E), discard (D)" +
                "\n -- <row W> if you selected to store the marble in the warehouse you have to write in which row" +
                "\n -- <resource> if you have multiple leader card that transform a white marble in a resource please insert which resource you want\n" +
                Color.ANSI_YELLOW.escape()+"\t EXCHANGEWAREHOUSE <row1> <row2>" + Color.RESET +
                "\n -- <row1> && <row2> are the rows you want to switch\n" +
                Color.ANSI_YELLOW.escape()+"\t BUYDEVCARD <ID> <positon>" + Color.RESET +
                "\n -- <ID> is the id of the devCard" +
                "\n -- <position> where you want to stored the card in your SlotDevCard\n" +
                Color.ANSI_YELLOW.escape()+"\t PAYRESOURCES <W/S/E> <resource> <number> "+Color.RESET+"   repeat all parameters for every different resource or every different structure the resource come from" +
                "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                "\n -- <resource> the resource needed for the payment" +
                "\n -- <number> how many resources\n" +
                Color.ANSI_YELLOW.escape()+"\t ACTIVEBASEPRODUCTION <resource wanted> <W/S/E> <resource> <W/S/E> <resource>" + Color.RESET +
                "\n -- <resource wanted> is the resource you want to be produced" +
                "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                "\n -- <resource> the resource you want to be as payment for the production\n" +
                Color.ANSI_YELLOW.escape()+"\nACTIVELEADERACTIONPROD <ID> <W/S/E> <resource> " +Color.RESET + "     only if you have a leader card with as effect an extraproduction" +
                "\n -- <ID> the id of the leader card" +
                "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                "\n -- <resource> the resource you want to be as payment for the production\n" +
                Color.ANSI_YELLOW.escape()+"\n ACTIVEDEVCARDPRODUCTION <ID>" + Color.RESET +
                "\n -- <ID> the id of one of yours devCard that can be activated to do a production" +
                Color.ANSI_YELLOW.escape()+"\n UPDATELEADERCARD <ID> <0/1>" + Color.RESET+
                "\n -- <ID> id of one of your leader card" +
                "\n -- <0/1> 0=discard, 1=active\n" +
                Color.ANSI_YELLOW.escape()+"\t ENDPRODUCTION  "+Color.RESET+"      when you want to finish your production but not the turn\n" +
                Color.ANSI_YELLOW.escape()+"\t ENDTURN "+Color.RESET+"    when you want to finish your turn\n" +
                Color.ANSI_YELLOW.escape()+"\t SHOW <object> " + Color.RESET +
                "\n -- <object> is something you want to be shown, tipe "+Color.ANSI_YELLOW.escape()+"HELPSHOW"+Color.RESET+" to see what you can show");
    }

    public void helpShow(){
        out.println("Type :" +
                Color.ANSI_YELLOW.escape()+"\t LEADERACTIONBOX " + Color.RESET + " to show your leaderCard and their state \n"+
                Color.ANSI_YELLOW.escape()+"\t SLOTDEVCARD " + Color.RESET + " to show your slotDevCard \n"+
                Color.ANSI_YELLOW.escape()+"\t WAREHOUSE " + Color.RESET + " to show your warehouse \n"+
                Color.ANSI_YELLOW.escape()+"\t STRONGBOX " + Color.RESET + " to show your strongbox \n"+
                Color.ANSI_YELLOW.escape()+"\t FAITHTRACK " + Color.RESET + " to show your faithMarker in your faithTrack \n"+
                Color.ANSI_YELLOW.escape()+"\t MARKETTRAY " + Color.RESET + " to show marketTray \n"+
                Color.ANSI_YELLOW.escape()+"\t DEVCARDDECK " + Color.RESET + " to show the devCardDeck \n"+
                Color.ANSI_YELLOW.escape()+"\t EXTRACHEST " + Color.RESET + " to show your extraChest, if you have one \n"+
                Color.ANSI_YELLOW.escape()+"\t DEVCARD <ID> " + Color.RESET + " to show the devCard that has that ID \n"+
                Color.ANSI_YELLOW.escape()+"\t LEADERCARD <ID> " + Color.RESET + " to show the leaderCard that has that ID \n"+
                Color.ANSI_YELLOW.escape()+"\t LEADERACTIONBOX " + Color.RESET + " to show your leaderCard and their state \n");
        if(playerBoard.getplayernumber()==1)
            out.println(Color.ANSI_YELLOW.escape()+"\t LORENZOTURN " + Color.RESET + " to show Lorenzo's last turn and which token he used \n");

    }

    public void clearboard(){
        for(int i=0;i<15;i++){
            System.out.println("\n");
        }
    }

}
