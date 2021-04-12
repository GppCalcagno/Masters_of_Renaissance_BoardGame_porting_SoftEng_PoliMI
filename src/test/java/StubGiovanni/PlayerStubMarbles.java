package StubGiovanni;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;

import java.util.ArrayList;
import java.util.List;

public class PlayerStubMarbles extends Player {
    private WarehouseDepotsStub warehouse;
    private List<Resources> leaderCardEffectWhiteMarble;

    public PlayerStubMarbles(String nickname) {
        super(nickname);
        this.warehouse = new WarehouseDepotsStub();
        this.leaderCardEffectWhiteMarble = new ArrayList<>();
    }

    public WarehouseDepotsStub getWarehouse(){
        return warehouse;
    }

    public void addleaderCardEffectWhiteMarble (Resources resources){
        this.leaderCardEffectWhiteMarble.add(resources);
    }

    public List<Resources> getLeaderCardEffectWhiteMarble (){
        return leaderCardEffectWhiteMarble;
    }
}

