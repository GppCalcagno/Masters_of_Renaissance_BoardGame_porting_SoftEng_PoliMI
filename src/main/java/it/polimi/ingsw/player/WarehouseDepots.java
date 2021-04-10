package it.polimi.ingsw.player;

import it.polimi.ingsw.card.leadereffect.ExtraChest;
import it.polimi.ingsw.producible.Resources;

import java.util.Arrays;
import java.util.List;

public class WarehouseDepots {
    /** sizex and sizey represent the size of the warehouse
     */
    private final int sizex = 3;
    private final int sizey = 3;
    /** The warehouse is a diagonal matrix, where the constructor block three
     * cells in order to trasform the warehouse
     */
    private Resources[][] warehouse;

    /** The list leaderCardEffect is not null when the player has a leader card
     * that add extra chest to the warehouse.
     */
    private List<ExtraChest> leaderCardEffect;

    /** this is the constructor, the warehouse is created as a matrix then transform
     * in a diagonal matrix
     */
    public WarehouseDepots(){
        warehouse = new Resources[sizex][sizey];
        warehouse[2][1] = null;
        warehouse[1][1] = null;
        warehouse[2][0] = null;

    }

    /**
     * @param row the caller wants to know how many resources are in that
     *      *               row
     * @return an integer: the number of resources
     */
    public int getWarehouseNum(int row){
        return (int) Arrays.stream(warehouse[row]).filter(i -> i !=null).count();
    }

    /** this method check if an insertion from the market into the warehouse
     * is legal in accord with the rules of the game
     * @param row the column where i want put my resources
     * @param res the type of Resources i want insert
     */
    public void checkInsertion(int row, Resources res) throws CantDoIt{
        int i = 0;
        if(warehouse[row][0]!=null) {
            if (res.getClass().equals(warehouse[row][0].getClass())) {
                insertResources(row, res);
            }
            else throw new CantDoIt("Can't Insert");

        }
        else insertResources(row, res);
    }

    /** this method allow to insert a resource in the warehouse matrix
     * @param row where
     * @param res
     */
    public void insertResources (int row, Resources res) {
        int i = 0;
        while (warehouse[row][i]!=null){
            i++;
        }
        if(i<=row) warehouse[row][i] = res;
    }

    /** this method check if the player can do a change of depots in accord with the rules
     * @param row1 the first column that player want change
     * @param row2 the second column that player want to change
     */
    public void checkExchange( int row1, int row2) throws CantDoIt{
        if(getWarehouseNum(row1)<=row2+1 && getWarehouseNum(row2)<=row1+1){
            if(getWarehouseNum(row1)>=getWarehouseNum(row2)) exchange(row1, row2);
            else exchange(row2, row1);
        }
        else throw new CantDoIt("Can't Do the Exchange");


    }

    /** this method does the exchange if checkExchange allows it
     * @param row1 the first column that player want change
     * @param row2 the second column that player want to change
     */
    public void exchange(int row1, int row2){
        Resources[] vet = new Resources[warehouse[row1].length];
        for(int i=0; i<getWarehouseNum(row1); i++){
            vet[i] = warehouse[row1][i];
            warehouse[row1][i] = warehouse[row2][i];
            warehouse[row2][i] = vet[i];
        }

    }

    /**
     * this method delete the resources inside the warehouse when player use them
     * @param res the resources you want delete
     */
    public void delete(Resources res){
        int i = 0;
        int j = warehouse.length-1;

        while(warehouse[i][0]==null || !res.getClass().equals(warehouse[i][0].getClass()) || i>warehouse.length-1){
            i++;
        }
        if(i>warehouse.length-1) System.out.println("This Resource isn't located in the warehouse");
        else{
            while (warehouse[i][j]==null) j--;
            warehouse[i][j]=null;
        }
    }

    /*public void addLeaderCardEffect(Resources res) throws CantDoIt{
        if(leaderCardEffect.getnum()==2) throw new CantDoIt("Can't Insert new Resources");
        else{
            if(leaderCardEffect.getResources().getClass().equals(res.getClass())) leaderCardEffect.updateResources(leaderCardEffect.getnum()+1);
            else throw new CantDoIt("Different Resources, can't add");
        }
    }

    public void show(){
        System.out.println(leaderCardEffect.getnum());
    }*/

}