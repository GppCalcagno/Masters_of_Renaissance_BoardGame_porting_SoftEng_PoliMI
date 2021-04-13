package it.polimi.ingsw.singleplayer.token;

import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.singleplayer.LorenzoIlMagnifico;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MoveTwoTest {

    @Test
    void effectTokens() throws IOException, NegativeQuantityExceptions {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        Tokens t = new MoveTwo();

        t.effectTokens(l);
        t.effectTokens(l);
        t.effectTokens(l);
        t.effectTokens(l);
        t.effectTokens(l);
        t.effectTokens(l);

        assertEquals(12, l.getFaithMarker());

    }
}