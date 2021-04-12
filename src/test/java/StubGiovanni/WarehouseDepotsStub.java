package StubGiovanni;

import it.polimi.ingsw.player.WarehouseDepots;
import it.polimi.ingsw.producible.Resources;

import java.util.ArrayList;
import java.util.List;

public class WarehouseDepotsStub extends WarehouseDepots {
    private final int sizex = 3;
    private final int sizey = 3;
    private Resources[][] warehouse;

    private List<Resources> leaderCardEffect;

    public WarehouseDepotsStub(){
        warehouse = new Resources[sizex][sizey];
        this.leaderCardEffect = new ArrayList<>();
    }

    public void addleaderCardEffect (Resources resources){
        this.leaderCardEffect.add(resources);
    }

    public List<Resources> getLeaderCardEffect() {
        return leaderCardEffect;
    }

    public void checkInsertion(Resources resources) {
         warehouse[0][0] = resources;
    }

    public Resources getResourcesWarehouse(int i, int j) {
        return this.warehouse[i][j];
    }
}

