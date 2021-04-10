package it.polimi.ingsw.stub;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.card.DevelopmentCard;
import it.polimi.ingsw.producible.Producible;
import it.polimi.ingsw.requirements.Requirements;

import java.util.Map;

public class stubDevelopmentCard extends DevelopmentCard{

    private int level;

    public stubDevelopmentCard(int level) {
        this.level = level;

    }

    @Override
    public int getLevel() {
        return level;
    }
}