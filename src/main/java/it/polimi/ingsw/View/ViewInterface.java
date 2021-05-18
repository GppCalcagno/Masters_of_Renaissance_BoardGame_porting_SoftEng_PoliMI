package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;

public interface ViewInterface {

    /**
     * this class is the interface where CLI and GUI take methods
     */

    void askServerInfo();

    /* ************************************ LOGIN PHASE ******************************** */
    void askNickname(); //chiede nome

    void askNumPlayer(); //chiede numero

    void inputFromPlayer();
    /* ************************************ INITGAME PHASE ******************************** */

    void gameStart();

    void askChooseLeaderCards(); //mostrra le carte e chie quale delle 4 carte tenere

    void doAction();
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
    void showPlayerState(String name);

    //WIP
    void showWinnerandVictoryPoint();
    void showLorenzoTrun();
}
