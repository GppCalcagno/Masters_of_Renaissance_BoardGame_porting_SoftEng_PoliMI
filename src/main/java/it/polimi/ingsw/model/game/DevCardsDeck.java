package it.polimi.ingsw.model.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.card.ColorCard;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DevCardsDeck {
    /**
     * this is the cube where the development cards are allocated
     * Row = Level
     *      row 2 <-> level 1
     *      row 1 <-> level 2
     *      row 0 <-> level 3
     *
     * Column = Color
     * See getColumnFromColor
     */
    private DevelopmentCard[][][] developmentCardDeck;

    /**
     * this is the constructor that initialized the cube as 4*3*4 in accord with the rules
     */
    public DevCardsDeck() throws IOException {
        developmentCardDeck = new DevelopmentCard[3][4][4];

        Gson gson = new GsonBuilder().create();

        String devCards3g = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3green.json"));
        DevelopmentCard[] devCards3gvet = gson.fromJson(devCards3g, DevelopmentCard[].class);
        List<DevelopmentCard> devCards3glist = Arrays.asList(devCards3gvet);
        Collections.shuffle(devCards3glist);
        devCards3glist.toArray(devCards3gvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[0][0][z] = devCards3gvet[z];
        }

        String devCards3b = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3blue.json"));
        DevelopmentCard[] devCards3bvet = gson.fromJson(devCards3b, DevelopmentCard[].class);
        List<DevelopmentCard> devCards3blist = Arrays.asList(devCards3bvet);
        Collections.shuffle(devCards3blist);
        devCards3blist.toArray(devCards3bvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[0][1][z] = devCards3bvet[z];
        }

        String devCards3y = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3yellow.json"));
        DevelopmentCard[] devCards3yvet = gson.fromJson(devCards3y, DevelopmentCard[].class);
        List<DevelopmentCard> devCards3ylist = Arrays.asList(devCards3yvet);
        Collections.shuffle(devCards3ylist);
        devCards3ylist.toArray(devCards3yvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[0][2][z] = devCards3yvet[z];
        }

        String devCards3p = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level3purple.json"));
        DevelopmentCard[] devCards3pvet = gson.fromJson(devCards3p, DevelopmentCard[].class);
        List<DevelopmentCard> devCards3plist = Arrays.asList(devCards3pvet);
        Collections.shuffle(devCards3plist);
        devCards3plist.toArray(devCards3pvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[0][3][z] = devCards3pvet[z];
        }

        String devCards2g = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2green.json"));
        DevelopmentCard[] devCards2gvet = gson.fromJson(devCards2g, DevelopmentCard[].class);
        List<DevelopmentCard> devCards2glist = Arrays.asList(devCards2gvet);
        Collections.shuffle(devCards2glist);
        devCards2glist.toArray(devCards2gvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[1][0][z] = devCards2gvet[z];
        }

        String devCards2b = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2blue.json"));
        DevelopmentCard[] devCards2bvet = gson.fromJson(devCards2b, DevelopmentCard[].class);
        List<DevelopmentCard> devCards2blist = Arrays.asList(devCards2bvet);
        Collections.shuffle(devCards2blist);
        devCards2blist.toArray(devCards2bvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[1][1][z] = devCards2bvet[z];
        }

        String devCards2y = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2yellow.json"));
        DevelopmentCard[] devCards2yvet = gson.fromJson(devCards2y, DevelopmentCard[].class);
        List<DevelopmentCard> devCards2ylist = Arrays.asList(devCards2yvet);
        Collections.shuffle(devCards2ylist);
        devCards2ylist.toArray(devCards2yvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[1][2][z] = devCards2yvet[z];
        }

        String devCards2p = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level2purple.json"));
        DevelopmentCard[] devCards2pvet = gson.fromJson(devCards2p, DevelopmentCard[].class);
        List<DevelopmentCard> devCards2plist = Arrays.asList(devCards2pvet);
        Collections.shuffle(devCards2plist);
        devCards2plist.toArray(devCards2pvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[1][3][z] = devCards2pvet[z];
        }

        String devCards1g = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1green.json"));
        DevelopmentCard[] devCards1gvet = gson.fromJson(devCards1g, DevelopmentCard[].class);
        List<DevelopmentCard> devCards1glist = Arrays.asList(devCards1gvet);
        Collections.shuffle(devCards1glist);
        devCards1glist.toArray(devCards1gvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[2][0][z] = devCards1gvet[z];
        }

        String devCards1b = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1blue.json"));
        DevelopmentCard[] devCards1bvet = gson.fromJson(devCards1b, DevelopmentCard[].class);
        List<DevelopmentCard> devCards1blist = Arrays.asList(devCards1bvet);
        Collections.shuffle(devCards1blist);
        devCards1blist.toArray(devCards1bvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[2][1][z] = devCards1bvet[z];
        }

        String devCards1y = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1yellow.json"));
        DevelopmentCard[] devCards1yvet = gson.fromJson(devCards1y, DevelopmentCard[].class);
        List<DevelopmentCard> devCards1ylist = Arrays.asList(devCards1yvet);
        Collections.shuffle(devCards1ylist);
        devCards1ylist.toArray(devCards1yvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[2][2][z] = devCards1yvet[z];
        }

        String devCards1p = Files.readString(Paths.get("src/cardJSON/developmentCard/devCards_level1purple.json"));
        DevelopmentCard[] devCards1pvet = gson.fromJson(devCards1p, DevelopmentCard[].class);
        List<DevelopmentCard> devCards1plist = Arrays.asList(devCards1pvet);
        Collections.shuffle(devCards1plist);
        devCards1plist.toArray(devCards1pvet);
        for(int z = 0; z < 4; z++){
            developmentCardDeck[2][3][z] = devCards1pvet[z];
        }
    }

    /**
     * this method return the card that is at the top of the cube
     * @param x row
     * @param y column
     * @return the card
     */
    public DevelopmentCard getDevCards(int x, int y) throws NoSuchElementException {
        if(developmentCardDeck[x][y][0]!=null) return developmentCardDeck[x][y][0];
        else throw new NoSuchElementException();
    }

    /**
     * this method remove the card at the top of the cube
     * @param row row
     * @param column column
     */
    public boolean removeDevCards(int row, int column){
        int i=0;
        if(developmentCardDeck[row][column][0]==null) return false;
        else {
            while (i <= developmentCardDeck.length-1 && developmentCardDeck[row][column][i + 1] != null) i++;
            for (int j = 0; j < i; j++) {
                developmentCardDeck[row][column][j] = developmentCardDeck[row][column][j + 1];
            }
            developmentCardDeck[row][column][i]=null;
            return true;
        }
    }

    /**
     * this method assign the card at the top of the cube to the player who purchase it
     * @param player player who calls
     * @param playercolumn for SlotDevCards' insertCards
     * @param level level of the row of the matrix - reverse of the level of card. levelMatrix = 0 --> levelCard = 3
     * @param color color of the required card - equivalent to the column of the matrix
     */
    public boolean purchaseCards(Player player, int playercolumn, int level, int color) throws GameFinishedException {
        if (developmentCardDeck[level][color][0]!=null){
            if(player.getSlotDevCards().insertCards(playercolumn, developmentCardDeck[level][color][0])){
                removeDevCards(level,color);
                return true;
            }
            else return false;
        }
        else return false;
    }

    /**
     * this method return the index of the column from a color
     * @param color the color where cards of that color are stored
     * @return the column of that color, in order whit the rules.
     */
    public int getColumnFromColor(ColorCard color){
        int i = 0;
        if(color == ColorCard.GREEN) i=0;
        else if(color == ColorCard.BLUE) i=1;
        else if(color == ColorCard.YELLOW) i=2;
        else if(color == ColorCard.PURPLE) i=3;
        return i;
    }

    /**
     * a get of the developmentCardDeck
     * @return developmentCardDeck
     */
    public DevelopmentCard[][][] getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    /**
     * This method searches a Development card from its ID
     * @param ID is the identifier
     * @return a DevelopmentCard object
     */
    public DevelopmentCard getDevCardFromID (String ID) {

        for (DevelopmentCard[][] decks : developmentCardDeck) {
            for (DevelopmentCard[] deckrow : decks) {
                for (DevelopmentCard card : deckrow) {
                    if (card.getID().equals(ID))
                        return card;
                }
            }
        }
        return null;
    }
}
