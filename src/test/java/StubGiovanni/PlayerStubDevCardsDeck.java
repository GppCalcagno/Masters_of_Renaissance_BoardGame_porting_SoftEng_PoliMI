package StubGiovanni;

import it.polimi.ingsw.player.Player;

public class PlayerStubDevCardsDeck extends Player {
    private SlotDevCardsStubDevCardsDeck slotDevCards;

    public PlayerStubDevCardsDeck(String nickname) {
        super(nickname);
        slotDevCards = new SlotDevCardsStubDevCardsDeck();
    }

    public SlotDevCardsStubDevCardsDeck getSlotDevCards(){
        return this.slotDevCards;
    }
}
