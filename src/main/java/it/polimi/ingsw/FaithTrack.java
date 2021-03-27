package it.polimi.ingsw;

public class FaithTrack {

    /**
     * this attribute is a integer  indicating the faithtrack length
     *
     */
    final static int faithtracksize= 25;

    /**
     * this attribute is a integer vector indicating the Victorypoints on the faith track
     *
     */
    final int[]faithtrack;

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
    }

    /**
     *this attribute is the initial point of the first Vatican Report Section
     */
    final static int section1start=5;

    /**
     *this attribute is the final point of the first Vatican Report Section
     */
    final static int section1end=8;

    /**
     *this attribute is the initial point of the second Vatican Report Section
     */
    final static int section2start=12;

    /**
     *this attribute is the final point of the second Vatican Report Section
     */
    final static int section2end=16;

    /**
     *this attribute is the initial point of the third Vatican Report Section
     */
    final static int section3start=19;

    /**
     *this attribute is the final point of the third Vatican Report Section
     */
    final static int section3end=24;


    /**
     * this method returns the number of VictoryPoints of the selected space
     * @param i is the index of the faithtrack space
     * @return the number VictoryPoints of the selected space
     */
    public int getVictoryPoints(int i){
        return faithtrack[i];
    }

    public void checkPopeSpace( )

}

