package it.polimi.ingsw;

public class MarketStructure {
    /**
     * These attributes are the sizes of rows and columns of the Market Tray, in which there are the Market Marbles
     */
    private static final int sizex = 3;
    private static final int sizey = 4;

    /**
     * This attribute is the matrix that represents the Market Tray
     */
    private Marbles[][] marketTray;

    /**
     * This attribute is the remaining Marble on the top right corner of the slide
     */
    private Marbles remainingMarble;

    /**
     * This is the constructor method
     * @param marketTray it is the Market Tray in which there are the Market Marbles
     * @param remainingMarble it is the remaining one from the 13 Marbles
     */
    public MarketStructure(Marbles[][] marketTray, Marbles remainingMarble) {
        marketTray = new Marbles[sizex][sizey];
        this.remainingMarble = remainingMarble;
    }

    /**
     * This method returns the row or the column of Marbles, chosen by the player
     * @param direction indicates if the player want to extract a row or a column of Marbles
     * @param n indicates the number of the row or of the column chosen
     * @return it is a vector of Marbles
     */
    public Marbles [] extractMarbles (char direction, int n) {
        //to implement
        return null;
    }

    /**
     * This method inserts the remaining Marble into the row or the column chosen with extractMarbles
     * @param direction indicates if the player has chosen a row or a column
     * @param n indicates the number of the row or the column chosen
     */
    public void insertMarble (char direction, int n) {
        //to implement
    }

    /**
     * This method returns the Market Tray to make know the situation to player who invokes it
     * @return it is a matrix of Marbles
     */
    public Marbles[][] getMarketTray() {
        return marketTray;
    }

    /**
     * This method returns the remaining Marble from the 13 ones
     * @return it is a Marble
     */
    public Marbles getRemainingMarble() {
        return remainingMarble;
    }

    /**
     * This method converts the extracted Market Marbles with the respective Resources
     * @param p to add the converted Resources to the player's Warehouse depots
     */
    public void manageResources (Player p) {
        //to implement
    }
}
