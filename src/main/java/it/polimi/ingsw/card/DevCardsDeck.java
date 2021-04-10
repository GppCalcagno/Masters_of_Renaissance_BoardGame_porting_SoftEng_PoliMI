package it.polimi.ingsw.card;

import it.polimi.ingsw.player.CantDoIt;
import it.polimi.ingsw.player.Player;

public class DevCardsDeck {

    /**
     * this is the cube where the development cards are allocated
     */
    private DevelopmentCard[][][] developmentCardDeck;

    /**
     * this is the constructor that initialized the cube as 4*3*4 in accord with the rules
     */
    public DevCardsDeck(){
        developmentCardDeck = new DevelopmentCard[3][4][4];
    }

    /**
     * this method return the card that is at the top of the cube
     * @param x row
     * @param y column
     * @return the card
     */
    public DevelopmentCard getDevCards(int x, int y){
        return developmentCardDeck[x][y][0];
    }

    /**
     * this method remove the card at the top of the cube
     * @param x row
     * @param y column
     */
    public void removeDevCards(int x, int y) throws CantDoIt{
        int i=0;
        if(developmentCardDeck[x][y][0]==null) throw new CantDoIt("Can't Delete");
        else {
            while (i != developmentCardDeck.length - 1 && developmentCardDeck[x][y][i + 1] != null) i++;
            for (int j = 0; j < i; j++) {
                developmentCardDeck[x][y][j] = developmentCardDeck[x][y][j + 1];
            }
        }
    }

    /**
     * this method assign the card at the top of the cube to the player who purchase it
     * @param p player who calls
     * @param x row
     * @param y column
     */
    public void purhcaseCards(Player p, int x, int y){
       // p.getSlotDevCards().insertCards();

    }

    /**
     * this method return the index of the column from a color
     * @param color the color where cards of that color are stored
     * @return the column of that color, in order whit the rules
     */
    public int getColumnFromColor(ColorCard color){
        int i = 0;
        if(color == ColorCard.GREEN) i=0;
        else if(color == ColorCard.BLUE) i=1;
        else if(color == ColorCard.YELLOW) i=2;
        else if(color == ColorCard.PURPLE) i=3;
        return i;
    }

    /**
     * a get of the developmentCardDeck
     * @return developmentCardDeck
     */
    public DevelopmentCard[][][] getDevelopmentCardDeck() {
        return developmentCardDeck;
    }
}
