package it.polimi.ingsw.model.card;

import java.io.Serializable;

public abstract class Card {
    /**
     * This attribute indicates the number of victory points
     */
    private int victoryPoints;

    /**
     * This attribute identifies the card with an unique identifier
     */
    private String ID;

    /**
     * This method returns the number of victory points
     * @return the victory points
     */
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    /**
     * This method returns the card's ID
     * @return a String
     */
    public String getID() {
        return ID;
    }
}
