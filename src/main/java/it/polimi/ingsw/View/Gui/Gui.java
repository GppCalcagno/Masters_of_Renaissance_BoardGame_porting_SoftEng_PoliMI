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
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards());
            });
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn");
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
                    sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                });
            }
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
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
                sceneLauncher.payResourcesScene();
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
            Platform.runLater(()-> sceneLauncher.payResourcesScene());
        }
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
        if (playerBoard.isMyturn()) {
            sceneLauncher.getPayResourcesStage().close();
            Platform.runLater(() -> sceneLauncher.getStage().setScene(sceneLauncher.mainboard()));
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
            Platform.runLater(()-> sceneLauncher.getPayResourcesStage().close());
    }

    @Override
    public void onUpdateStateLeaderAction(String leaderCard, boolean state) {

    }

    @Override
    public void onUpdateStrongbox() {
        if (playerBoard.isMyturn())
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
                sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn");
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
        // when the server asks to disconnect
        clientController.disconnect();
    }

    @Override
    public void onPlayerDisconnect(String name) {
        //when a player logs out

    }

    @Override
    public void onResume(String name) {
        String firstMessage;
        String secondMessage;

        if(playerBoard.getNickname().equals(name)){
            firstMessage = "Welcome Back " + playerBoard.getNickname() + " !";
            if(!playerBoard.getMarbleBuffer().isEmpty()){
                Platform.runLater(() -> {
                    sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                });
                secondMessage = "You have to manage the extracted Marbles";
                Platform.runLater(() -> {
                    sceneLauncher.resumeGameScene(firstMessage, secondMessage);
                });
            }
            else {
                if(playerBoard.getLeaderCards().size()>2 ){
                    secondMessage = "You have to Complete Initial Card choice";
                    Platform.runLater(() -> {
                        sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialLeaderCards());
                    });
                    Platform.runLater(() -> {
                        sceneLauncher.resumeGameScene(firstMessage, secondMessage);
                    });
                }
                else {
                    if(playerBoard.getCurrentInitialResourcesToChoose()!=0){
                        secondMessage = "You have to Complete Initial Resources choose";
                        Platform.runLater(() -> {
                            sceneLauncher.getStage().setScene(sceneLauncher.chooseInitialResources());
                        });
                        Platform.runLater(() -> {
                            sceneLauncher.resumeGameScene(firstMessage, secondMessage);
                        });
                    }
                    else {
                        Platform.runLater(() -> {
                            sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                        });
                        if (playerBoard.getActivedDevCardProd() != null) {
                            secondMessage = "You have to Complete Card Production";
                            Platform.runLater(() -> {
                                sceneLauncher.devCardProductionScene(sceneLauncher.getPayResourcesStage());
                            });
                            Platform.runLater(() -> {
                                sceneLauncher.resumeGameScene(firstMessage, secondMessage);
                            });
                        } else {
                            if (playerBoard.getCurrentDevCardToBuy() != null) {
                                secondMessage = "You have to Complete Card Purchase";
                                Platform.runLater(() -> {
                                    sceneLauncher.payResourcesScene();
                                });
                                Platform.runLater(() -> {
                                    sceneLauncher.resumeGameScene(firstMessage, secondMessage);
                                });
                            }
                        }
                    }
                }
            }
            if(!playerBoard.isMyturn()) {
                Platform.runLater(() -> {
                    sceneLauncher.getStage().setScene(sceneLauncher.mainboard());
                });
                Platform.runLater(() -> {
                    sceneLauncher.showMessage("It's " + playerBoard.getCurrentPlayer() + "'s turn");
                });
            }

            //inThread.start();
        }
        else {
            Platform.runLater(() -> {
                sceneLauncher.showMessage("PLAYER: " + name + " is now connected");
            });
        }
    }

    @Override
    public void showMessage(String message) {
        Platform.runLater(() -> {
            sceneLauncher.showMessage("Wait for other players");
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
    public void showOtherPlayer() {

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

    /*@Override
    public ClientController getController() {
        return clientController;
    }

*/
}
