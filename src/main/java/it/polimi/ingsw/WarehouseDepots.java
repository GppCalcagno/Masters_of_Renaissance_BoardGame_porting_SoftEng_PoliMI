package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Optional;

public class WarehouseDepots {
    /**
     * sizex and sizey represent the size of the warehouse
     **/
    private final int sizex = 3;
    private final int sizey = 3;
    /**
     * The warehouse is a diagonal matrix, where the constructor block three
     * cells in order to trasform the warehouse
     */
    private Resources[][] warehouse;

    /**
     * The list leaderCardEffect is not null when the player has a leader card
     * that add extra chest to the warehouse.
     * It is optional because it could be null if the player has not a leader
     * card with that ability, or maybe he has not activated it yet
     */
    private ExtraChest leaderCardEffect; //da rivedere
    Optional<ExtraChest> optionalLeaderCardEffect = Optional.of(leaderCardEffect);

    /**
     * this is the constructor, the warehouse is created as a matrix then transform
     * in a diagonal matrix
     */
    public WarehouseDepots(){
        warehouse = new Resources[sizex][sizey];
        warehouse[0][0] = null;
        warehouse[0][1] = null;
        warehouse[1][0] = null;

    }

    /**
     *
     * @param column the caller wants to know how mani resources are in that
     *      *               column
     * @return an integer: the number of resources
     */
    public int getWarehouseNum(int column){
        return 0;
    }

    /**
     * this method check if an insertion from the market into the warehouse
     * is legal in accord with the rules of the game
     * @param column the column where i want put my resources
     * @param res the type of Resources i want insert
     */
    public void checkInsertion(int column, Resources res){

    }

    /**
     * this method allow to insert a rescousces in the warehouse matrix
     * @param column where
     * @param res
     */
    public void insertResousces (int column, Resources res){

    }

    /**
     * this method check if the player can do a change of depots in accord with the rules
     * @param column1 the first column that player want change
     * @param column2 the second column that player want to change
     */
    public void checkExchange( int column1, int column2){

    }

    /**
     * this method does the exchange if checkExchange allows it
     * @param column1 the first column that player want change
     * @param column2 the second column that player want to change
     */
    public void exchange(int column1, int column2){

    }

    /**
     * this method delete the resources inside the warehouse when player use them
     * @param column where a resource is located
     */
    public void delete(int column){

    }

}
