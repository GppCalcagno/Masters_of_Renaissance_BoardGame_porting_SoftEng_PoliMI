package stub;

import it.polimi.ingsw.producible.Resources;

public class stubResources extends Resources {

    private int res;

    public stubResources(int res){
        this.res = res;
    }

    public int getRes() {
        return res;
    }
}