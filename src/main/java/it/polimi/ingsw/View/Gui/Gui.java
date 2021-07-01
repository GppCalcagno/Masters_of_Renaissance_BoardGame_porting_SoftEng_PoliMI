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
    public void onUpdateStartGame() {
        if (playerBoard.isMyturn()) {
            Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards()));
        }
        else {
            Platform.runLater(() -> sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn"));
            Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));
        }
    }

    @Override
    public void onUpdateCurrPlayer() {
        if(playerBoard.isMyturn()) {
            Platform.runLater(()->sceneLauncher.showMessage("It' your turn"));
            if (playerBoard.getLeaderCards().size() > 2) {
                Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards()));
            } else {
                if (playerBoard.getCurrentInitialResourcesToChoose() != 0) {
                    Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialResources()));
                } else {
                    if (!playerBoard.getMarbleBuffer().isEmpty()) {
                        Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                        Platform.runLater(() -> sceneLauncher.manageMarbleScene());
                    } else {
                        if (playerBoard.getActivedDevCardProd() != null) {
                            Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                            Platform.runLater(() -> sceneLauncher.payResourcesScene());
                        } else {
                            if (playerBoard.getCurrentDevCardToBuy() != null) {
                                Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                                Platform.runLater(() -> sceneLauncher.payResourcesScene());
                            } else {
                                Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                            }
                        }
                    }
                }
            }
        }
        else {
            Platform.runLater(() -> {
                if (sceneLauncher.getProductionsStage() != null)
                    sceneLauncher.getProductionsStage().close();
                if (sceneLauncher.getExtractionMarbleStage() != null)
                    sceneLauncher.getExtractionMarbleStage().close();
                if (sceneLauncher.getActiveBuyDevCardStage() != null)
                    sceneLauncher.getActiveBuyDevCardStage().close();
                if(playerBoard.getLeaderCards().size()<=2)sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn");
            });
        }
    }

    @Override
    public void onUpdateInitialLeaderCards(List<String> leaderCard) {
        if (playerBoard.isMyturn()) {
            if (playerBoard.getPlayerList().indexOf(playerBoard.getCurrentPlayer()) == 0) {
                Platform.runLater(() -> {
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
                sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn");
            });
        }
    }

    @Override
    public void onUpdateActivatedDevCardProduction(String devCard) {
        if (playerBoard.isMyturn()){
            Platform.runLater(() -> {
                if (sceneLauncher.getProductionsStage() != null)
                    sceneLauncher.getProductionsStage().close();
                sceneLauncher.payResourcesScene();
            });
        }
        else{
            Platform.runLater(()->{
                sceneLauncher.showMessage(playerBoard.getCurrentPlayer() + " has activated a DevCard's production");
            });
        }
    }

    @Override
    public void onUpdateError(String error) {
        Platform.runLater(() -> {
            sceneLauncher.showErrorMessage(error);
        });
    }

    @Override
    public void onUpdateDevCardDeck(String devCard) {
        if (playerBoard.isMyturn()) {
            Platform.runLater(()-> {
                if (sceneLauncher.getActiveBuyDevCardStage() != null)
                    sceneLauncher.getActiveBuyDevCardStage().close();
                sceneLauncher.payResourcesScene();
            });
        }else{
            Platform.runLater(()->{
                sceneLauncher.showMessage(playerBoard.getCurrentPlayer() + " has bought a DevCard");
            });
        }
    }

    @Override
    public void onUpdateFaithMarker() {
        Platform.runLater(()-> {
            if (playerBoard.getMarbleBuffer().isEmpty() && sceneLauncher.getManageMarbleStage() != null)
                sceneLauncher.getManageMarbleStage().close();
            sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
        });
    }

    @Override
    public void onUpdateMarketTray() {
        if (playerBoard.isMyturn()){
            Platform.runLater(()-> sceneLauncher.getExtractionMarbleStage().close());
            Platform.runLater(()-> sceneLauncher.manageMarbleScene());
        }
        else{
            Platform.runLater(()->{
                sceneLauncher.showMessage(playerBoard.getCurrentPlayer() + " has extracted marbles");
            });
        }
    }

    @Override
    public void onUpdateUpdateResources() {
        if (playerBoard.isMyturn()) {
            Platform.runLater(() -> {
                if (sceneLauncher.getPayResourcesStage() != null)
                    sceneLauncher.getPayResourcesStage().close();
                if (sceneLauncher.getProductionsStage() != null)
                    sceneLauncher.getProductionsStage().close();
                sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
            });
        }
    }

    @Override
    public void onUpdateSinglePlayerGame() {
        Platform.runLater(() -> {
            //This exception is caught when the single player is reconnected
            try {
                sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
            }
            catch (ArrayIndexOutOfBoundsException | NullPointerException ignored) {}
            sceneLauncher.showLorenzoTurn();
        });
    }

    @Override
    public void onUpdateSlotDevCards() {
        if (playerBoard.isMyturn())
            Platform.runLater(()-> {
                if (sceneLauncher.getPayResourcesStage() != null)
                    sceneLauncher.getPayResourcesStage().close();
            });
        else{
            Platform.runLater(()-> sceneLauncher.showMessage(playerBoard.getCurrentPlayer() + " has update his SlotDevCards"));
        }
    }

    @Override
    public void onUpdateStateLeaderAction(String leaderCard, boolean state) {
        if (playerBoard.isMyturn()) {
            String decision;
            if (state)
                decision = "activated";
            else decision = "discarded";
            Platform.runLater(() -> sceneLauncher.showMessage("The card has been " + decision));
        }
        else{
            Platform.runLater(()-> sceneLauncher.showMessage(playerBoard.getCurrentPlayer() + " has update one of his LeaderCard"));
        }
    }

    @Override
    public void onUpdateStrongbox() {
        if (playerBoard.isMyturn())
            Platform.runLater(()-> {
                if (sceneLauncher.getProductionsStage() != null)
                    sceneLauncher.getProductionsStage().close();
                sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
            });
        else{
            Platform.runLater(()->{
                sceneLauncher.showMessage(playerBoard.getCurrentPlayer() + " has update his Strongbox");
            });
        }
    }

    @Override
    public void onUpdateWarehouse() {

        if (playerBoard.isMyturn()) {
            Platform.runLater(() -> {
                if (playerBoard.getMarbleBuffer().isEmpty() && sceneLauncher.getManageMarbleStage() != null)
                    sceneLauncher.getManageMarbleStage().close();
                sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
            });
        }

    }

    @Override
    public void onUpdateWinnerMultiplayer() {
        Platform.runLater(() -> {
            sceneLauncher.endGameScene();
        });
    }

    @Override
    public void onUpdateWinnerSinglePlayer() {
        Platform.runLater(() -> {
            sceneLauncher.endGameScene();
        });
    }

    @Override
    public void onDisconnect() {
        clientController.disconnect();
    }

    @Override
    public void onPlayerDisconnect(String name) {
        Platform.runLater(()->sceneLauncher.showMessage("PLAYER " + name +" has left the game."));

    }

    @Override
    public void onResume(String name) {
        String secondMessage;

        if (playerBoard.getNickname().equals(name)) {
            String firstMessage = "Welcome Back " + playerBoard.getNickname() + " !";
            if (playerBoard.getLeaderCards().size() > 2) {
                secondMessage = "You have to Complete Initial Card choice";
                Platform.runLater(() -> sceneLauncher.resumeGameScene(firstMessage, secondMessage));
                if(getPlayerBoard().isMyturn()) Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards()));
                else Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));
            } else {
                if (playerBoard.getCurrentInitialResourcesToChoose() != 0) {
                    secondMessage = "You have to Complete Initial Resources choose";
                    Platform.runLater(() -> sceneLauncher.resumeGameScene(firstMessage, secondMessage));
                    if(getPlayerBoard().isMyturn()) Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialResources()));
                    else Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));
                } else {
                    if (!playerBoard.getMarbleBuffer().isEmpty()) {
                        secondMessage = "You have to manage the extracted Marbles";
                        Platform.runLater(() -> sceneLauncher.resumeGameScene(firstMessage, secondMessage));
                        if(getPlayerBoard().isMyturn()) {
                            Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                            Platform.runLater(() -> sceneLauncher.manageMarbleScene());
                        }
                        else Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));
                    } else {
                        if (playerBoard.getActivedDevCardProd() != null) {
                            secondMessage = "You have to Complete Card Production";
                            Platform.runLater(() -> sceneLauncher.resumeGameScene(firstMessage, secondMessage));
                            if(getPlayerBoard().isMyturn()) {
                                Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                                Platform.runLater(() -> sceneLauncher.payResourcesScene());
                            }
                            else Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));
                        } else {
                            if (playerBoard.getCurrentDevCardToBuy() != null) {
                                secondMessage = "You have to Complete Card Purchase";
                                Platform.runLater(() -> sceneLauncher.resumeGameScene(firstMessage, secondMessage));
                                if(getPlayerBoard().isMyturn()) {
                                    Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                                    Platform.runLater(() -> sceneLauncher.payResourcesScene());
                                }
                                else Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));

                            } else {
                                if(getPlayerBoard().isMyturn()) Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
                                else Platform.runLater(()-> sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers()));
                            }
                        }
                    }
                }
            }
        }
        else {
            Platform.runLater(() -> sceneLauncher.showMessage("PLAYER: " + name + " is now connected"));
        }
    }

    @Override
    public void showMessage(String message) {
        Platform.runLater(() -> {
            sceneLauncher.showMessage(message);
        });
    }

    @Override
    public void showLeaderActionBox() {

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
    public void showOtherPlayer() {
        if (playerBoard.isMyturn()) {
            Platform.runLater(()-> sceneLauncher.getOtherPlayerStage().close());
            if (playerBoard.getOtherPlayer().getLeaderCards().size() > 2)
                Platform.runLater(()-> sceneLauncher.showMessage("The player indicated has to choose yet the Leader cards"));
            else
                Platform.runLater(()-> sceneLauncher.showOtherPlayerBoard());
        }
    }

    @Override
    public void showLorenzoTurn() {
        Platform.runLater(() -> {
            sceneLauncher.showLorenzoTurn();
        });
    }

    @Override
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    @Override
    public void waitforOtherPlayers() {
        Platform.runLater(() -> {
            sceneLauncher.getStage().setScene(sceneLauncher.waitOtherPlayers());
        });
    }

}
