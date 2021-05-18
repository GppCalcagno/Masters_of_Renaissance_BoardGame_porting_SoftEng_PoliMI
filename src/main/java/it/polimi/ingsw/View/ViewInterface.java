package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;

public interface ViewInterface {

    void askServerInfo();

    void endturn(); //fai apparire a schermo che il turno finisce e manda il messaggio

    /* ************************************ LOGIN PHASE ******************************** */
    void askNickname(); //chiede nome

    void askNumPlayer(); //chiede numero

    /* ************************************ INITGAME PHASE ******************************** */
    void GameStart();

    void askChooseLeaderCards(); //mostrra le carte e chie quale delle 4 carte tenere

    /* ************************************ SHOW PHASE ******************************** */
    void showMessage(String message);

    void showLeaderActionBox();
    void showSlotDevCard();

    void showWarehouse();
    void showStrongbox();
    void showWhiteMarbleEffectList();

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
