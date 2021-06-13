package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {

    @Test
    void standardIncrement() throws GameFinishedException {
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));
        playersVet.add(new Player("Giovanni"));
        playersVet.add(new Player("Giacomo"));

        for (int i = 0; i < 8; i++) {
            try {playersVet.get(0).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        }
        for (int i = 0; i < 3; i++) {
            try {playersVet.get(1).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        }

        for (int i = 0; i < 5; i++) {
            try { playersVet.get(2).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        }


        testtrack.checkPopeSpace(playersVet, 0);
        assertEquals(testtrack.getCurrentPopTile(), 1);
        assertTrue(playersVet.get(0).getPopsfavortiles(0));
        assertFalse(playersVet.get(1).getPopsfavortiles(0));
        assertTrue(playersVet.get(2).getPopsfavortiles(0));
    }

    @Test
    void allbelow() throws GameFinishedException {
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

        for (int i = 0; i < 8; i++) {
            try { playersVet.get(0).increasefaithMarker();} //si
            catch (ActiveVaticanReportException ignored){}
        }

        for (int i = 0; i < 3; i++) {
            try { playersVet.get(1).increasefaithMarker();}//no
            catch (ActiveVaticanReportException ignored){}
        }

        for (int i = 0; i < 6; i++) {
            try {playersVet.get(2).increasefaithMarker();}  //si
            catch (ActiveVaticanReportException ignored){}
        }

        try {testtrack.checkPopeSpace(playersVet,0);}
        catch (GameFinishedException ignored) {}

        for (int i = 0; i < 2; i++) {
            try {playersVet.get(0).increasefaithMarker();} //no
            catch (ActiveVaticanReportException ignored){}
        }


        for (int i = 0; i < 13; i++) {
            try {playersVet.get(1).increasefaithMarker();} //si
            catch (ActiveVaticanReportException ignored){}
        }
        for (int i = 0; i < 8; i++) {
            try {playersVet.get(2).increasefaithMarker();} //si
            catch (ActiveVaticanReportException ignored){}
        }

        try {testtrack.checkPopeSpace(playersVet,0);}
        catch (GameFinishedException ignored) {}
        try {testtrack.checkPopeSpace(playersVet,0);}//nessuno avanza
        catch (GameFinishedException ignored) {}


        for (int i = 0; i < 2; i++) {
            try {playersVet.get(0).increasefaithMarker();} //no
            catch (ActiveVaticanReportException ignored){}
        }

        for (int i = 0; i < 1; i++) {
            try { playersVet.get(1).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        }

        for (int i = 0; i < 10; i++) {
            try {playersVet.get(2).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        } //si

        try {testtrack.checkPopeSpace(playersVet,0);}
        catch (GameFinishedException ignored) {}

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
    void emptylist() throws GameFinishedException {
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

        try {testtrack.checkPopeSpace(playersVet,8);}
        catch (GameFinishedException ignored) {}

        assertEquals(testtrack.getCurrentPopTile(),1);
        assertFalse(playersVet.get(0).getPopsfavortiles(0));

        for (int i = 0; i < 16; i++) {
            try {playersVet.get(0).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        }

        try {testtrack.checkPopeSpace(playersVet,16);}
        catch (GameFinishedException ignored) {}

        assertEquals(testtrack.getCurrentPopTile(),2);
        assertFalse(playersVet.get(0).getPopsfavortiles(0));
        assertTrue(playersVet.get(0).getPopsfavortiles(1));

        for (int i = 0; i < 8; i++) {
            try {playersVet.get(0).increasefaithMarker();}
            catch (ActiveVaticanReportException ignored){}
        }
        try {testtrack.checkPopeSpace(playersVet,16);}
        catch (GameFinishedException ignored) {}

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


        for (int i = 0; i < 8; i++) {
            try {playersVet.get(0).increasefaithMarker();} //si
            catch (ActiveVaticanReportException ignored){}
        }
        for (int i = 0; i < 6; i++) {
            try {playersVet.get(1).increasefaithMarker();} //si
            catch (ActiveVaticanReportException ignored){}
        }

        try {testtrack.checkPopeSpace(playersVet,0);}
        catch (GameFinishedException ignored) {}

        for (int i = 0; i < 8; i++) {
            try {playersVet.get(0).increasefaithMarker();}//si
            catch (ActiveVaticanReportException ignored){}
        }
        for (int i = 0; i < 5; i++) {
            try { playersVet.get(1).increasefaithMarker();}//no
            catch (ActiveVaticanReportException ignored){}
        }

        try {testtrack.checkPopeSpace(playersVet,0);}
        catch (GameFinishedException ignored) {}

        for (int i = 0; i < 8; i++) {
            try {playersVet.get(0).increasefaithMarker();}//si
            catch (ActiveVaticanReportException ignored){}
        }

        for (int i = 0; i < 8; i++) {
            try {playersVet.get(1).increasefaithMarker();}//si
            catch (ActiveVaticanReportException ignored){}
        }

        try {testtrack.checkPopeSpace(playersVet,0);}
        catch (GameFinishedException ignored) {}

        assertEquals(testtrack.getPlayerPoint(playersVet.get(0)),29);
        assertEquals(testtrack.getPlayerPoint(playersVet.get(1)),18);
    }

    @Test
    public void FinishedgameException(){
        FaithTrack testtrack = new FaithTrack();
        ArrayList<Player> playersVet = new ArrayList<>();
        playersVet.add(new Player("Aldo"));

        try {
            testtrack.checkPopeSpace(playersVet,8);
        } catch (GameFinishedException ignored) {}

        try {
            testtrack.checkPopeSpace(playersVet,16);
        } catch (GameFinishedException ignored) {}

        try {
            testtrack.checkPopeSpace(playersVet,24);
            fail();
        } catch (GameFinishedException e) {
            assertTrue(true);
        }


    }
}