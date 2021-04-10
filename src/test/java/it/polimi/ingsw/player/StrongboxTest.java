package it.polimi.ingsw.player;

import it.polimi.ingsw.stub.secondStubResources;
import org.junit.jupiter.api.Test;
import stub.stubResources;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    void updateResources() {

        Strongbox s = new Strongbox();
        stubResources res1 = new stubResources(4);
        secondStubResources res2 = new secondStubResources();
        s.updateResources(res1, 3);

        s.updateResources(res2, 3);

        assertEquals(3, s.getNumResources(res1));

        s.updateResources(res1, -2);

        assertEquals(1, s.getNumResources(res1));

    }
}