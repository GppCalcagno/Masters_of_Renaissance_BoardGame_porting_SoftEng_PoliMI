package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.ViewInterface;
import javafx.application.Platform;

import java.util.List;

public class Gui implements ViewInterface, Runnable{

    private PlayerBoard playerBoard;
    private ClientController clientController;
    private SceneLauncher sceneLauncher;

    public Gui(){
        playerBoard = new PlayerBoard();
        clientController = new ClientController(this);
        this.sceneLauncher = new SceneLauncher(clientController, playerBoard);
    }

    @Override
    public void run(){
        clientController.readMessage();
    }

    @Override
    public void askServerInfo() {
    }

    @Override
    public void askLogin() {
        Platform.runLater(()-> {
            GuiJavaFX.getWindow().setScene(sceneLauncher.askLogin());
        });
    }

    @Override
    public void askNumPlayer() {
        Platform.runLater(()-> {
            GuiJavaFX.getWindow().setScene(sceneLauncher.askNumPlayer());
        });
    }

    @Override
    public void init() {
        //askServerInfo();
    }

    @Override
    public void onUpdateStartGame() {
        Platform.runLater(()->{
            GuiJavaFX.getWindow().setScene(sceneLauncher.mainboard());
        });

    }

    @Override
    public void onUpdateCurrPlayer() {

    }

    @Override
    public void onUpdateInitialLeaderCards(List<String> leaderCard) {

    }

    @Override
    public void onUpdateActivatedDevCardProduction(String devCard) {

    }

    @Override
    public void onUpdateError(String error) {

    }

    @Override
    public void onUpdateDevCardDeck(String devCard) {

    }

    @Override
    public void onUpdateFaithMarker() {

    }

    @Override
    public void onUpdateMarketTray() {

    }

    @Override
    public void onUpdatePlayerState(String nickname, boolean state) {

    }

    @Override
    public void onUpdateUpdateResources() {

    }

    @Override
    public void onUpdateSinglePlayerGame() {

    }

    @Override
    public void onUpdateSlotDevCards() {

    }

    @Override
    public void onUpdateStateLeaderAction(String leaderCard, boolean state) {

    }

    @Override
    public void onUpdateStrongbox() {

    }

    @Override
    public void onUpdateWarehouse() {

    }

    @Override
    public void onUpdateWinnerMultiplayer() {

    }

    @Override
    public void onUpdateWinnerSinglePlayer() {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLeaderActionBox() {

    }

    @Override
    public void showSlotDevCard() {

    }

    @Override
    public void showWarehouse() {

    }

    @Override
    public void showStrongbox() {

    }

    @Override
    public void showFaithTrack() {

    }

    @Override
    public void showExtraChest() {

    }

    @Override
    public void showMarketTray() {

    }

    @Override
    public void showDevCardDeck() {

    }

    @Override
    public void showMarbleBuffer() {

    }

    @Override
    public void showDevCard(String ID) {

    }

    @Override
    public void showLeaderAction(String ID) {

    }

    @Override
    public void showLorenzoTrun() {

    }

    @Override
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    @Override
    public ClientController getController() {
        return clientController;
    }


}
