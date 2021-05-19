package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
import it.polimi.ingsw.View.Cli.Structure.*;
import it.polimi.ingsw.View.ViewInterface;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;


public class Cli implements ViewInterface {
    private PlayerBoard playerBoard;
    private ActionParser parser;
    private final PrintStream out;
    private ClientController controller;
    private InputReader input;

    /**
     * @param playerBoard playerìs board
     * @param controller client controller
     */
    public Cli(PlayerBoard playerBoard, ClientController controller) {
        parser= new ActionParser(this,controller,playerBoard);
        this.playerBoard = playerBoard;
        this.controller = controller;
        out = System.out;
        input = new InputReader(parser);
        out.println("sono la cli");
    }
    @Override
    public void start(){
        gameStart();
        askServerInfo();
    }


    /**
     * this method calls InputReader and read the input inserted by the player
     */
    @Override
    public void inputFromPlayer() {
        while (playerBoard.isMyturn()) {
            input.run();
        }
    }

    /**
     * when a player connect, the server ip and the port are requested in order to set the connection with the server
     */
    @Override
    public void askServerInfo() {
        Scanner in= new Scanner(System.in);
        out.println("\n");
        out.println("Please enter server IP [default: 127.0.0.1]: ");
        String serverAddress = in.nextLine();
        out.println("\n");
        out.println("Please enter server port [default : 1234]: ");
        int serverPort = in.nextInt();
        controller.connect(serverAddress, serverPort);
    }

    @Override
    public void askLogin() {
        Scanner in= new Scanner(System.in);
        out.println("\n");
        out.println("Please enter your nickname: ");
        String nickname = in.nextLine();
        out.println("Welcome : " + nickname);
        controller.sendMessage(new MessageLogin(nickname));
    }

