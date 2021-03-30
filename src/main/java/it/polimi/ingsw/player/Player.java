package it.polimi.ingsw.player;

import it.polimi.ingsw.card.LeaderAction;

import java.util.ArrayList;
import java.util.List;

public class Player {
    /**
     * this attribute is the player Nickname
     */
    private String nickname;

    /**
     * this attribute is the number of Victory Points of the player
     */
    private int victoryPoints;

    /**
     * this attribute is a list of the LeaderAction Card of the Player
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



    /**
     * This is the costructor of Player
     * @param nickname is the Nickname of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.victoryPoints = 0;
        this.faithMarker=0;
        this.leaderActionBox = new ArrayList<LeaderAction>(); //da inizializzare
        this.connected = true ;

        this.popsfavortiles = new boolean[popfavortilessize];
        this.popsfavortiles[0]=false;
        this.popsfavortiles[1]=false;
        this.popsfavortiles[2]=false;

        this.slotDevCards= new SlotDevCards();
        this.strongbox= new Strongbox();
        this.warehouse= new WarehouseDepots();
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
        //come facciamo a riconoscere che il player è connesso- da implementare quando si costruisce rete
        this.connected=b;
    }

    /**
     * this method increase player's faithmarker
     */
    public void increasefaithMarker(){
        faithMarker++;
    }

    /**
     * Remove a leaderaction from leaderActionBox
     * @param l is the leaderAction to be removed
     */
    public void discardLeaderAction(LeaderAction l){
        leaderActionBox.remove(l);
    }

    /**
     * add a leaderaction from leaderActionBox
     * @param l is the leaderAction to be removed
     */
    public void addLeaderAction(LeaderAction l){
        leaderActionBox.add(l);
    }
}
