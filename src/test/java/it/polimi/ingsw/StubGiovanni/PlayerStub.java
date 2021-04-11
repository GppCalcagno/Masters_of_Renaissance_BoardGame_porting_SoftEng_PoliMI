package it.polimi.ingsw.StubGiovanni;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;

import java.util.ArrayList;
import java.util.List;

public class PlayerStub extends Player {
    private List<Resources> leaderCardEffectDiscount;

    private WarehouseDepotsStub warehouse;

    private List<Resources> leaderCardEffectWhiteMarble;

    private SlotDevCardsStub slotDevCards;

    //private int faithMarker;

    public PlayerStub(String nickname) {
        super(nickname);
        this.leaderCardEffectDiscount = new ArrayList<>();
        this.warehouse= new WarehouseDepotsStub();
        this.leaderCardEffectWhiteMarble = new ArrayList<>();
        this.slotDevCards = new SlotDevCardsStub();
        // this.faithMarker = 0;
    }

    public void addleaderCardEffectDiscount (Resources resources){
        this.leaderCardEffectDiscount.add(resources);
    }

    public List<Resources> getLeaderCardEffectDiscount (){
        return leaderCardEffectDiscount;
    }

    public WarehouseDepotsStub getWarehouse(){
        return this.warehouse;
    }

    public void addleaderCardEffectWhiteMarble (Resources resources){
        this.leaderCardEffectWhiteMarble.add(resources);
    }

    public List<Resources> getLeaderCardEffectWhiteMarble (){
        return leaderCardEffectWhiteMarble;
    }

    public SlotDevCardsStub getSlotDevCards(){
        return slotDevCards;
    }

    // public int getFaithMarker() {
    // return this.faithMarker;
    // }

    // public void increasefaithMarker(){
    // faithMarker++;
    // }
}

