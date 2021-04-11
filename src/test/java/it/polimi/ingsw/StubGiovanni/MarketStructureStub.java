package it.polimi.ingsw.StubGiovanni;

import it.polimi.ingsw.game.MarketStructure;
import it.polimi.ingsw.marbles.BlueMarble;
import it.polimi.ingsw.marbles.Marbles;

import java.util.List;

public class MarketStructureStub extends MarketStructure {
    private static final int sizex = 3;
    private static final int sizey = 4;

    private Marbles[][] marketTray;
    private Marbles remainingMarble;
    private List<Marbles> buffer;

    public MarketStructureStub(Marbles remainingMarble) {
        super(remainingMarble);
        this.marketTray = new Marbles[sizex][sizey];
        for(int i = 0; i < sizex; i++) {
            for(int j = 0; j < sizey; j++) {
                this.marketTray[i][j] = new BlueMarble();
            }
        }
    }
}

