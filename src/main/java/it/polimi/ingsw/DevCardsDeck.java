package it.polimi.ingsw;

import java.util.List;

public class DevCardsDeck {

    /**
     * this is the cube where the development cards are allocated
     */
    private DevelopmentCard[][][] developmentCardDeck;

    /**
     * this is the constructor that initialized the cube as 4*3*4 in accord with the rules
     */
    public DevCardsDeck(){
        developmentCardDeck = new DevelopmentCard[4][3][4];
    }

    /**
     * this method return the card that is at the top of the cube
     * @param x column
     * @param y row
     * @return the card
     */
    public DevelopmentCard getDevCards(int x, int y){
        return developmentCardDeck[x][y][0];
    }

    /**
     * this method remove the card at the top of the cube
     * @param x column
     * @param y row
     */
    public void removeDevCards(int x, int y){

    }

    /**
     * this method assign the card at the top of the cube to the player who purchase it
     * @param p player who calls
     * @param x column
     * @param y row
     */
    public void purhcaseCards(Player p, int x, int y){

    }

}
