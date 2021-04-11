package it.polimi.ingsw.StubGiovanni;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.requirements.RequirementsLeader;

public class RequestedResourcesStub extends RequirementsLeader {
    private Resources reqResources;

    private int reqnumResources;

    public RequestedResourcesStub(Resources reqResources, int reqnumResources) {
        this.reqResources = reqResources;
        this.reqnumResources = reqnumResources;
    }

    @Override
    public boolean checkResources(Player player) {
        return true;
    }
}
