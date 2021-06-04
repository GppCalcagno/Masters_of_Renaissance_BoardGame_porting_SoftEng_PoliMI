package it.polimi.ingsw.Network.Server.UpdateCreator;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.util.List;

public interface UpdateCreator {


    void onUpdateError(String name, String message);

    void onUpdateError(String message);

    void onUpdateInitialLeaderCards(Player player, List<LeaderAction> leaderActionList);

    void onUpdateStartGame(DevelopmentCard[][][] developmentCardDeck, List<Player> playersList , Marbles[][] marketTray, Marbles remainingMarble);

    void onUpdateCurrentPlayer(Player currPlayer);

    void onUpdateFaithMarker(Player player, List<Player> playerList, boolean removeMarblefromBuffer, int blackcrosstoken);

    void onUpdateMarketTray(Player player, char direction, int num);

    void onUpdateDevCardDeck(Player player, DevelopmentCard card);

    void onUpdateActivatedDevCardProduction(Player player, String ID);

    void onUpdatePlayerDisconnected(Player player);

    void onUpdateResources(Player player);

    void onUpdateSinglePlayer(int blackCrossToken, DevelopmentCard[][][] devCardsDeck, Tokens tokens, String tokenColor);

    void onUpdateSlotDevCard(Player player, DevelopmentCard card, int column);

    void onUpdateLeaderCard(Player player, String IDcard, boolean active);

    void onUpdateStrongBox(Player player);

    void onUpdateWarehouse(Player player, boolean removeMarble);

    void onUpdateWinnerMultiplayer(Player winner, List<Player> playerList);

    void onUpdateWinnerSinglePlayer(boolean winner, int finalpoint);

    void onUpdateGameFinished();

    void onRequestDisconnect(String name);

    void onRequestNumPlayer(String name);


    void onRequestResume(List<Player> playerList, Player player, Player CurrentPlayer, DevelopmentCard[][][] devCardsDeck,
                         Marbles[][] marketTray, Marbles remainingMarble, int blackCrossToken);

    void onWaitingForOtherPlayer(String name);

    void onWaitingForOtherPlayer();

    void onReqOtherPlayer(Player currentPlayer, Player player);
}
