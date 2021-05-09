package it.polimi.ingsw.View;

public interface view {

    /* ************************************ LOGIN PHASE ******************************** */
    void askNickname();

    void askNumPlayer();

    /* ************************************ INITGAME PHASE ******************************** */

    void askChooseLeaderCards();

    void askChooseResourcesFirstTurn();

    /* ************************************ INGAME PHASE ******************************** */


    void askChooseAction(); //comprende i 3 turni più le LeaderAction
    void askActiveLeaderAction();

    /* *** Market Turn *** */
    void askTakeMarble();
    void askExchange();
    void askAddDiscardMarble();

    /* *** Producion and Purchase Turn *** */
    void askSelectDevCard();
    void askActiveProducition();
    void askChooseResources();
    void askInsertCard();



    /* ************************************ SHOW PHASE ******************************** */

    void showMessage(String message);

    void showLeaderActionBox();
    void showSlotDevCard();
    void showWarehouse();
    void showStrongbox();
    void showFaithTrack();

    void showMarketTray();
    void showDevCardDeck();

    void showDevCard(String ID);
    void showLeaderAction(String ID);

    void showPlayerState(String name);

    //WIP
    void showWinner();
    void showVictoryPoint();



}
