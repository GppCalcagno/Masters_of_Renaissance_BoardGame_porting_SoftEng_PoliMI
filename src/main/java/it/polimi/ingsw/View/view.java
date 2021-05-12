package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.PlayerBoard;

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

    void askChooseResourcesFirstTurn();

    void askChooseAction();

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
    void askSelectTrasformationWhiteMarble();

    /* *** Purchase Card Turn *** */
    void askSelectDevCard();//ask 3 turno
    void askChooseResourcesPurchaseDevCard();
    void askInsertCard();


    /* *** Producion  Turn *** */
    void askProductionType();
    /*  0 -> askChooseResourcesBaseProduction
        1 -> askActiveProductionDevCard
        2 -> ActiveLeaderCardProduction
        3 -> End Production
     */

    void askChooseResourcesBaseProduction();
    void askChosenResourceBaseProduction();

    void askActiveProductionDevCard();
    void askChooseResourcesDevCardProduction();

    void askActiveLeaderCardProduction();

    void askEndTurnActiveLeaderCard();//chiede se finire il turno o attivare carta leader


    void askUpdateStateLeaderAction();

    /* ************************************ SHOW PHASE ******************************** */

    void showMessage(String message);

    void showLeaderActionBox();
    void showSlotDevCard();

    void showWarehouse();
    void showStrongbox();
    void showProductionBuffer();

    void showFaithTrack();
    void showExtraChest();

    void showMarketTray();
    void showDevCardDeck();


    void showDevCard(String ID);
    void showLeaderAction(String ID);
    void showPlayerState(String name);

    //WIP
    void showWinner();
    void showVictoryPoint();



}
