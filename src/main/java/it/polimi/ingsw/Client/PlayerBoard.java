package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.*;

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

    private boolean[] popsfavouritetile;
    private int faithMarker;
    private int blackCrossToken;

    private List<String> leaderCard;
    private List<String> whiteMarbleEffectList;
    private String [][] slotDevCard;
    private List<String> prductionBuffer;

    private String [][][] devCardDeck;
    private String [][] marketTray;
    private List<String> marbleBuffer;
    private String remainingMarble;

    private String lastTokenUsed; //only for singleplayer


    public PlayerBoard () {
        playerList= new ArrayList<>();
        currentPlayer=null;

        warehouse= new String[3][3];
        strongbox= new HashMap<>();
        extrachest= new HashMap<>();

        faithMarker=0;
        popsfavouritetile= new boolean[]{false, false, false};


        leaderCard= new ArrayList<>();
        slotDevCard= new String[3][3];

        devCardDeck= new String[3][4][4];
        marketTray= new String[3][4];
        prductionBuffer= new ArrayList<>();
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

    public void setWhiteMarbleEffectList(List<String> whiteMarbleEffectList) {
        this.whiteMarbleEffectList = whiteMarbleEffectList;
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

    public void setPrductionBuffer(List<String> prductionBuffer) {
        this.prductionBuffer = prductionBuffer;
    }

    public void setPopsfavouritetile(boolean[] popsfavouritetile) {
        this.popsfavouritetile = popsfavouritetile;
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

    public void removeCardfromDevCardDeck (String ID) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < devCardDeck[i][j].length; k++) {
                    if (devCardDeck[i][j][k].equals(ID))
                        devCardDeck[i][j][k] = null;
                }
            }
        }
    }

    public void updateresoruces(String[][] warehouse,  Map<String, Integer> extraChest, Map<String,Integer> strongbox){
        this.warehouse=warehouse;
        this.extrachest=extraChest;
        this.strongbox=strongbox;
    }


    public void initialization(List<String> playerList, List<String> leaderCard, String [][][] devCardDeck,  String[][] marketTray, String remainingMarble){
        this.playerList=playerList;
        currentPlayer=playerList.get(0);

        this.leaderCard=leaderCard;
        this.devCardDeck=devCardDeck;
        this.marketTray=marketTray;
        this.remainingMarble= remainingMarble;
    }

    public void singlePlayerUpdate(String[][][] devCardDeck, int blackCrossToken, String tokenID){
        this.devCardDeck=devCardDeck;
        this.blackCrossToken=blackCrossToken;
        this.lastTokenUsed=tokenID;
    }

    public boolean[] getPopsfavouritetile() {
        return popsfavouritetile;
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String[][] getWarehouse() {
        return warehouse;
    }

    public Map<String, Integer> getExtrachest() {
        return extrachest;
    }

    public Map<String, Integer> getStrongbox() {
        return strongbox;
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    public List<String> getLeaderCard() {
        return leaderCard;
    }

    public List<String> getWhiteMarbleEffectList() {
        return whiteMarbleEffectList;
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

    public List<String> getMarbleBuffer() {
        return marbleBuffer;
    }

    public String getRemainingMarble() {
        return remainingMarble;
    }

    public List<String> getPrductionBuffer() {
        return prductionBuffer;
    }
}
