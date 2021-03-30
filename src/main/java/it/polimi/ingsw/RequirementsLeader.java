package it.polimi.ingsw;

public abstract class RequirementsLeader implements Requirements{
    /**
     * check if the card requirements are met
     * @param P is the player who uses the Card
     * @return 1 if the requirements are met, 0 otherwise
     */
    public abstract boolean checkResources(Player P);
}
