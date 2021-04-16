package it.polimi.ingsw.game;

import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {
    //non posso avere un'avanzare disomogeneo perchè ogni volta che viene incrementato il marker viene attivato il controllo

    @Test
    void standardincrement() {
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));
        playersVet.add(new Player("Giovanni"));
        playersVet.add(new Player("Giacomo"));

        for (int i = 0; i < 8; i++) { playersVet.get(0).increasefaithMarker();}
        for (int i = 0; i < 3; i++) { playersVet.get(1).increasefaithMarker();}
        for (int i = 0; i < 5; i++) { playersVet.get(2).increasefaithMarker();}


        testtrack.checkPopeSpace(playersVet, 0);
        assertEquals(testtrack.getCurrentPopTile(), 1);
        assertTrue(playersVet.get(0).getPopsfavortiles(0));
        assertFalse(playersVet.get(1).getPopsfavortiles(0));
        assertTrue(playersVet.get(2).getPopsfavortiles(0));
    }

    @Test
    void allbelow() {
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));
        playersVet.add(new Player("Giovanni"));
        playersVet.add(new Player("Giacomo"));

        testtrack.checkPopeSpace(playersVet,0);

        assertEquals(testtrack.getCurrentPopTile(), 0);
        assertFalse(playersVet.get(0).getPopsfavortiles(0));
        assertFalse(playersVet.get(1).getPopsfavortiles(0));
        assertFalse(playersVet.get(2).getPopsfavortiles(0));
    }

    @Test
    void mix(){
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));
        playersVet.add(new Player("Giovanni"));
        playersVet.add(new Player("Giacomo"));

        for (int i = 0; i < 8; i++) { playersVet.get(0).increasefaithMarker();} //si
        for (int i = 0; i < 3; i++) { playersVet.get(1).increasefaithMarker();} //no
        for (int i = 0; i < 6; i++) { playersVet.get(2).increasefaithMarker();} //si

        testtrack.checkPopeSpace(playersVet,0);

        for (int i = 0; i < 2; i++) { playersVet.get(0).increasefaithMarker();} //no
        for (int i = 0; i < 13; i++) { playersVet.get(1).increasefaithMarker();} //si
        for (int i = 0; i < 8; i++) { playersVet.get(2).increasefaithMarker();} //si

        testtrack.checkPopeSpace(playersVet,0);
        testtrack.checkPopeSpace(playersVet,0); //nessuno avanza


        for (int i = 0; i < 2; i++) { playersVet.get(0).increasefaithMarker();} //no
        for (int i = 0; i < 1; i++) { playersVet.get(1).increasefaithMarker();} //no
        for (int i = 0; i < 10; i++) { playersVet.get(2).increasefaithMarker();} //si

        testtrack.checkPopeSpace(playersVet,0);

        assertEquals(testtrack.getCurrentPopTile(),3);

        assertTrue(playersVet.get(0).getPopsfavortiles(0));
        assertFalse(playersVet.get(0).getPopsfavortiles(1));
        assertFalse(playersVet.get(0).getPopsfavortiles(2));

        assertFalse(playersVet.get(1).getPopsfavortiles(0));
        assertTrue(playersVet.get(1).getPopsfavortiles(1));
        assertFalse(playersVet.get(1).getPopsfavortiles(2));

        assertTrue(playersVet.get(2).getPopsfavortiles(0));
        assertTrue(playersVet.get(2).getPopsfavortiles(1));
        assertTrue(playersVet.get(2).getPopsfavortiles(2));
    }

    @Test
    void emptylist(){
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();

        testtrack.checkPopeSpace(playersVet,0);
        assertEquals(testtrack.getCurrentPopTile(),0);
    }

    @Test
    void SinglePlayer(){
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));

        testtrack.checkPopeSpace(playersVet,8);

        assertEquals(testtrack.getCurrentPopTile(),1);
        assertFalse(playersVet.get(0).getPopsfavortiles(0));

        for (int i = 0; i < 16; i++) { playersVet.get(0).increasefaithMarker();}

        testtrack.checkPopeSpace(playersVet,16);

        assertEquals(testtrack.getCurrentPopTile(),2);
        assertFalse(playersVet.get(0).getPopsfavortiles(0));
        assertTrue(playersVet.get(0).getPopsfavortiles(1));

        for (int i = 0; i < 8; i++) { playersVet.get(0).increasefaithMarker();}
        testtrack.checkPopeSpace(playersVet,16);

        assertEquals(testtrack.getCurrentPopTile(),3);
        assertFalse(playersVet.get(0).getPopsfavortiles(0));
        assertTrue(playersVet.get(0).getPopsfavortiles(1));
        assertTrue(playersVet.get(0).getPopsfavortiles(2));
    }

    @Test
    void victorypoints(){
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));
        playersVet.add(new Player("Giovanni"));


        for (int i = 0; i < 8; i++) { playersVet.get(0).increasefaithMarker();}
        for (int i = 0; i < 6; i++) { playersVet.get(1).increasefaithMarker();}

        testtrack.checkPopeSpace(playersVet,0);

        for (int i = 0; i < 8; i++) { playersVet.get(0).increasefaithMarker();}
        for (int i = 0; i < 5; i++) { playersVet.get(1).increasefaithMarker();}

        testtrack.checkPopeSpace(playersVet,0);

        for (int i = 0; i < 8; i++) { playersVet.get(0).increasefaithMarker();}
        for (int i = 0; i < 8; i++) { playersVet.get(1).increasefaithMarker();}

        testtrack.checkPopeSpace(playersVet,0);

        assertEquals(testtrack.getPlayerPoint(playersVet.get(0)),79);
        assertEquals(testtrack.getPlayerPoint(playersVet.get(1)),40);
    }
}