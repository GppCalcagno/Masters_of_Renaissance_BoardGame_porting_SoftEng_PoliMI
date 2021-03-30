package it.polimi.ingsw;

import java.util.HashMap;
import java.util.Map;

public class DevelopmentCard extends Card{
    /**
     * This attribute indicate the Development Card's color
     */
    private ColorCard colorCard;

    /**
     * This attribute indicates the Development Card's level (number of dots)
     */
    private int level;

    /**
     * This attribute indicates the cost to buy this Development Card
     */
    private Requirements costProduction;

    /**
     * This attribute indicates the number and the type of Resources given from the Development Card's Production power
     */
    private Map<Producible, Integer> productedResources;

    /**
     * This is the constructed method
     *
     * @param victoryPoints number of victory points
     */
    public DevelopmentCard(int victoryPoints, ColorCard colorCard, int level, Requirements costProduction, Map<Producible, Integer> productedResources) {
        super(victoryPoints);
        this.colorCard = colorCard;
        this.level = level;
        this.costProduction = costProduction;
        this.productedResources = new HashMap<Producible, Integer>();
    }

    /**
     * This method returns the Development Card's color
     * @return Card's color
     */
    public ColorCard getColorCard() {
        return colorCard;
    }

    /**
     * This method returns the Development Card's number of dots (the level)
     * @return Development Card's number of dots (level)
     */
    public int getLevel() {
        return level;
    }

    /**
     * This method returns the Resources produced from the Development Card's Production power
     * @return a Map that indicates the different number of the produced Resources
     */
    public Map<Producible, Integer> getProductedResources() {
        // to implement
        return null;
    }
}
