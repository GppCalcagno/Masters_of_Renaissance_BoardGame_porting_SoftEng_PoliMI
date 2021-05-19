package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;

import java.util.List;

/**
 * this class is the interface where CLI and GUI take methods
 */
public interface ViewInterface {

    void askServerInfo();
    void askLogin();
    void askNumPlayer();
    void start();



    /* ************************************ UPDATE ******************************** */
    void onUpdateStartGame();
    void onUpdateCurrPlayer();
    void onUpdateInitialLeaderCards(List<String> leaderCard);
    void onUpdateActivatedDevCardProduction(String devCard);
    void onUpdateError(String error);
    void onUpdateRequestNumPlayers();
    void onUpdateDevCardDeck(String devCard);
    void onUpdateFaithMarker();
    void onUpdateInfo(String info);
    void onUpdateMarketTray();
    void onUpdatePlayerState(String nickname, boolean state);
    void onUpdateUpdateResources(); // solo current
    void onUpdateSinglePlayerGame(); //lorenzo fa turno e dice che token ha usato
    void onUpdateSlotDevCards(); //solo current
    void onUpdateStateLeaderAction(String leaderCard, boolean state);
    void onUpdateStrongbox();
    void onUpdateWarehouse();
    void onUpdateWinnerMultiplayer();
    void onUpdateWinnerSinglePlayer();

    /* ************************************ INITGAME PHASE ******************************** */
    /* ************************************ INGAME PHASE ******************************** */
    void inputFromPlayer();
    /* ************************************ SHOW PHASE ******************************** */
    void showMessage(String message);

    void showLeaderActionBox();
    void showSlotDevCard();
    void showWarehouse();
    void showStrongbox();
    void showFaithTrack();
    void showExtraChest();
    void showMarketTray();
    void showDevCardDeck();
    void showDevCard(String ID);
    void showLeaderAction(String ID);

    void showLorenzoTrun();
}
