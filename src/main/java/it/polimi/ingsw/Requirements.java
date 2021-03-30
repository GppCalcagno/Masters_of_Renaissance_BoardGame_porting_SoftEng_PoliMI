package it.polimi.ingsw;

public interface Requirements {
    /**
     * check if the card requirements are met
     * @param P is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    public boolean checkResources(Player P);
}
