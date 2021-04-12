package StubNico;

import it.polimi.ingsw.producible.Coins;

public class stubResources extends Coins {

    private int res;

    public stubResources(int res){
        this.res = res;
    }

    public int getRes() {
        return res;
    }
}