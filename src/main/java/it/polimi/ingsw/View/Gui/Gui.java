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
        if (playerBoard.isMyturn()) {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards());
            });
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn"));
            });
        }
    }

    @Override
    public void onUpdateCurrPlayer() {
        if(playerBoard.isMyturn()) {
            if (playerBoard.getLeaderCards().size() == 4) {
                Platform.runLater(() -> {
                    sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards());
                });
            }
            else {
                Platform.runLater(() -> {
                    sceneLauncher.getStage().setFullScreen(true);
                    sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                });
            }
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn"));
            });
        }
    }

    @Override
    public void onUpdateInitialLeaderCards(List<String> leaderCard) {
        if (playerBoard.isMyturn()) {
            if (playerBoard.getPlayerList().indexOf(playerBoard.getCurrentPlayer()) == 0) {
                Platform.runLater(() -> {
                    sceneLauncher.getStage().setFullScreen(true);
                    sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                });
            }
            else {
                Platform.runLater(() -> {
                    sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialResources());
                });
            }
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn"));
            });
        }
    }

    @Override
    public void onUpdateActivatedDevCardProduction(String devCard) {

    }

    @Override
    public void onUpdateError(String error) {
        Platform.runLater(() -> {
            sceneLauncher.showErrorMessage(error);
        });
    }

    @Override
    public void onUpdateDevCardDeck(String devCard) {

    }

    @Override
    public void onUpdateFaithMarker() {
        Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
    }

    @Override
    public void onUpdateMarketTray() {

    }

    @Override
    public void onUpdateUpdateResources() {

    }

    @Override
    public void onUpdateSinglePlayerGame() {
        Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
    }

    @Override
    public void onUpdateSlotDevCards() {

    }

    @Override
    public void onUpdateStateLeaderAction(String leaderCard, boolean state) {

    }

    @Override
    public void onUpdateStrongbox() {
        Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));

    }

    @Override
    public void onUpdateWarehouse() {
        //provvisorio
        if (playerBoard.isMyturn()) {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
            });
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn"));
            });
        }
    }

    @Override
    public void onUpdateWinnerMultiplayer() {

    }

    @Override
    public void onUpdateWinnerSinglePlayer() {

    }

    @Override
    public void onDisconnect() {
        // when the server asks to disconnect
        clientController.disconnect();
    }

    @Override
    public void onPlayerDisconnect(String name) {
        //when a player logs out

    }

    @Override
    public void onResume(String name) {

    }

    @Override
    public void showMessage(String message) {
        Platform.runLater(() -> {
            sceneLauncher.getStage().setScene(sceneLauncher.showMessage("Wait for other players"));
        });
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
