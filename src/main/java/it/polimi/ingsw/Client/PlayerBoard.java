package it.polimi.ingsw.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ChestLeader;
import it.polimi.ingsw.model.card.leadereffect.DiscountLeader;
import it.polimi.ingsw.model.card.leadereffect.ProductionLeader;
import it.polimi.ingsw.model.card.leadereffect.TrasformationMarbleLeader;
import it.polimi.ingsw.model.producible.*;
import it.polimi.ingsw.model.requirements.RequestedLevelDevelopmentCards;
import it.polimi.ingsw.model.requirements.RequestedResources;
import it.polimi.ingsw.model.requirements.RequestedTypeDevelopmentCards;
import it.polimi.ingsw.model.requirements.Requirements;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * this class is used to store Client Data, them are stored most of all in String.
 */
public class PlayerBoard {

    private List<String> playerList;
    /**
     * @nickname is the name of tha player whom this playerboard is connected
     */
    private String nickname;

    /**
     * the name of the current player, not always the same of nickname
     */
    private String currentPlayer;

    //Structure for resources
    private String[][] warehouse;
    private Map<String,Integer> extrachest;
    private Map<String,Integer> strongbox;

    //faith track
    private Map<String, Integer> playersFaithMarkerPosition;
    private Map<String, boolean[]> playersPopFavoriteTile;

    private List<String> leaderCards;

    private String [][] slotDevCard;

    private String [][][] devCardDeck;
    private String [][] marketTray;

    private String remainingMarble;
    /**
     * this is the buffer that player has to manage when he does an extraction
     */
    private List<String> marbleBuffer;
    private String activedDevCardProd;
    /**
     * the card player selects to buy but he doesn't pay it yet
     */
    private String currentDevCardToBuy;
    /**
     * if the player isn't the first one he has to choose a resource, in order with the rules
     */
    private int currentInitialResourcesToChoose;

    private String playerWinner;
    private Map<String, Integer> playersPoints;

    private String lastTokenUsed; //only for singleplayer
    private String lastTokenUsedColor; //only for singleplayer
    private int blackCrossToken; //only for singleplayer

    /**
     * other player to show the status of other players' board
     */
    private OtherPlayer otherPlayer;
    private Map<String, DevelopmentCard> developmentCardMap;
    private Map<String, LeaderAction> leaderActionMap;


    public PlayerBoard () {
        playerList= new ArrayList<>();
        currentPlayer=null;

        warehouse= new String[3][3];
        strongbox= new HashMap<>();
        extrachest= new HashMap<>();

        blackCrossToken=0;
        playersFaithMarkerPosition= new HashMap<>();
        playersPopFavoriteTile= new HashMap<>();
        playersPoints= new HashMap<>();


        leaderCards= new ArrayList<>();
        slotDevCard= new String[3][3];

        devCardDeck= new String[3][4][4];
        marketTray= new String[3][4];

        developmentCardMap = new HashMap<>();
        leaderActionMap = new HashMap<>();

        currentInitialResourcesToChoose=0;
        currentDevCardToBuy=null;
        activedDevCardProd=null;

        try {
            initializeLeaderCardMap();
            initializeDevCardMap();
        } catch (IOException ioException) {
            System.out.println("CAN'T LOAD CARD! CHECK GSON FILE");
            ioException.printStackTrace();
            System.exit(0);
        }
        marbleBuffer = new ArrayList<>();
        otherPlayer=null;
    }

    public void setNickname(String name){
        this.nickname=name;
    }


