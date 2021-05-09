package it.polimi.ingsw.View;

public interface view {

    void askServerInfo();

    /* ************************************ LOGIN PHASE ******************************** */
    void askNickname(); //chiede nome

    void askNumPlayer(); //chiede numero

    /* ************************************ INITGAME PHASE ******************************** */

    void askChooseLeaderCards(); //chiede quale delle 4 carte tenere

    void askChooseResourcesFirstTurn(); //chiede quale risorse prendere

    /* ************************************ INGAME PHASE ******************************** */


    void askChooseAction(); //chiede quale delle 4 cose fare
    void askActiveLeaderAction(); //ask 1 turno

    /* *** Market Turn *** */
    void askTakeMarble(); //ask 2 turno
    void askAfterTakeMarble();// chiede se vuole fare exchange o fare gestire biglie
    void askExchange();
    void askAddDiscardMarble();

    /* *** Producion and Purchase Turn *** */
    void askSelectDevCard();//ask 3 turno
    void askActiveProducition();
    void askChooseResources();
    void askInsertCard();
    void askEndProduction();



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
