package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessagePing;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Network.Message.UpdateMesssage.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.WarehouseDepots;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCreator {
    Server server;

    public UpdateCreator(Server server) {
        this.server = server;
    }

    public void onUpdateError(String message) {
        MessageError messageError = new MessageError("server", message);
        server.sendBroadcastMessage(messageError);
    }

    public void onUpdateInitialLeaderCards (Player player, List<LeaderAction> leaderActionList) {
        List<String> stringList = new ArrayList<>();
        for (LeaderAction l : leaderActionList) {
            stringList.add(l.getID());
        }
        MessageUpdateInitialLeaderCards message = new MessageUpdateInitialLeaderCards(player.getNickname(), stringList);
        server.sendtoPlayer(player.getNickname(), message);
    }

    public void onUpdateStartGame(DevelopmentCard[][][] developmentCardDeck, List<Player> playersList , Marbles[][] marketTray, Marbles remainingMarble){
        String[][][] stringdevCardDeck =devCardDeckConvert(developmentCardDeck);

        String [][] stringmarketTray = new String[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                stringmarketTray[i][j] = marketTray[i][j].toString();
            }
        }
        String stringremainingMarble= remainingMarble.toString();

        Map<String,List<String>> stringleaderCardsToChoose= new HashMap<>();
        List<String> stringPlayerList= new ArrayList<>();
        for(Player p: playersList){
            List<String> stringLeaderCard= new ArrayList<>();
            for (LeaderAction card: p.getLeaderActionBox()){
                stringLeaderCard.add(card.getID());
            }
            stringleaderCardsToChoose.put(p.getNickname(),stringLeaderCard);
            stringPlayerList.add(p.getNickname());
        }

        MessageStartGame message= new MessageStartGame(stringleaderCardsToChoose,stringPlayerList,stringdevCardDeck,stringmarketTray,stringremainingMarble);
        server.sendBroadcastMessage(message);
    }

    public void onUpdateCurrentPlayer(Player currPlayer){
        MessageUpdateCurrPlayer message= new MessageUpdateCurrPlayer(currPlayer.getNickname());
        server.sendBroadcastMessage(message);
    }

    public void onUpdateFaithMarker(Player player, List<Player> playerList, boolean removeMarblefromBuffer){
        Map<String, Integer> playersPosition= new HashMap<>();
        Map<String, boolean[]> playersPopFavoriteTile= new HashMap<>();

        for (Player p: playerList){
            playersPosition.put(p.getNickname(),p.getFaithMarker());
            playersPopFavoriteTile.put(p.getNickname(),p.getPopsfavortiles());
        }

        MessageUpdateFaithMarker message = new MessageUpdateFaithMarker(player.getNickname(), playersPosition, playersPopFavoriteTile, removeMarblefromBuffer);
        server.sendBroadcastMessage(message);
    }

    public void onUpdateMarketTray(Player player, char direction, int num){
        MessageUpdateMarketTray message= new MessageUpdateMarketTray(player.getNickname(),direction, num);
        server.sendBroadcastMessage(message);
    }

    public void onUpdateDevCardDeck(Player player, DevelopmentCard card){
        MessageUpdateDevCardDeck message= new MessageUpdateDevCardDeck(player.getNickname(),card.getID());
        server.sendBroadcastMessage(message);

    }

    public void onUpdateActivatedDevCardProduction(Player player, String ID) {
        server.sendBroadcastMessage(new MessageActivatedDevCardProduction(player.getNickname(), ID));
    }

    public void onUpdatePlayerState(Player player, boolean state){
        MessageUpdatePlayerState message= new MessageUpdatePlayerState(player.getNickname(),state);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateResources(Player player){
        WarehouseDepots warehouseDepots= player.getWarehouse();
        List<ExtraChest> extraChests = player.getWarehouse().getLeaderCardEffect();
        Map<String,Integer> strongbox= player.getStrongbox().getChest();
        String[][] stringWarehouseDepots= warehouseConvert(warehouseDepots);
        Map<String,Integer> stringExtrachest = extrachestConvert(extraChests);

        MessageUpdateResources message= new MessageUpdateResources(player.getNickname(),stringWarehouseDepots,stringExtrachest,strongbox);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateSinglePlayer(int blackCrossToken, DevelopmentCard[][][] devCardsDeck, Tokens tokens){
        MessageUpdateSinglePlayerGame message= new MessageUpdateSinglePlayerGame(blackCrossToken, tokens.getID(), devCardDeckConvert(devCardsDeck));
        server.sendBroadcastMessage(message);

    }

    public void onUpdateSlotDevCard(Player player, DevelopmentCard card, int column){
        MessageUpdateSlotDevCards message= new MessageUpdateSlotDevCards(player.getNickname(),card.getID(),column);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateLeaderCard(Player player, String IDcard, boolean active){
        MessageUpdateStateLeaderAction message= new MessageUpdateStateLeaderAction(player.getNickname(), IDcard, active);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateStrongBox(Player player){
        MessageUpdateStrongbox message= new MessageUpdateStrongbox(player.getNickname(), player.getStrongbox().getChest());
        server.sendBroadcastMessage(message);

    }

    public void onUpdateWarehouse(Player player, boolean removeMarble){
        WarehouseDepots warehouseDepots= player.getWarehouse();
        List<ExtraChest> extraChests= player.getWarehouse().getLeaderCardEffect();
        String[][] stringWarehouseDepots= warehouseConvert(warehouseDepots);
        Map<String,Integer> stringExtrachests = extrachestConvert(extraChests);

        MessageUpdateWarehouse message= new MessageUpdateWarehouse(player.getNickname(), stringWarehouseDepots,stringExtrachests,removeMarble);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateWinnerMultiplayer(Player winner, List<Player> playerList){
        Map<String, Integer> playersPoints = new HashMap<>();
        for (Player p: playerList){
            playersPoints.put(p.getNickname(),p.getVictoryPoints());
        }
        MessageUpdateWinnerMultiplayer message = new MessageUpdateWinnerMultiplayer(winner.getNickname(),playersPoints);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateWinnerSinglePlayer(boolean winner, int finalpoint){
        MessageUpdateWinnerSinglePlayer message= new MessageUpdateWinnerSinglePlayer(winner, finalpoint);
        server.sendBroadcastMessage(message);
    }

    public void onUpdateGameFinished(){
        server.sendBroadcastMessage(new MessageGameFinished());
    }



    /**
     * this method is used to convert Warehouse Version
     * @param warehouseDepots Warehouse Model Version
     * @return Warehouse Client Version
     */
    private String[][] warehouseConvert(WarehouseDepots warehouseDepots){
        Resources[][] oldwarehouse=warehouseDepots.getWarehouse();
        String[][] warehouse = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (oldwarehouse[i][j] == null)
                    warehouse[i][j] = null;
                else
                    warehouse[i][j] = oldwarehouse[i][j].toString();
            }
        }
        return warehouse;
    }

    /**
     * this method is used to convert extraChests Version
     * @param extraChests extraChests Model Version
     * @return extraChests Client Version
     */
    private Map<String,Integer> extrachestConvert( List<ExtraChest> extraChests){
        Map<String,Integer> result= new HashMap<>();
        for(ExtraChest chest: extraChests){
            result.put(chest.getResources().toString(),chest.getnum());
        }
        return result;
    }

    /**
     * this method is used to convert DevcardDeck Version
     * @param developmentCardDeck DevcardDeck Model version
     * @return DevcardDeck client version
     */
    private String[][][] devCardDeckConvert(DevelopmentCard[][][] developmentCardDeck){
        String[][][] stringdevCardDeck = new String[3][4][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if(developmentCardDeck[i][j][k]!=null)
                        stringdevCardDeck[i][j][k] = developmentCardDeck[i][j][k].getID();
                    else
                        stringdevCardDeck[i][j][k]=null;
                }
            }
        }
        return stringdevCardDeck;
    }



}