    public void setActivedDevCardProd(String activedDevCardProd) {
        if(isMyturn())
            this.activedDevCardProd = activedDevCardProd;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public void setWarehouse(String[][] warehouse, Map<String,Integer> extrachest) {
        if(isMyturn()){
            this.warehouse = warehouse;
            this.extrachest= extrachest;
        }
    }


    public void setStrongbox(Map<String, Integer> strongbox) {
        if(isMyturn())
        this.strongbox = strongbox;
    }

    public void setLeaderCard(List<String> leaderCard) {
        if(isMyturn())
            this.leaderCards = leaderCard;
    }

    /**
     * this method set the position of the faith marker, if is bigger than 24, the end of the faith track, it is set to 24
     */
    public void setFaithMarker(Map<String, Integer> playersPosition, Map<String, boolean[]> playersPopFavoriteTile, int blackCrossToken){
        this.playersFaithMarkerPosition=playersPosition;
        this.playersPopFavoriteTile=playersPopFavoriteTile;
        this.blackCrossToken=blackCrossToken;

        if(this.blackCrossToken>24)this.blackCrossToken=24;

        for(String name: this.playersFaithMarkerPosition.keySet()){
            if(this.playersFaithMarkerPosition.get(name)>24)
                this.playersFaithMarkerPosition.put(name,24);

        }
    }

    /**
     * when a player does an extraction the market tray change
     * @param direction if row or column
     * @param n row or column's number
     */
    public void updateMarketTray(char direction, int n){
        String temp = remainingMarble;
        switch (direction) {
            case 'C':
                if (isMyturn()) {
                    for (int i = 0; i < 3; i++) marbleBuffer.add(marketTray[i][n]);
                }
                remainingMarble = marketTray[0][n];
                marketTray[0][n] = marketTray[1][n];
                marketTray[1][n] = marketTray[2][n];
                marketTray[2][n] = temp;
                break;
            case 'R':
                if (isMyturn()) {
                    for (int i = 0; i < 4; i++) marbleBuffer.add(marketTray[n][i]);
                }
                remainingMarble = marketTray[n][0];
                marketTray[n][0] = marketTray[n][1];
                marketTray[n][1] = marketTray[n][2];
                marketTray[n][2] = marketTray[n][3];
                marketTray[n][3] = temp;
                break;
        }
    }

    /**
     * remove a marble form the buffer if action (add or discard) is true
     * @param action add or discard, the player have to select one of those if wants remove a marble from the buffer
     */
    public void removemarblefromBuffer(boolean action){
        if(isMyturn() && action)
            marbleBuffer.remove(0);
    }

    /**
     * this method remove a card from the top of a pile in the dev card deck
     * @param ID the card to be removed
     */
    public void removeCardfromDevCardDeck (String ID) {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                    if (devCardDeck[i][j][0] != null && devCardDeck[i][j][0].equals(ID)) {
                        while (k < 3 && devCardDeck[i][j][k + 1] != null)
                            k++;
                        for (int z = 0; z< k; z++) {
                            devCardDeck[i][j][z] = devCardDeck[i][j][z + 1];
                        }
                        devCardDeck[i][j][k] = null;
                }
            }
        }
        if(isMyturn())
            currentDevCardToBuy=ID;

    }

    /**
     * update the scructure that are changed
     */
    public void updateresoruces(String[][] warehouse,  Map<String, Integer> extraChest, Map<String,Integer> strongbox){
        if(isMyturn()){
            this.warehouse=warehouse;
            this.extrachest=extraChest;
            this.strongbox=strongbox;
        }
        //this message comes only after paying for a devcard
        currentDevCardToBuy=null;
        activedDevCardProd=null;
    }


    /**
     * when game start set the first parameters
     */
    public void onGameStart(List<String> playerList, Map<String,List<String>> leaderCards, String [][][] devCardDeck,  String[][] marketTray, String remainingMarble){
        this.playerList=playerList;
        currentPlayer=playerList.get(0);

        this.leaderCards=leaderCards.get(nickname);
        this.devCardDeck=devCardDeck;
        this.marketTray=marketTray;
        this.remainingMarble= remainingMarble;

        for (String p: playerList){
            playersFaithMarkerPosition.put(p,0);
            playersPopFavoriteTile.put(p, new boolean[3]);
        }
    }

    /**
     * Lorenzo played a turn and his parameters have been updated
     */
    public void singlePlayerUpdate(String[][][] devCardDeck, int blackCrossToken, String tokenID, String tokenColor){
        this.devCardDeck=devCardDeck;
        this.blackCrossToken=blackCrossToken;
        this.lastTokenUsed=tokenID;
        this.lastTokenUsedColor = tokenColor;

        if(this.blackCrossToken>24)this.blackCrossToken=24;
    }

    /**
     * update the slot dev card
     */
    public void updateSlotDevCard(String ID, int col){
        if(isMyturn()){
            int i=0;
            while(slotDevCard[i][col]!=null && i<3)i++;
            slotDevCard[i][col]=ID;
        }
    }

    /**
     * update the state of a leadercard, in particular if the leadercard has been activated or discarded
     * @param ID leadercard's id
     * @param activeOrDiscard active or discard's state
     */
    public void updateStateLeaderCard(String ID,boolean activeOrDiscard){
        if(isMyturn()){
            if(activeOrDiscard){
                leaderActionMap.get(ID).setActivated();
            }
            else {
                leaderActionMap.remove(ID);
                leaderCards.remove(ID);
            }
        }
    }

    /**
     * update the winner of the game
     * @param playerWinner the winner
     * @param playersPoints winner's points
     */
    public void updateWinner(String playerWinner, Map<String, Integer> playersPoints){
        this.playerWinner=playerWinner;
        this.playersPoints=playersPoints;
    }

    /**
     * update the winner of the single player's game
     * @param win if win or lose, lorenzo wins
     * @param finalpoint player's final points
     */
    public void updateWinner(boolean win, int finalpoint){
        if(win)
            playerWinner=nickname;
        else
            playerWinner= "LorenzoIlMagnifico";
        playersPoints.put(nickname,finalpoint);
    }

    /**
     * this method return if is player's turn
     */
    public boolean isMyturn(){ return currentPlayer.equals(nickname); }

    /**
     * how many resources a player can select at the game' start
     * @return
     */
    public int getNumInitialResources(){
        switch (playerList.indexOf(nickname)){
            case 0: return 0;
            case 1: return 1;
            case 2: return 1;
            case 3: return 2;
            default: return 0;
        }
    }

    /**
     * the position of the player in the list
     * @return
     */
    public int getplayernumber(){
        return playerList.indexOf(nickname);
    }

    /**
     * this method allow to reconnect to a game when player accidentally or on purpose disconnect himself
     * @param playersNameList
     * @param currentPlayer
     * @param devCardDeck
     * @param marketTray
     * @param remainingMarble
     * @param marbleBuffer
     * @param warehouse
     * @param extraChest
     * @param strongbox
     * @param playersPosition
     * @param playersPopFavoriteTile
     * @param blackcrosstoken
     * @param leaderCards
     * @param slotDevCards
     * @param activeDevCardToBuy
     * @param activeDevCardProd
     * @param currentInitialResourcesToChoose
     */
    public void resume(List<String> playersNameList,
            String currentPlayer, String[][][] devCardDeck, String[][] marketTray,
            String remainingMarble,List<String> marbleBuffer, String[][] warehouse, Map<String,
            Integer> extraChest, Map<String, Integer> strongbox, Map<String, Integer> playersPosition,
            Map<String, boolean[]> playersPopFavoriteTile, int blackcrosstoken, List<String> leaderCards, String[][] slotDevCards, String activeDevCardToBuy,
            String activeDevCardProd, int currentInitialResourcesToChoose) {

        this.playerList=playersNameList;
        this.currentPlayer=currentPlayer;

        this.warehouse=warehouse;
        this.strongbox=strongbox;
        this.extrachest=extraChest;

        this.playersFaithMarkerPosition=playersPosition;
        this.playersPopFavoriteTile=playersPopFavoriteTile;

        this.leaderCards=leaderCards;
        this.slotDevCard=slotDevCards;

        this.devCardDeck=devCardDeck;
        this.marketTray=marketTray;
        this.remainingMarble=remainingMarble;
        this.marbleBuffer=marbleBuffer;

        this.activedDevCardProd=activeDevCardProd;
        this.currentDevCardToBuy=activeDevCardToBuy;
        this.currentInitialResourcesToChoose=currentInitialResourcesToChoose;

        this.blackCrossToken=blackcrosstoken;
    }

    /**
     * create an object other player for visualize it
     * @param name
     * @param warehouse
     * @param extrachest
     * @param strongbox
     * @param leaderCards
     * @param slotDevCard
     */
    public void setOtherPlayer(String name, String[][] warehouse, Map<String, Integer> extrachest, Map<String, Integer> strongbox, List<String> leaderCards, String[][] slotDevCard) {
        this.otherPlayer = new OtherPlayer(name,warehouse,extrachest,strongbox,leaderCards,slotDevCard);
    }

    /**
     * load the gson file of the dev cards
     * @throws IOException
     */
    void initializeDevCardMap() throws IOException {
        Gson gson = new GsonBuilder().create();

        Reader reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level3green.json"));
        DevelopmentCard[] devCards3gvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc3g : devCards3gvet) {
            developmentCardMap.put(dc3g.getID(), dc3g);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level3blue.json"));
        DevelopmentCard[] devCards3bvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc3b : devCards3bvet) {
            developmentCardMap.put(dc3b.getID(), dc3b);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level3yellow.json"));
        DevelopmentCard[] devCards3yvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc3y : devCards3yvet) {
            developmentCardMap.put(dc3y.getID(), dc3y);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level3purple.json"));
        DevelopmentCard[] devCards3pvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc3p : devCards3pvet) {
            developmentCardMap.put(dc3p.getID(), dc3p);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level2green.json"));
        DevelopmentCard[] devCards2gvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc2g : devCards2gvet) {
            developmentCardMap.put(dc2g.getID(), dc2g);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level2blue.json"));
        DevelopmentCard[] devCards2bvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc2b : devCards2bvet) {
            developmentCardMap.put(dc2b.getID(), dc2b);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level2yellow.json"));
        DevelopmentCard[] devCards2yvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc2y : devCards2yvet) {
            developmentCardMap.put(dc2y.getID(), dc2y);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level2purple.json"));
        DevelopmentCard[] devCards2pvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc2p : devCards2pvet) {
            developmentCardMap.put(dc2p.getID(), dc2p);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level1green.json"));
        DevelopmentCard[] devCards1gvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc1g : devCards1gvet) {
            developmentCardMap.put(dc1g.getID(), dc1g);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level1blue.json"));
        DevelopmentCard[] devCards1bvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc1b : devCards1bvet) {
            developmentCardMap.put(dc1b.getID(), dc1b);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level1yellow.json"));
        DevelopmentCard[] devCards1yvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc1y : devCards1yvet) {
            developmentCardMap.put(dc1y.getID(), dc1y);
        }
        reader= new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/cardJSON/developmentCard/devCards_level1purple.json"));
        DevelopmentCard[] devCards1pvet = gson.fromJson(reader, DevelopmentCard[].class);
        for (DevelopmentCard dc1p : devCards1pvet) {
            developmentCardMap.put(dc1p.getID(), dc1p);
        }
    }

    /**
     * load the gson file of the leader cards
     * @throws IOException
     */
    void initializeLeaderCardMap () throws IOException {
        RuntimeTypeAdapterFactory<Requirements> requirementsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Requirements.class)
                .registerSubtype(RequestedResources.class)
                .registerSubtype(RequestedLevelDevelopmentCards.class)
                .registerSubtype(RequestedTypeDevelopmentCards.class);
        RuntimeTypeAdapterFactory<Resources> resourcesRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Resources.class)
                .registerSubtype(Coins.class)
                .registerSubtype(Servants.class)
                .registerSubtype(Shields.class)
                .registerSubtype(Stones.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(requirementsRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(resourcesRuntimeTypeAdapterFactory)
                .create();

        Reader discount = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettDiscountLeader_LeaderCards.json"));
        DiscountLeader[] discountLeaders = gson.fromJson(discount, DiscountLeader[].class);
        for (LeaderAction dl : discountLeaders) {
            leaderActionMap.put(dl.getID(), dl);
        }

        Reader chest = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettChestLeader_LeaderCards.json"));
        ChestLeader[] chestLeaders = gson.fromJson(chest, ChestLeader[].class);
        for (LeaderAction cl : chestLeaders) {
            leaderActionMap.put(cl.getID(), cl);
        }

        Reader production = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettProductionLeader_LeaderCards.json"));
        ProductionLeader[] productionLeaders = gson.fromJson(production, ProductionLeader[].class);
        for (LeaderAction pl : productionLeaders) {
            leaderActionMap.put(pl.getID(), pl);
        }

        Reader transformation = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettTrasformationMarbleLeader_LeaderCards.json"));
        TrasformationMarbleLeader[] transformationMarbleLeaders = gson.fromJson(transformation, TrasformationMarbleLeader[].class);
        for (LeaderAction tl : transformationMarbleLeaders) {
            leaderActionMap.put(tl.getID(), tl);
        }
    }


    /**
     * allows the search of a card from its id
     * @param ID card's id
     * @return a DevelopmentCard object
     */
    public DevelopmentCard searchDevCard (String ID) {
        return developmentCardMap.get(ID);
    }

    /**
     * allows the search of a card from its id
     * @param ID card's id
     * @return a DevelopmentCard object
     */
    public LeaderAction searchLeaderCard (String ID) {
        return leaderActionMap.get(ID);
    }


    public Map<String, LeaderAction> getLeaderActionMap() {
        return leaderActionMap;
    }

    public List<String> getPlayerList() {
        return playerList;
    }


    public String getNickname() {
        return nickname;
    }

    public Map<String, Integer> getPlayersFaithMarkerPosition() {
        return playersFaithMarkerPosition;
    }

    public Map<String, boolean[]> getPlayersPopFavoriteTile() {
        return playersPopFavoriteTile;
    }

    public String getPlayerWinner() {
        return playerWinner;
    }

    public Map<String, Integer> getPlayersPoints() {
        return playersPoints;
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

    public List<String> getLeaderCards() {
        return leaderCards;
    }

    public String getLastTokenUsed() {
        return lastTokenUsed;
    }

    public String getLastTokenUsedColor() {
        return lastTokenUsedColor;
    }

    public int getFaithMarker(){
        return playersFaithMarkerPosition.get(nickname);
    }

    public int getBlackCrossToken() {
        return blackCrossToken;
    }

    public String getActivedDevCardProd() {
        return activedDevCardProd;
    }

    public String getCurrentDevCardToBuy() {
        return currentDevCardToBuy;
    }

    public int getCurrentInitialResourcesToChoose() {
        return currentInitialResourcesToChoose;
    }

    public OtherPlayer getOtherPlayer() {
        return otherPlayer;
    }
}
