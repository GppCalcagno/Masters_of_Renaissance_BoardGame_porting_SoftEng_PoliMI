package it.polimi.ingsw.Network.Server;

import it.polimi.ingsw.Network.message.UpdateMesssage.MessageStartGame;
import it.polimi.ingsw.Network.message.UpdateMesssage.MessageUpdateCurrPlayer;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCreator {

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
    }

    public void onUpdateCurrentPlayer(Player currPlayer){
        MessageUpdateCurrPlayer message= new MessageUpdateCurrPlayer(currPlayer.getNickname());
    }

    public void onUpdateFaithMarker(){

    }
}