    /**
     * how many players want to play. this is decided by the first player that connects himself to the server
     */
    @Override
    public void askNumPlayer() {
        Scanner in= new Scanner(System.in);
        out.println("\n");
        out.println("Please enter the number of players: ");
        int numPlayers = in.nextInt();
        controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), numPlayers));
    }

    @Override
    public void onUpdateStartGame() {
        System.out.println("Game is started. Have Fun!");
    }

    @Override
    public void onUpdateCurrPlayer() {
        if(playerBoard.isMyturn()){
            System.out.println("Is your turn, please insert a command");
            System.out.println("type HELP to see all commands");
        }
        else System.out.println("Is " + playerBoard.getCurrentPlayer() + "'s turn.");
    }

    @Override
    public void onUpdateInitialLeaderCards(List<String> leaderCard) {
        if(playerBoard.isMyturn()) System.out.println("You selected : " + leaderCard.get(0) + " and " + leaderCard.get(1));
        else System.out.println(playerBoard.getCurrentPlayer() + " selected these leaderCard : " + leaderCard.get(0) + " and " + leaderCard.get(1));
    }

    @Override
    public void onUpdateActivatedDevCardProduction(String devCard) {
        if(playerBoard.isMyturn()) System.out.println("You activated : " + devCard);
        else System.out.println(playerBoard.getCurrentPlayer() + " activated " + devCard);
    }

    @Override
    public void onUpdateError(String error) {
        System.out.println(error);
    }

    @Override
    public void onUpdateRequestNumPlayers() {
        askNumPlayer();
    }

    @Override
    public void onUpdateDevCardDeck(String devCard) {
        if(playerBoard.isMyturn()){
            System.out.println("You selected : " + devCard);
            System.out.println("Now you have to pay it. " +
                    "\n PAYRESOURCES <W/S/E> <resource> <number>    repeat all parameters for every different resource or every different structure the resource come from" +
                    "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                    "\n -- <resource> the resource needed for the payment" +
                    "\n -- <number> how many resources");
        }
        else System.out.println(playerBoard.getCurrentPlayer() + " bought : " + devCard);
    }

    @Override
    public void onUpdateFaithMarker() {
        if (playerBoard.isMyturn()) System.out.println("This is your new faithMarker's position : " + playerBoard.getFaithMarker());
        else System.out.println(playerBoard.getCurrentPlayer() + "'s faithMarker is now at : " + playerBoard.getPlayersFaithMarkerPosition().get(playerBoard.getCurrentPlayer()));
    }

    @Override
    public void onUpdateInfo(String info) {
        System.out.println(info);
    }

    @Override
    public void onUpdateMarketTray() {
        if(playerBoard.isMyturn()){
            System.out.println("You changed the marketTray");
            if(playerBoard.getMarbleBuffer()!=null)
            System.out.println("Now you have to manage marbles you extracted" +
                    "\n MANAGEMARBLE <W/E/D> <row W> <resource>" +
                    "\n -- <W/E/D> is where you want to store the marble: warehouse (W), extrachest (E), discard (D)" +
                    "\n -- <row W> if you selected to store the marble in the warehouse you have to write in which row" +
                    "\n -- <resource> if you have multiple leader card that transform a white marble in a resource please insert which resource you want");
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
        if (playerBoard.isMyturn()) System.out.println("You update your Resources");
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
        if(playerBoard.getMarbleBuffer()!=null) System.out.println("Please continue on manage your marbles...");
    }

    @Override
    public void onUpdateWinnerMultiplayer() {
        System.out.println("The winner is : " + playerBoard.getPlayerWinner() + " with : " + playerBoard.getPlayersPoints().get(playerBoard.getPlayerWinner()) + " points!");
    }

    @Override
    public void onUpdateWinnerSinglePlayer() {
        if(playerBoard.getPlayerWinner()==playerBoard.getCurrentPlayer()) System.out.println("You WIN with " + playerBoard.getPlayersPoints());
        else System.out.println("Lorenzo win, try again...");
    }
    /**
     * show the start of the game
     */
    public void gameStart(){
        ViewStart viewstart = new ViewStart();
    }

    /**
     * this method print the message it receives
     * @param message
     */
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
        playerBoard.searchDevCard(ID);
    }

    @Override
    public void showLeaderAction(String ID) {
        System.out.println(playerBoard.searchLeaderCard(ID));
    }

    @Override
    public void showLorenzoTrun() {
        System.out.println(playerBoard.getLastTokenUsed());
    }

    /**
     * this method print the allowed command that the player can write from his cli
     */
    public void help(){
        System.out.println("Please insert a command: " +
                "\n EXTRACTIONMARBLE <r/c> <num>" +
                "\n -- <r/c> select row or column of the marketTray" +
                "\n -- <num> the number or the row or the column" +
                "\n MANAGEMARBLE <W/E/D> <row W> <resource>" +
                "\n -- <W/E/D> is where you want to store the marble: warehouse (W), extrachest (E), discard (D)" +
                "\n -- <row W> if you selected to store the marble in the warehouse you have to write in which row" +
                "\n -- <resource> if you have multiple leader card that transform a white marble in a resource please insert which resource you want" +
                "\n EXCHANGEWAREHOUSE <row1> <row2>" +
                "\n -- <row1> && <row2> are the rows you want to switch" +
                "\n BUYDEVCARD <ID> <positon>" +
                "\n -- <ID> is the id of the devCard" +
                "\n -- <position> where you want to stored the card in your SlotDevCard" +
                "\n PAYRESOURCES <W/S/E> <resource> <number>    repeat all parameters for every different resource or every different structure the resource come from" +
                "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                "\n -- <resource> the resource needed for the payment" +
                "\n -- <number> how many resources" +
                "\n ACTIVEBASEPRODUCTION <resource wanted> <W/S/E> <resource> <W/S/E> <resource>" +
                "\n -- <resource wanted> is the resource you want to be produced" +
                "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                "\n -- <resource> the resource you want to be as payment for the production" +
                "\nACTIVELEADERACTIONPROD <ID> <W/S/E> <resource>      only if you have a leader card with as effect an extraproduction" +
                "\n -- <ID> the id of the leader card" +
                "\n -- <W/S/E> where the resource you want to use to pay is taken: warehouse (W), strongbox (S), extrachest (E)" +
                "\n -- <resource> the resource you want to be as payment for the production" +
                "\n ACTIVEDEVCARDPRODUCTION <ID>" +
                "\n -- <ID> the id of one of yours devCard that can be activated to do a production" +
                "\n UPDATELEADERCARD <ID> <0/1>" +
                "\n -- <ID> id of one of your leader card" +
                "\n -- <0/1> 0=discard, 1=active" +
                "\n ENDPRODUCTION        when you want to finish your production but not the turn" +
                "\n ENDTURN     when you want to finish your turn" +
                "\n SHOW <object> " +
                "\n -- <object> is something you want to be shown");
    }
}
