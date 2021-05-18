package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.Message.UpdateMesssage.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Strongbox;
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

    public void onUpdateStartGame(DevelopmentCard[][][] developmentCardDeck, List<Player> playersList , Marbles[][] marketTray, Marbles remainingMarble, Map<String,List<LeaderAction>> leaderCardsToChoose){
        String[][][] stringdevCardDeck = new String[3][4][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    stringdevCardDeck[i][j][k] = developmentCardDeck[i][j][k].getID();
                }
            }
        }

        List<String> stringPlayerList= new ArrayList<>();
        for(Player p: playersList){
            stringPlayerList.add(p.getNickname());
        }
        String [][] stringmarketTray = new String[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                stringmarketTray[i][j] = marketTray[i][j].toString();
            }
        }
        String stringremainingMarble= remainingMarble.toString();

        Map<String,List<String>> stringleaderCardsToChoose= new HashMap<>();
        for(String p: leaderCardsToChoose.keySet()){
            List<String> stringLeaderCard= new ArrayList<>();
            for (LeaderAction card: leaderCardsToChoose.get(p)){
                stringLeaderCard.add(card.getID());
            }
            stringleaderCardsToChoose.put(p,stringLeaderCard);
        }

        MessageStartGame message= new MessageStartGame(stringleaderCardsToChoose,stringPlayerList,stringdevCardDeck,stringmarketTray,stringremainingMarble);
        server.sendBroadcastMessage(message);
    }

    public void onUpdateCurrentPlayer(Player currPlayer){
        MessageUpdateCurrPlayer message= new MessageUpdateCurrPlayer(currPlayer.getNickname());
        server.sendBroadcastMessage(message);
    }

    public void onUpdateFaithMarker(List<Player> playerList){
        Map<String, Integer> playersPosition= new HashMap<>();
        Map<String, boolean[]> playersPopFavoriteTile= new HashMap<>();

        for (Player p: playerList){
            playersPosition.put(p.getNickname(),p.getFaithMarker());
            playersPopFavoriteTile.put(p.getNickname(),p.getPopsfavortiles());
        }

        MessageUpdateFaithMarker message = new MessageUpdateFaithMarker(playersPosition, playersPopFavoriteTile);
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



    public void onUpdatePlayerState(Player player, boolean state){
        MessageUpdatePlayerState message= new MessageUpdatePlayerState(player.getNickname(),state);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateResources(Player player, WarehouseDepots warehouseDepots, List<ExtraChest> extraChests, Map<String,Integer> strongbox){
        String[][] stringWarehouseDepots= warehouseConvert(warehouseDepots);
        Map<String,Integer> stringExtrachest = extrachestConvert(extraChests);

        MessageUpdateResources message= new MessageUpdateResources(player.getNickname(),stringWarehouseDepots,stringExtrachest,strongbox);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateSinglePlayer(Tokens token, int blackCrossToken, Tokens[] tokensHeap){
        String[] StringtokensHeap= new String[7];
        for(int i=0;i<7;i++) StringtokensHeap[i]=tokensHeap[i].getID();

        MessageUpdateSinglePlayerGame message= new MessageUpdateSinglePlayerGame(StringtokensHeap,blackCrossToken,token.getID());
        server.sendBroadcastMessage(message);

    }

    public void onUpdateSlotDevCard(Player player, DevelopmentCard card, int column){
        MessageUpdateSlotDevCards message= new MessageUpdateSlotDevCards(player.getNickname(),card.getID(),column);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateLeaderCard(Player player, LeaderAction card, boolean active){
        MessageUpdateStateLeaderAction message= new MessageUpdateStateLeaderAction(player.getNickname(), card.getID(),active);
        server.sendBroadcastMessage(message);

    }

    public void onUpdateStrongBox(Player player, Strongbox strongbox){
        MessageUpdateStrongbox message= new MessageUpdateStrongbox(player.getNickname(), strongbox.getChest());
        server.sendBroadcastMessage(message);

    }

    public void onUpdateWarehouse(Player player, WarehouseDepots warehouseDepots, List<ExtraChest> extraChests){
        String[][] stringWarehouseDepots= warehouseConvert(warehouseDepots);
        Map<String,Integer> stringExtrachests = extrachestConvert(extraChests);

        MessageUpdateWarehouse message= new MessageUpdateWarehouse(player.getNickname(), stringWarehouseDepots,stringExtrachests);
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

    public void  onUpdateWinnerSinglePlayer(boolean winner, int finalpoint){
        MessageUpdateWinnerSinglePlayer message= new MessageUpdateWinnerSinglePlayer(winner, finalpoint);
        server.sendBroadcastMessage(message);
    }

    //REQUESTNUMPLAYER????


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
}
