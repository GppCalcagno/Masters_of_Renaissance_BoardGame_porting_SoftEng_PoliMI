package it.polimi.ingsw.card;

import it.polimi.ingsw.requirements.Requirements;

public abstract class Card {
    /**
     * This attribute indicates the number of victory points
     */
    private int victoryPoints;

    /**
     * This method returns the number of victory points
     * @return the victory points
     */
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

}
