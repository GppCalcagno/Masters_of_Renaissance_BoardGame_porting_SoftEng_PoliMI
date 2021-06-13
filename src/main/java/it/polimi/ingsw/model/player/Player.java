package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NoSelectedLeaderActionExceptions;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.producible.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    /**
     * this attribute is the player Nickname
     */
    private final String nickname;

    /**
     * this attribute is the number of Victory Points of the player
     */
    private int victoryPoints;

    /**
     * this attribute is a list of the LeaderAction Card of the Player!
     */
    private List<LeaderAction> leaderActionBox;

    /**
     * this attribute indicates whether the player is connected to the game:
     * 1= connected
     * 0= disconnected
     */
    private boolean connected;


    /**
     * this attribute indicates  the location on the FaithTrack
     */
    private int faithMarker;
    /**
     * this attribute indicates the numbers of the popfavortiles
     */
    private static final int popfavortilessize=3;

    /**
     * This attribute indicates the discounted Resources when they buy a Development Card
     */
    private List<Resources> leaderCardEffectDiscount;

    /**
     * This attribute indicates the obtained Resources when they take a White Marbles from the Market
     */
    private List<Resources> leaderCardEffectWhiteMarble;

    /**
     * this attribute indicates whether the popsfavortiles are active or not
     */
    private boolean [] popsfavortiles;

    /**
     * this attribute indicates  the player's warehouse
     */
    private WarehouseDepots warehouse;

    /**
     * this attribute indicates  the player's strongbox
     */
    private Strongbox strongbox;

    /**
     * this attribute indicates  the player's Slotdevcards
     */
    private SlotDevCards slotDevCards;

    private DevelopmentCard currentDevCardToBuy;

    private int columnSlotBuyDev;

    private DevelopmentCard currentDevCardToProduce;

    private int initialResources;

    private GameState gameState;

    private TurnPhase turnPhase;

    /**
     * 0-2 slotDevCard Production
     * 3-4 leaderCard Production
     * 5 baseProduction
     */
    private boolean[] canDoProduction;

    /**
     * This is the costructor of Player
     * @param nickname is the Nickname of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.victoryPoints = 0;
        this.faithMarker=0;
        this.leaderActionBox = new ArrayList<LeaderAction>();
        this.connected = true ;
        this.canDoProduction = new boolean[6];
        setCanDoProductionTrue();

        this.popsfavortiles = new boolean[popfavortilessize];

        for (int i = 0; i < 2; i++) { this.popsfavortiles[i]=false; }

        this.slotDevCards= new SlotDevCards();
        this.strongbox= new Strongbox();
        this.warehouse= new WarehouseDepots();
        this.leaderCardEffectDiscount = new ArrayList<>();
        this.leaderCardEffectWhiteMarble = new ArrayList<>();

        this.currentDevCardToBuy = null;
        this.columnSlotBuyDev = -1;

        this.currentDevCardToProduce = null;

        this.initialResources = 0;

        this.gameState=GameState.INITGAME;
        this.turnPhase=TurnPhase.DOTURN;
    }

    public void setCanDoProductionTrue(){
        for (int i = 0; i < 6; i++) {
            canDoProduction[i] = true;
        }
    }

    public void setCanDoProduction(int i, boolean val) {
        canDoProduction[i]=val;
    }

    public boolean getCanDoProduction(int i){
        return canDoProduction[i];
    }

    /**
     * This method returns player's VictoryPoints
     * @return player's VictoryPoints
     */
    public int getVictoryPoints(){
        return victoryPoints;
    }

    /**
     * This method returns player's NickName
     * @return player's NickName
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method returns player's state: 1=connected, 0=disconnected
     * @return player's state
     */
    public boolean getConnected() {
        return connected;
    }

    /**
     *this method returns player's faithMarker
     */
    public int getFaithMarker() {
        return faithMarker;
    }

    /**
     * This method returns the state of player's Popsfavortiles
     * @param i is the index of the player's Popsfavortiles
     * @return the state of player's Popsfavortiles
     */
    public boolean getPopsfavortiles(int i) {
        return popsfavortiles[i];
    }

    /**
     * se the state of selected Popsfavortiles
     * @param i is the index of PopsfavortilesVector
     * @param state is the current state of the Popsfavortiles
     */
    public void setPopsfavortiles(int i, boolean state){
        popsfavortiles[i]=state;
    }
    /**
     * this method increase the number of the player's VictoryPoints
     * @param num is the number of points to increase
     */
    public void addVictoryPoints(int num){
        victoryPoints+=num;
    }

    /**
     * this method set the state (connected or not)of the player
     * @param b is the current state of the player
     */
    public void setConnected(boolean b){
        this.connected=b;
    }

    /**
     * this method increase player's faithmarker
     */
    public void increasefaithMarker() throws ActiveVaticanReportException {
        faithMarker++;
        if (faithMarker == 8 || faithMarker == 16 || faithMarker == 24)
            throw new ActiveVaticanReportException();
        else if (faithMarker == 25)
            faithMarker = 24;
    }

    /**
     * add a leaderaction from leaderActionBox
     * @param l is the leaderAction to be removed
     */
    public void addLeaderAction(LeaderAction l){
        leaderActionBox.add(l);
    }

    /**
     * This method returns the warehouse attribute
     * @return WarehouseDepots object
     */
    public WarehouseDepots getWarehouse(){
        return warehouse;
    }

    /**
     * This method returns the slotDevCards attribute
     * @return SlotDevCards object
     */
    public SlotDevCards getSlotDevCards(){
        return slotDevCards;
    }

    /**
     * This method returns the strongbox attribute
     * @return strongbox object
     */
    public Strongbox getStrongbox(){
        return strongbox;
    }


    /**
     * This method adds an object Resources to leaderCardEffectDiscount list
     * @param resources is the discounted Resources from buying a Development Card
     */
    public void addleaderCardEffectDiscount (Resources resources){
        this.leaderCardEffectDiscount.add(resources);
    }

    /**
     * This method adds an object Resources to leaderCardEffectWhiteMarble list
     * @param resources is the obtained Resources when they take a White Marble
     */
    public void addleaderCardEffectWhiteMarble (Resources resources){
        this.leaderCardEffectWhiteMarble.add(resources);
    }

    /**
     * This method returns the leaderCardEffectDiscount attribute
     * @return a Resources list, that contains the resources that can be discounted when they buy a Development Card that requires that resource
     */
    public List<Resources> getLeaderCardEffectDiscount (){
        return leaderCardEffectDiscount;
    }

    /**
     * This method returns the leaderCardEffectWhiteMarble attribute
     * @return a Resources list, that contains the resources that can be added to the Warehouse depot or to the Strongbox when they take a White Marble from the Market
     */
    public List<Resources> getLeaderCardEffectWhiteMarble (){
        return leaderCardEffectWhiteMarble;
    }

    public void chooseResourceWhiteMarbleEffect (String resource) {
        for (Resources r : leaderCardEffectWhiteMarble) {
            if (resource != null) {
                if (r.toString().equals(resource) || r.toString().equals(resource.toUpperCase())) {
                    if (leaderCardEffectWhiteMarble.indexOf(r) == 1) {
                        Resources r0 = leaderCardEffectWhiteMarble.get(0);
                        leaderCardEffectWhiteMarble.set(0, r);
                        leaderCardEffectWhiteMarble.set(1, r0);
                    }
                }
            }
        }
    }

    /**
     * This method returns the LeaderActionBox attribute
     * @return a List of LeaderAction
     */
    public List<LeaderAction> getLeaderActionBox(){
        return this.leaderActionBox;
    }



    /**
     * This method counts the total number of Victory points from the activated Leader cards
     * @return the number of victory points
     */
    public int countLeaderActionVictoryPoints(){
        int leaderActionVictoryPoints = 0;
        for(LeaderAction leaderAction : leaderActionBox){
            if(leaderAction.getActivated())
                leaderActionVictoryPoints += leaderAction.getVictoryPoints();
        }
        return leaderActionVictoryPoints;
    }

    /**
     * This method returns the total number of player's resources
     * @return the number of player's resources
     */
    public int countTotalResources(){
        int totalResources = 0;
        List<Resources> resourcesList = new ArrayList<>();
        resourcesList.add(new Coins());
        resourcesList.add(new Servants());
        resourcesList.add(new Stones());
        resourcesList.add(new Shields());
        for(Resources resources : resourcesList){
            totalResources += this.strongbox.getNumResources(resources) + this.warehouse.getNumResources(resources);
        }
        return totalResources;
    }

    /**
     * this method sort the leaderCardEffectWhiteMarble
     * @param resources is the resources to put on top
     * @throws NoSelectedLeaderActionExceptions
     */
    public void selectedWhiteMarble(Resources resources) throws NoSelectedLeaderActionExceptions {
        int index=-1;

        for(Resources res: leaderCardEffectWhiteMarble){
            if(res.getClass().equals(resources.getClass())){
                index=leaderCardEffectWhiteMarble.indexOf(res);
            }
        }
        if(index==-1) throw new NoSelectedLeaderActionExceptions();

        if(index!=0)
        leaderCardEffectWhiteMarble.add(0,leaderCardEffectWhiteMarble.remove(index));
    }

    /**
     * this method is used by the controller to check if the client sent the right resources
     * @param warehouseMap resources in Warehouse
     * @param strongboxMap resources in StrongBox
     * @return true if all the resources are owned by player
     */
    public boolean checkListResources(Map<String,Integer> warehouseMap,Map<String,Integer> strongboxMap, Map<String,Integer> extraChestMap){
        //qui ho hardcodato le risorse
        int i=0;
        Resources[] listResources= {new Coins(),new Servants(), new Shields(), new Stones()};

        for(String x:warehouseMap.keySet()){
            i=0;
            //searchResource
            while(!(x.equals(listResources[i].toString())))i++;
            //check quantity
            if(warehouseMap.get(x) > warehouse.getWarehouseNumResources(listResources[i]))return false;
        }


        for(String x:strongboxMap.keySet()) {
            i = 0;

            //searchResource
            while (!x.equals(listResources[i].toString())) i++;
            //check quantity
            if (strongboxMap.get(x) > strongbox.getNumResources(listResources[i])) return false;
        }

        for(String x:extraChestMap.keySet()) {
            boolean check=false;
            int index=0;
            while(!check && index < warehouse.getLeaderCardEffect().size()){
                if(warehouse.getLeaderCardEffect().get(index).getResources().toString().equals(x))
                    check=true;
                else
                    index++;
            }

            if (!check)
                return false;

            if(check && warehouse.getLeaderCardEffect().get(index).getnum()<extraChestMap.get(x))
                return false;
        }
        return true;
    }

    public boolean[] getPopsfavortiles() {
        return popsfavortiles;
    }

    public DevelopmentCard getCurrentDevCardToBuy() {
        return currentDevCardToBuy;
    }

    public int getColumnSlotBuyDev() {
        return columnSlotBuyDev;
    }

    public void setCurrentDevCardToBuy(DevelopmentCard currentDevCardToBuy) {
        this.currentDevCardToBuy = currentDevCardToBuy;
    }

    public void setColumnSlotBuyDev(int columnSlotBuyDev) {
        this.columnSlotBuyDev = columnSlotBuyDev;
    }

    public DevelopmentCard getCurrentDevCardToProduce() {
        return currentDevCardToProduce;
    }

    public void setCurrentDevCardToProduce(DevelopmentCard currentDevCardToProduce) {
        this.currentDevCardToProduce = currentDevCardToProduce;
    }

    public int getInitialResources() {
        return initialResources;
    }

    public void setInitialResources(int initialResources) {
        this.initialResources = initialResources;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }
}
