package StubGiovanni;

import it.polimi.ingsw.player.SlotDevCards;
import it.polimi.ingsw.producible.Resources;

import java.util.ArrayList;
import java.util.List;

public class SlotDevCardsStub extends SlotDevCards {
    private List<Resources> leaderCardEffect;

    public SlotDevCardsStub() {
        this.leaderCardEffect = new ArrayList<>();
    }

    public void addleaderCardEffect (Resources resources){
        this.leaderCardEffect.add(resources);
    }

    public List<Resources> getLeaderCardEffect() {
        return leaderCardEffect;
    }
}

