package it.polimi.ingsw.model.singleplayer.token;


import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MoveOneAndMixTest {

    @Test
    void effectTokens() throws IOException {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        Tokens t = new MoveOneAndMix();

        try {
            t.effectTokens(l);
        } catch (ActiveVaticanReportException ignored) {}

        assertEquals(1, l.getFaithMarker());
    }
}