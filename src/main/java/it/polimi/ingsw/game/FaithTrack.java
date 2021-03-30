package it.polimi.ingsw.game;

import it.polimi.ingsw.player.Player;

public class FaithTrack {

    /**
     * this attribute is a integer  indicating the faithtrack vector length
     */
    private final static int faithtracksize= 25;

    /**
     * this attribute is a integer  indicating the popsfavouritetilepoints vector length
     */
    private final static int popsfavouritetilepointsize= 3;

    /**
     * this attribute is a integer vector indicating the Victorypoints on the faith track
     */
    private final int[]faithtrack;

    /**
     * his attribute is a integer vector indicating the Victorypoints on the popsfavouritetile
     */
    private final int[] popsfavouritetilepoints;

    /**
     * this is the costructor of the faithtrack
     */
    public FaithTrack(){
        faithtrack= new int [faithtracksize];
        faithtrack[0]=0;
        faithtrack[1]=0;
        faithtrack[2]=0;
        faithtrack[3]=1;
        faithtrack[4]=0;
        faithtrack[5]=0;
        faithtrack[6]=2;
        faithtrack[7]=0;
        faithtrack[8]=0;
        faithtrack[9]=4;
        faithtrack[10]=0;
        faithtrack[11]=0;
        faithtrack[12]=6;
        faithtrack[13]=0;
        faithtrack[14]=0;
        faithtrack[15]=9;
        faithtrack[16]=0;
        faithtrack[17]=0;
        faithtrack[18]=12;
        faithtrack[19]=0;
        faithtrack[20]=0;
        faithtrack[21]=16;
        faithtrack[22]=0;
        faithtrack[23]=0;
        faithtrack[24]=20;

        popsfavouritetilepoints= new int [popsfavouritetilepointsize];
        popsfavouritetilepoints[0]=2;
        popsfavouritetilepoints[1]=3;
        popsfavouritetilepoints[2]=4;
    }

    /**
     *this attribute is the initial point of the first Vatican Report Section
     */
    private final static int section1start=5;

    /**
     *this attribute is the final point of the first Vatican Report Section
     */
    private final static int section1end=8;

    /**
     *this attribute is the initial point of the second Vatican Report Section
     */
    private final static int section2start=12;

    /**
     *this attribute is the final point of the second Vatican Report Section
     */
    private final static int section2end=16;

    /**
     *this attribute is the initial point of the third Vatican Report Section
     */
    private final static int section3start=19;

    /**
     *this attribute is the final point of the third Vatican Report Section
     */
    private final static int section3end=24;


    /**
     * this method returns the number of VictoryPoints of the selected space
     *
     * @param i is the index of the faithtrack space
     * @return the number VictoryPoints of the selected space
     */
    public int getVictoryPoints(int i){
        return faithtrack[i];
    }


    /**
     * this method manages the Faithtrack.
     * Updates player VictoryPoints and
     * actives or discards Players'popfavortiles
     *
     * @param p is the Players Vector
     * @param blackCrossToken is LorenzoIlMagnifico's blackCrossToken (=O if Multiplayer Game)
     */
    public void checkPopeSpace(Player[] p, int blackCrossToken){

    }

    /**
     * this method returns the number of Victory points of a popsfavouritetile
     *
     * @param i is the index of the popsfavouritetile Vector
     * @return the number of Victory points of the selected popsfavouritetile
     */
    public int getPopsfavouritetilepoints(int i) {
        return popsfavouritetilepoints[i];
    }
}

