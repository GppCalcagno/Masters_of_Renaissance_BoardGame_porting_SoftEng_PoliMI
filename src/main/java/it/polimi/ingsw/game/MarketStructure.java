package it.polimi.ingsw.game;

import it.polimi.ingsw.marbles.Marbles;

import java.util.ArrayList;
import java.util.List;

public class MarketStructure {
    /**
     * These attributes are the sizes of rows and columns of the Market Tray, in which there are the Market Marbles
     */
    private static final int sizex = 3;
    private static final int sizey = 4;

    /**
     * This attribute is the matrix that represents the Market Tray
     */
    private Marbles[][] marketTray;

    /**
     * This attribute is the remaining Marble on the top right corner of the slide
     */
    private Marbles remainingMarble;

    /**
     * This attribute is a vector in which extractMarbles inserts the taken Marbles
     */
    private List<Marbles> buffer;

    /**
     * This is the constructor method
     * @param remainingMarble it is the remaining one from the 13 Marbles
     */
    public MarketStructure(Marbles remainingMarble) {
        marketTray = new Marbles[sizex][sizey];
        this.remainingMarble = remainingMarble;
        this.buffer = new ArrayList<>();
    }

    /**
     * This method returns the row or the column of Marbles, chosen by the player
     * @param direction indicates if the player want to extract a row or a column of Marbles
     * @param n indicates the number of the row or of the column chosen
     */
    public void extractMarbles (char direction, int n) {
        // 'c' = colonna, 'r' = riga

        if( direction == 'c' ) {
            if( n >= 0 && n <= sizey ) {
                for(int i = 0; i < sizex; i++) {
                    this.buffer.add(this.marketTray[i][n]);
                }
                insertMarble(direction, n);
            }
            else {
                System.out.println("Errore: num colonna inesistente");
            }
        }
        else if ( direction == 'r' ) {
            if( n >= 0 && n <= sizex ) {
                for(int j = 0; j < sizey; j++) {
                    this.buffer.add(this.marketTray[n][j]);
                }
                insertMarble(direction, n);
            }
            else {
                System.out.println("Errore: num riga inesistente");
            }
        }
        else {
            System.out.println("Errore: nessuna riga o colonna valida indicata");
        }
    }

    /**
     * This method inserts the remaining Marble into the row or the column chosen with extractMarbles
     * @param direction indicates if the player has chosen a row or a column
     * @param n indicates the number of the row or the column chosen
     */
    public void insertMarble (char direction, int n) {
        // non faccio controlli sulla validità dei parametri, perchè già fatti in extractMarbles
        if( direction == 'c') {
            Marbles temp = remainingMarble;
            remainingMarble = marketTray[0][n];
            marketTray[0][n] = marketTray[1][n];
            marketTray[1][n] = marketTray[2][n];
            marketTray[2][n] = temp;
        }
        else if( direction == 'r') {
            Marbles temp = remainingMarble;
            remainingMarble = marketTray[n][0];
            marketTray[n][0] = marketTray[n][1];
            marketTray[n][1] = marketTray[n][2];
            marketTray[n][2] = marketTray[n][3];
            marketTray[n][3] = temp;
        }
    }

    /**
     * This method discards a Marbles from the ones taken from the Market
     * @param marbles is the Marbles chosen from the Player that it will be discarded
     */
    public void discardMarbles(Marbles marbles) {
        buffer.remove(marbles);
    }

    /**
     * This method empties the buffer after converting Marbles into Resources
     */
    public void emptyBuffer() {
        buffer.clear();
    }

    /**
     * This method returns the Market Tray to make know the situation to player who invokes it
     * @return it is a matrix of Marbles
     */
    public Marbles[][] getMarketTray() {
        return marketTray;
    }

    /**
     * This method returns the remaining Marble from the 13 ones
     * @return it is a Marble
     */
    public Marbles getRemainingMarble() {
        return remainingMarble;
    }

    /**
     * This method returns the buffer
     * @return the vector of the taken Marbles with the method extractMarbles
     */
    public List<Marbles> getBuffer() {
        return buffer;
    }
}
