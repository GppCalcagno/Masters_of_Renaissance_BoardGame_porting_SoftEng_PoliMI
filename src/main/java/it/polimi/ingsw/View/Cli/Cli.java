package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.Network.Message.ClientMessage.*;
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
        out.println("Please enter server IP [default: 127.0.0.1]: ");
        String serverAddress = input.nextLine();

        out.println("\n");
        out.println("Please enter server port [default : 1234]: ");
        int serverPort = input.nextInt();

        controller.sendMessage(new MessageConnect(serverAddress, serverPort));
    }

    @Override
    public void endturn() {
        controller.sendMessage(new MessageEndTurn(playerBoard.getNickname()));

    }

    @Override  //nickname + num player + gamestart + first resources and leader action tutto in un unico metodo non è meglio??
    public void askNickname() {
        out.println("\n");
        out.println("Please enter your nickname: ");
        String nickname = input.nextLine();
        out.println("Welcome : " + nickname);
        controller.sendMessage(new MessageLogin(nickname));
    }

    @Override
    public void askNumPlayer() {
        out.println("\n");
        out.println("Please enter the number of players: ");
        int numPlayers = input.nextInt();
        controller.sendMessage(new MessageNumPlayers(playerBoard.getNickname(), numPlayers));
    }

    @Override
    public void GameStart(){
        ViewStart viewstart = new ViewStart();
    }


    @Override
    public void askChooseLeaderCards() {
        showLeaderActionBox();
        out.println("Please enter which two cards you want keep during the game: [0-3]");
        int i1 = input.nextInt();
        int i2 = input.nextInt();
        controller.sendMessage(new MessageChooseLeaderCards(playerBoard.getNickname(), i1, i2));
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

    public void help(){
        System.out.println("Here the commands you can insert during the game: " +
                "\n extractionmarble <row or column (r/c)> < index of r/c>" +
                "\n managemarble <W/E/D (warehouse,extrachest,discard)> (optional)<index of W> <resources wanted if changeWitheMarble is activated>" +
                "\n exchangewarehouse <row1> <row2>" +
                "\n buydevcard <id card> <position where store the card in slotdevcard>" +
                "\n payresources <W/S/E (warehouse/strongbox/extrachest)> <resource> <how many>" +
                "\n activebaseproduction <resource wanted> <W/S/E> <resource paied> <W/S/E> <resource paied>" +
                "\n activeleaderaction <id card> <W/S/E> <resource>" +
                "\n activedevcardproduction <id card>" +
                "\n updateleadercard <id card> <0 discard 1 active>" +
                "\n endturn" +
                "\n endproduction" +
                "\n show <structure to show>" +
                "\n help ");
    }

}
