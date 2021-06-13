package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.Client.PlayerBoard;

import java.util.List;

/**
 * this class is the interface where CLI and GUI take methods. All methods interact with the view and the server
 */
public interface ViewInterface {

    /**
     * this method ask the player the info of the server (if the game is online) as addres and port
     */
    void askServerInfo();

    /**
     * this method asks the player his name
     */
    void askLogin();

    /**
     * this method ask the number of the player
     */
    void askNumPlayer();



    /* ************************************ UPDATE ******************************** */

    /**
     * All these methods receive updates from the server and updates the leaderBoard and the Cli or Gui
     */

    void onUpdateStartGame();
    void onUpdateCurrPlayer();

    /**
     * @param leaderCard fourth leader cards
     */
    void onUpdateInitialLeaderCards(List<String> leaderCard);

    /**
     * @param devCard the card that start the production
     */
    void onUpdateActivatedDevCardProduction(String devCard);

    /**
     * @param error write this string and show it in the view
     */
    void onUpdateError(String error);

    /**
     * @param devCard card bought that have to be removed from the deck
     */
    void onUpdateDevCardDeck(String devCard);
    void onUpdateFaithMarker();
    void onUpdateMarketTray();
    void onUpdateUpdateResources(); // solo current
    void onUpdateSinglePlayerGame(); //lorenzo fa turno e dice che token ha usato
    void onUpdateSlotDevCards(); //solo current

    /**
     * @param leaderCard leader card that change state
     * @param state new state, active or discard
     */
    void onUpdateStateLeaderAction(String leaderCard, boolean state);
    void onUpdateStrongbox();
    void onUpdateWarehouse();
    void onUpdateWinnerMultiplayer();
    void onUpdateWinnerSinglePlayer();

    void onDisconnect();

    /**
     * @param name the name of the player disconnected
     */
    void onPlayerDisconnect(String name);

    /**
     * @param name the name of the player that rejoin the game
     */
    void onResume(String name);



    /* ************************************ SHOW PHASE ******************************** */

    /**
     * All these methods show the player what methods are for. They can be called from the client or directly the
     * from the player during the game
     */

    /**
     * @param message what to be shown
     */
    void showMessage(String message);

    void showLeaderActionBox();
    void showFaithTrack();
    void showExtraChest();
    void showMarketTray();
    void showDevCardDeck();
    void showMarbleBuffer();

    /**
     * @param ID card's id
     */
    void showDevCard(String ID);

    /**
     * @param ID card's id
     */
    void showLeaderAction(String ID);

    void showOtherPlayer();

    void showLorenzoTurn();

    PlayerBoard getPlayerBoard();

    void waitforOtherPlayers();
}
