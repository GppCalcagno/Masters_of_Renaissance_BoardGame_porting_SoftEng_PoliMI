package it.polimi.ingsw.card;

import it.polimi.ingsw.requirements.Requirements;
import it.polimi.ingsw.producible.Producible;
import it.polimi.ingsw.requirements.RequirementsProduction;

import java.util.HashMap;
import java.util.Map;

public class DevelopmentCard extends Card {
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
    private RequirementsProduction costProduction;

    /**
     * This attribute indicates the number and the type of Resources given from the Development Card's Production power
     */
    private Map<String, Integer> productedResources;

    /**
     * This attribute indicates the requirements to buy a Development Card
     */
    private RequirementsProduction cost;

    /**
     * This is the constructed method
     */
    public DevelopmentCard(ColorCard colorCard, int level, RequirementsProduction costProduction, Map<String, Integer> productedResources) {
        this.colorCard = colorCard;
        this.level = level;
        this.costProduction = costProduction;
        this.productedResources = new HashMap<>();
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
    public Map<String, Integer> getProductedResources() {
        return productedResources;
    }

    /**
     * This method returns cost attribute
     * @return number and type of Resource to buy the Development Card
     */
    public RequirementsProduction getCost() {
        return cost;
    }
}
