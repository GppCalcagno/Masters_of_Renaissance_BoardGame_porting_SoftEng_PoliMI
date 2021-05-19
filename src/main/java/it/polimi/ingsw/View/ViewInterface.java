package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;
/**
 * this class is the interface where CLI and GUI take methods
 */
public interface ViewInterface {

    void askServerInfo();
    void askLogin();
    void askNumPlayer();



    /* ************************************ UPDATE ******************************** */
    void onUpdateStartGame();
    void onUpdateCurrPlayer();
    void onUpdateInitialLeaderCards();
    void onUpdateActivatedDevCardProduction();
    void onUpdateError();
    void onUpdateRequestNumPlayers();
    void onUpdateDevCardDeck();
    void onUpdateFaithMarker();
    void onUpdateInfo();
    void onUpdateMarketTray();
    void onUpdatePlayerState();
    void onUpdateUpdateResources();
    void onUpdateSinglePlayerGame();
    void onUpdateSlotDevCards();
    void onUpdateStateLeaderAction();
    void onUpdateStringbox();
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

    //WIP
    void showWinnerandVictoryPoint();
    void showLorenzoTrun();
}
