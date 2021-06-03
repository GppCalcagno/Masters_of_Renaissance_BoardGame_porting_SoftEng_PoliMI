package it.polimi.ingsw.model.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.marbles.*;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MarketStructure {
    /**
     * These attributes are the sizes of rows and .columns of the Market Tray, in which there are the Market Marbles
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


    /**
     * This is the constructor method
     */
    public MarketStructure() throws IOException {
        marketTray = new Marbles[sizex][sizey];

        RuntimeTypeAdapterFactory<Marbles> marblesRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Marbles.class)
                .registerSubtype(WhiteMarble.class)
                .registerSubtype(BlueMarble.class)
                .registerSubtype(GreyMarble.class)
                .registerSubtype(RedMarble.class)
                .registerSubtype(YellowMarble.class)
                .registerSubtype(PurpleMarble.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(marblesRuntimeTypeAdapterFactory)
                .create();

        Reader marblesString = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/Marbles.json"));
        //String marblesString = Files.readString(Paths.get("cardJSON/Marbles.json"));
        Marbles[] marblesvet = gson.fromJson(marblesString, Marbles[].class);
        List<Marbles> marblesList = Arrays.asList(marblesvet);

        Collections.shuffle(marblesList);

        int k = 0;
        for(int i = 0; i < sizex; i++){
            for (int j = 0; j < sizey; j++){
                this.marketTray[i][j] = marblesList.get(k);
                k++;
            }
        }
        // Avrei voluto invocare la remove da marblesList, ma dava errori.
        // Con quel metodo mi sarei risparmiato quella var "k"
        this.remainingMarble = marblesList.get(k);
    }

    /**
     * This method returns the row or the column of Marbles, chosen by the player
     * @param direction indicates if the player want to extract a row or a column of Marbles
     * @param n indicates the number of the row or of the column chosen
     */
    public boolean extractMarbles (Player player, char direction, int n) {
        // 'c' = colonna, 'r' = riga

        if( direction == 'C' ) {
            if( n >= 0 && n <= sizey ) {
                for(int i = 0; i < sizex; i++) {
                    player.getWarehouse().addToBuffer(marketTray[i][n]);
                }
                insertMarble(direction, n);
                return true;
            }
            else {
                return false;
            }
        }
        else if ( direction == 'R' ) {
            if( n >= 0 && n <= sizex ) {
                for(int j = 0; j < sizey; j++) {
                    player.getWarehouse().addToBuffer(marketTray[n][j]);
                }
                insertMarble(direction, n);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * This method inserts the remaining Marble into the row or the column chosen with extractMarbles
     * @param direction indicates if the player has chosen a row or a column
     * @param n indicates the number of the row or the column chosen
     */
    public void insertMarble (char direction, int n) {
        // non faccio controlli sulla validità dei parametri, perchè già fatti in extractMarbles
        if( direction == 'C') {
            Marbles temp = remainingMarble;
            remainingMarble = marketTray[0][n];
            marketTray[0][n] = marketTray[1][n];
            marketTray[1][n] = marketTray[2][n];
            marketTray[2][n] = temp;
        }
        else if( direction == 'R') {
            Marbles temp = remainingMarble;
            remainingMarble = marketTray[n][0];
            marketTray[n][0] = marketTray[n][1];
            marketTray[n][1] = marketTray[n][2];
            marketTray[n][2] = marketTray[n][3];
            marketTray[n][3] = temp;
        }
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


}
