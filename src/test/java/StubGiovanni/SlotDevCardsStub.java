package StubGiovanni;

import it.polimi.ingsw.card.leadereffect.ExtraProduction;
import it.polimi.ingsw.player.SlotDevCards;
import it.polimi.ingsw.producible.Resources;

import java.util.ArrayList;
import java.util.List;

public class SlotDevCardsStub extends SlotDevCards {
    private List<ExtraProduction> leaderCardEffect;

    public SlotDevCardsStub() {
        this.leaderCardEffect = new ArrayList<>();
    }

    public List<ExtraProduction> getLeaderCardEffect() {
        return leaderCardEffect;
    }
}

