package StubGiovanni;

import it.polimi.ingsw.card.DevelopmentCard;
import it.polimi.ingsw.player.SlotDevCards;

public class SlotDevCardsStubDevCardsDeck extends SlotDevCards {
    public boolean insertCards( int column, DevelopmentCard card){
        return true;
    }
}
