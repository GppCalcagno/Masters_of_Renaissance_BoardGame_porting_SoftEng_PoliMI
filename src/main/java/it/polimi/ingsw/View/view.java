package it.polimi.ingsw.View;

public interface view {

    void askServerInfo();

    void endturn(); //fai apparire a schermo che il turno finisce e manda il messaggio
    /* ************************************ LOGIN PHASE ******************************** */
    void askNickname(); //chiede nome

    void askNumPlayer(); //chiede numero

    /* ************************************ INITGAME PHASE ******************************** */

    void askChooseLeaderCards(); //mostrra le carte e chie quale delle 4 carte tenere

    void askChooseResourcesFirstTurn(int num); //chiede quale risorse prendere in base al numero
    //todo controllare che il metodo sia giusto

    /* ************************************ INGAME PHASE ******************************** */


    void askChooseTurn(); //chiede quale delle 5 cose fare
        /*  0->extractMarble
             1-> selectDevCard
            2->chooseResForBaseprod
            3->ActiveProdDevCard
            4->activeprodLeaderCard
            5->Update stateLeadercard
        */

    void askActiveLeaderAction(); //ask 1 turno

    /* *** Market Turn *** */
    void askExtractMarble(); //ask 2 turno


    void askAfterTakeMarble();// chiede se vuole fare exchange o fare gestire biglie o impostare biglia
        /*  seleziona una tra le seguenti mosse:
           0->askExchange;
           1->askAddDiscardMarble;
           2-> askSetDefaulMarbleLeaderEffect;
        */

    void askExchange();
    void askAddDiscardMarble();
    void askSetDefaulMarbleLeaderEffect();

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
