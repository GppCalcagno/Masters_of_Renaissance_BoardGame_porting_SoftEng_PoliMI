package it.polimi.ingsw.Network.Server.UpdateCreator;

import it.polimi.ingsw.Network.Message.ServerUpdate.*;
import it.polimi.ingsw.Network.Server.UpdateSender.SenderUpdateInterface;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SlotDevCards;
import it.polimi.ingsw.model.player.WarehouseDepots;
import it.polimi.ingsw.model.producible.Resources;
import it.polimi.ingsw.model.singleplayer.token.Tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaSerUpdateCreator implements UpdateCreator {
    SenderUpdateInterface sender;

    public JavaSerUpdateCreator(SenderUpdateInterface sender) {
        this.sender=sender;
    }

    @Override
    public void onUpdateError(String name,String message) {
        UpdateError updateError = new UpdateError("server", message);
        sender.sendtoPlayer(name, updateError);
    }

    @Override
    public void onUpdateError(String message){
        UpdateError updateError = new UpdateError("server", message);
        sender.sendBroadcastMessage(updateError);
    }

    @Override
    public void onUpdateInitialLeaderCards (Player player, List<LeaderAction> leaderActionList) {
        List<String> stringList = leaderBoxConvert(leaderActionList);
        UpdateInitialLeaderCards message = new UpdateInitialLeaderCards(player.getNickname(), stringList);
        sender.sendtoPlayer(player.getNickname(), message);
    }

    @Override
    public void onUpdateStartGame(DevelopmentCard[][][] developmentCardDeck, List<Player> playersList , Marbles[][] marketTray, Marbles remainingMarble){
        String[][][] stringdevCardDeck =devCardDeckConvert(developmentCardDeck);

        String [][] stringmarketTray = marketTrayConvert(marketTray);

        String stringremainingMarble= remainingMarble.toString();

        Map<String,List<String>> stringleaderCardsToChoose= new HashMap<>();
        List<String> stringPlayerList= new ArrayList<>();
        for(Player p: playersList){
            List<String> stringLeaderCard= leaderBoxConvert(p.getLeaderActionBox());
            stringleaderCardsToChoose.put(p.getNickname(),stringLeaderCard);
            stringPlayerList.add(p.getNickname());
        }

        UpdateStartGame message= new UpdateStartGame(stringleaderCardsToChoose,stringPlayerList,stringdevCardDeck,stringmarketTray,stringremainingMarble);
        sender.sendBroadcastMessage(message);
    }

    @Override
    public void onUpdateCurrentPlayer(Player currPlayer){
        UpdateCurrPlayer message= new UpdateCurrPlayer(currPlayer.getNickname());
        sender.sendBroadcastMessage(message);
    }

    @Override
    public void onUpdateFaithMarker(Player player, List<Player> playerList, boolean removeMarblefromBuffer, int blackcrosstoken){
        Map<String, Integer> playersPosition= new HashMap<>();
        Map<String, boolean[]> playersPopFavoriteTile= new HashMap<>();

        for (Player p: playerList){
            playersPosition.put(p.getNickname(),p.getFaithMarker());
            playersPopFavoriteTile.put(p.getNickname(),p.getPopsfavortiles());
        }

        UpdateFaithMarker message = new UpdateFaithMarker(player.getNickname(), playersPosition, playersPopFavoriteTile, removeMarblefromBuffer, blackcrosstoken);
        sender.sendBroadcastMessage(message);
    }

    @Override
    public void onUpdateMarketTray(Player player, char direction, int num){
        UpdateMarketTray message= new UpdateMarketTray(player.getNickname(),direction, num);
        sender.sendBroadcastMessage(message);
    }

   @Override
    public void onUpdateDevCardDeck(Player player, DevelopmentCard card){
        UpdateDevCardDeck message= new UpdateDevCardDeck(player.getNickname(),card.getID());
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateActivatedDevCardProduction(Player player, String ID) {
        sender.sendBroadcastMessage(new UpdateActivatedDevCardProduction(player.getNickname(), ID));
    }

    @Override
    public void onUpdatePlayerDisconnected(Player player){
        UpdatePlayerDisconnected message= new UpdatePlayerDisconnected(player.getNickname());
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateResources(Player player){
        WarehouseDepots warehouseDepots= player.getWarehouse();
        List<ExtraChest> extraChests = player.getWarehouse().getLeaderCardEffect();
        Map<String,Integer> strongbox= player.getStrongbox().getChest();
        String[][] stringWarehouseDepots= warehouseConvert(warehouseDepots);
        Map<String,Integer> stringExtrachest = extrachestConvert(extraChests);

        UpdateResources message= new UpdateResources(player.getNickname(),stringWarehouseDepots,stringExtrachest,strongbox);
        sender.sendtoPlayer(player.getNickname(),message);

    }

   @Override
    public void onUpdateSinglePlayer(int blackCrossToken, DevelopmentCard[][][] devCardsDeck, Tokens tokens, String tokenColor){
        UpdateSinglePlayerGame message= new UpdateSinglePlayerGame(blackCrossToken, tokens.getID(), devCardDeckConvert(devCardsDeck), tokenColor);
       sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateSlotDevCard(Player player, DevelopmentCard card, int column){
        UpdateSlotDevCards message= new UpdateSlotDevCards(player.getNickname(),card.getID(),column);
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateLeaderCard(Player player, String IDcard, boolean active){
        UpdateStateLeaderAction message= new UpdateStateLeaderAction(player.getNickname(), IDcard, active);
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateStrongBox(Player player){
        UpdateStrongbox message= new UpdateStrongbox(player.getNickname(), player.getStrongbox().getChest());
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateWarehouse(Player player, boolean removeMarble){
        WarehouseDepots warehouseDepots= player.getWarehouse();
        List<ExtraChest> extraChests= player.getWarehouse().getLeaderCardEffect();
        String[][] stringWarehouseDepots= warehouseConvert(warehouseDepots);
        Map<String,Integer> stringExtrachests = extrachestConvert(extraChests);

        UpdateWarehouse message= new UpdateWarehouse(player.getNickname(), stringWarehouseDepots,stringExtrachests,removeMarble);
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateWinnerMultiplayer(Player winner, List<Player> playerList){
        Map<String, Integer> playersPoints = new HashMap<>();
        for (Player p: playerList){
            playersPoints.put(p.getNickname(),p.getVictoryPoints());
        }
        UpdateWinnerMultiplayer message = new UpdateWinnerMultiplayer(winner.getNickname(),playersPoints);
        sender.sendBroadcastMessage(message);

    }

    @Override
    public void onUpdateWinnerSinglePlayer(boolean winner, int finalpoint){
        UpdateWinnerSinglePlayer message= new UpdateWinnerSinglePlayer(winner, finalpoint);
        sender.sendBroadcastMessage(message);
    }

    @Override
    public void onUpdateGameFinished(){
        sender.sendBroadcastMessage(new UpdateGameFinished());
    }

    @Override
    public void onRequestDisconnect(String name){
        UpdateRequestDisconnect updateRequestDisconnect = new UpdateRequestDisconnect(name);
        sender.sendtoPlayer(name, updateRequestDisconnect);
    }

   @Override
    public void onRequestResume(List<Player> playerList, Player player, Player currentPlayer, DevelopmentCard[][][] devCardsDeck,
                                Marbles[][] marketTray, Marbles remainingMarble, int blackCrossToken){
       //convert Player list and FaithTrack date
       List<String> stringPlayerList= new ArrayList<>();
       Map<String, Integer> stringPlayersPosition= new HashMap<>();
       Map<String, boolean[]> stringPlayersPopFavoriteTile= new HashMap<>();

       for(Player p: playerList ){
           stringPlayerList.add(p.getNickname());
           stringPlayersPosition.put(p.getNickname(),p.getFaithMarker());
           stringPlayersPopFavoriteTile.put(p.getNickname(),p.getPopsfavortiles());
       }

       //convert MarbleBuffer
       List<String> stringMarbleBuffer= new ArrayList<>();
       for(Marbles marbles: player.getWarehouse().getBuffer()){
           stringMarbleBuffer.add(marbles.toString());
       }
        //convertLeaderCard
       List<String> stringLeadercard= leaderBoxConvert(player.getLeaderActionBox());

       String stringCurrentDevCardToBuy = null;
       String stringCurrentDevCardToProduce = null;

       if( player.getCurrentDevCardToBuy()!=null)
           stringCurrentDevCardToBuy=player.getCurrentDevCardToBuy().getID();
       if(player.getCurrentDevCardToProduce()!=null)
           stringCurrentDevCardToProduce=player.getCurrentDevCardToProduce().getID();

       UpdateReconnect message= new UpdateReconnect(player.getNickname(),stringPlayerList, currentPlayer.getNickname(),
               devCardDeckConvert(devCardsDeck),marketTrayConvert(marketTray),remainingMarble.toString(),stringMarbleBuffer,warehouseConvert(player.getWarehouse()),
               extrachestConvert(player.getWarehouse().getLeaderCardEffect()),player.getStrongbox().getChest(),stringPlayersPosition,stringPlayersPopFavoriteTile, blackCrossToken,
               stringLeadercard, slotDevCardConvert(player.getSlotDevCards()),stringCurrentDevCardToBuy,stringCurrentDevCardToProduce,player.getInitialResources());
       sender.sendBroadcastMessage(message);
    }


    @Override
    public void onWaitingForOtherPlayer(String name){
        sender.sendtoPlayer(name, new UpdateWaitingForOtherPlayer());
    }

    @Override
    public void onWaitingForOtherPlayer(){
        sender.sendBroadcastMessage(new UpdateWaitingForOtherPlayer());
    }

    @Override
    public void onRequestNumPlayer(String name){
        sender.sendtoPlayer(name,new UpdateRequestNumPlayers());
    }

    @Override
    public void onReqOtherPlayer(Player currentPlayer, Player player){
        String othername= player.getNickname();
        String[][] warehouse= warehouseConvert(player.getWarehouse());
        Map<String,Integer> extrachest=extrachestConvert(player.getWarehouse().getLeaderCardEffect());
        Map<String,Integer> strongbox=player.getStrongbox().getChest();
        String [][] slotDevCard= slotDevCardConvert(player.getSlotDevCards());

        List<String> leadercards= leaderBoxConvert(player.getLeaderActionBox());
        UpdateOtherPlayer message= new UpdateOtherPlayer("server",othername,warehouse,extrachest,strongbox,leadercards,slotDevCard);
        sender.sendtoPlayer(currentPlayer.getNickname(),message);
    }


    private String[][] marketTrayConvert(Marbles[][] marketTray){
        String [][] stringmarketTray = new String[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                stringmarketTray[i][j] = marketTray[i][j].toString();
            }
        }
        return stringmarketTray;
    }

    private String[][] slotDevCardConvert(SlotDevCards slotDevCards){
        String[][] stringSlotDevCard= new String[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(slotDevCards.getDevCards(i,j)!=null){
                    stringSlotDevCard[i][j]= slotDevCards.getDevCards(i,j).getID();
                }
            }
        }
        return stringSlotDevCard;
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

    private List<String> leaderBoxConvert(List<LeaderAction> leaderActionBox){
        List<String> leadercards= new ArrayList<>();
        for (LeaderAction card: leaderActionBox){
            leadercards.add(card.getID());
        }
        return leadercards;
    }
}
