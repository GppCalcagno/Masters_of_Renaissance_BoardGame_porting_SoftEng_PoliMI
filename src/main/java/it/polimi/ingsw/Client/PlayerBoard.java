package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class is used to store Client Data
 */
public class PlayerBoard {

    private List<String> playerList;
    private String nickname;
    private String currentPlayer;

    private String[][] warehouse;
    private Map<String,Integer> extrachest;
    private Map<String,Integer> strongbox;
    private int faithMarker;

    private List<String> leaderCard;
    private String [][] slotDevCard;

    private String [][][] devCardDeck;
    private String [][] marketTray;
    private List<String> marbleBuffer;
    private String remainingMarble;


    public PlayerBoard () {
        playerList= new ArrayList<>();
        warehouse= new String[3][3];
        strongbox= new HashMap<>();
        extrachest= new HashMap<>();
        faithMarker=0;
        currentPlayer=null;

        leaderCard= new ArrayList<>();
        slotDevCard= new String[3][3];

        devCardDeck= new String[3][4][4];
        marketTray= new String[3][4];
    }

    public void setNickname(String name){
        this.nickname=name;
    }

    public void setPlayerList(List<String> playerList) {
        this.playerList = playerList;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setFaithMarker(int faithMarker) {
        this.faithMarker = faithMarker;
    }

    public void setWarehouse(String[][] warehouse, Map<String,Integer> extrachest) {
        this.warehouse = warehouse;
        this.extrachest= extrachest;

    }

    public void setStrongbox(Map<String, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setLeaderCard(List<String> leaderCard) {
        this.leaderCard = leaderCard;
    }

    public void setSlotDevCard(String[][] slotDevCard) {
        this.slotDevCard = slotDevCard;
    }

    public void setDevCardDeck(String[][][] devCardDeck) {
        this.devCardDeck = devCardDeck;
    }

    public void setMarketTray(String[][] marketTray, String remainingMarble) {
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
    }

    public void setMarbleBuffer(List<String> marbleBuffer) {
        this.marbleBuffer = marbleBuffer;
    }

    public void updateMarketTray(char direction, int n){
        String temp = remainingMarble;

        if( direction == 'c') {
            remainingMarble = marketTray[0][n];
            marketTray[0][n] = marketTray[1][n];
            marketTray[1][n] = marketTray[2][n];
            marketTray[2][n] = temp;
        }
        else if( direction == 'r') {
            remainingMarble = marketTray[n][0];
            marketTray[n][0] = marketTray[n][1];
            marketTray[n][1] = marketTray[n][2];
            marketTray[n][2] = marketTray[n][3];
            marketTray[n][3] = temp;
        }
    }

    public void updateDevCardDeck (String ID) {
        for(int i=0;i<3;i++){
            for (int j=0;j<4;j++){
                for(int k=0;k<devCardDeck[i][j].length;k++){
                    if(devCardDeck[i][j][k].equals(ID))
                        devCardDeck[i][j][k]=null;
                }
            }
        }
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void initialization(List<String> playerList, List<String> leaderCard, String [][][] devCardDeck,  String[][] marketTray, String remainingMarble){
        this.playerList=playerList;
        currentPlayer=playerList.get(0);

        this.leaderCard=leaderCard;
        this.devCardDeck=devCardDeck;
        this.marketTray=marketTray;
        this.remainingMarble= remainingMarble;
    }



    public String getNickname() {
        return nickname;
    }

    public String[][] getWarehouse() {
        return warehouse;
    }

    public Map<String, Integer> getStrongbox() {
        return strongbox;
    }

    public List<String> getLeaderCard() {
        return leaderCard;
    }

    public String[][] getSlotDevCard() {
        return slotDevCard;
    }

    public String[][][] getDevCardDeck() {
        return devCardDeck;
    }

    public String[][] getMarketTray() {
        return marketTray;
    }

    public String getRemainingMarble() {
        return remainingMarble;
    }

    public Map<String, Integer> getExtrachest() {
        return extrachest;
    }

    public int getFaithMarker() {
        return faithMarker;
    }
}
