package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.ViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;

public class Gui implements ViewInterface{

    private PlayerBoard playerBoard;
    private ClientController clientController;
    private SceneLauncher sceneLauncher;
    private GuiJavaFX guiJavaFX;

    public Gui(ClientController controller){
        this.clientController=controller;
        playerBoard = new PlayerBoard();
        guiJavaFX= new GuiJavaFX();

        this.sceneLauncher = new SceneLauncher(clientController, playerBoard);

        Platform.startup(()->sceneLauncher.setStage());
        Platform.runLater(()->guiJavaFX.start(sceneLauncher.getStage()));
    }


    @Override
    public void askServerInfo() {
        Platform.runLater(()-> {
            sceneLauncher.getStage().setScene(sceneLauncher.askServerInfo());
        });
    }

    @Override
    public void askLogin() {
        Platform.runLater(()-> {
            sceneLauncher.getStage().setScene(sceneLauncher.askLogin());
        });
    }

    @Override
    public void askNumPlayer() {
        Platform.runLater(()-> {
            sceneLauncher.getStage().setScene(sceneLauncher.askNumPlayer());
        });
    }

    @Override
    public void init() {
    }

    @Override
    public void onUpdateStartGame() {
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
