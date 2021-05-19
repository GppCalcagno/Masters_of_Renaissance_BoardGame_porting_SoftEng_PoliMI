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

    void onUpdateError();

    /* ************************************ LOGIN PHASE ******************************** */
    void onRequestNumPlayer();
    void onUpdateStagegame();
    /* ************************************ INITGAME PHASE ******************************** */
    void onUpdateCurrPlayer();
    void onUpdateItitialLeaderCards();
    /* ************************************ INGAME PHASE ******************************** */







    /* ************************************ SHOW PHASE ******************************** */
    void showMessage(String message);

    void showLeaderActionBox();
    void showSlotDevCard();

    void showWarehouse();
    void showStrongbox();

    void showFaithTrack();
    void showExtraChest();

    void showMarketTray();
    void showMarbleBuffer();
    void showDevCardDeck();


    void showDevCard(String ID);
    void showLeaderAction(String ID);

    //WIP
    void showWinnerandVictoryPoint();
    void showLorenzoTrun();
}
