package it.polimi.ingsw;

public abstract class Card {
    /**
     * This attribute indicates the number of victory points
     */
    private int victoryPoints;

    /**
     * This is the constructed method
     * @param victoryPoints number of victory points
     */
    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     * This method returns the number of victory points
     * @return the victory points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }
}
