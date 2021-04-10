package it.polimi.ingsw.singleplayer;

import it.polimi.ingsw.card.DevCardsDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoIlMagnificoTest {

    @Test
    void drawTokens() {
        DevCardsDeck d = new DevCardsDeck();
        LorenzoIlMagnifico l = new LorenzoIlMagnifico(d);

        //l.showVector();

        l.drawTokens();
        l.drawTokens();
        l.drawTokens();
        l.drawTokens();
        l.drawTokens();

        //l.showVector();

    }

    @Test
    void increaseFaithMarker() {
    }
}