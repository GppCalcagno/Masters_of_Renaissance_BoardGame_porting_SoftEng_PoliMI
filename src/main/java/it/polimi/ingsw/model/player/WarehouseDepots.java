package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.card.leadereffect.ExtraChest;
import it.polimi.ingsw.model.marbles.Marbles;
import it.polimi.ingsw.model.producible.Resources;

import java.util.ArrayList;
import java.util.List;


public class WarehouseDepots {
    /**
     * sizex and sizey represent the size of the warehouse
     */
    private final int sizex = 3;
    private final int sizey = 3;
    /** The warehouse is a diagonal matrix, where the constructor block three
     * cells in order to trasform the warehouse
     */
    private Resources[][] warehouse;

    /**
     * The list leaderCardEffect is not null when the player has a leader card, that add extra chest to the warehouse.
     */
    private List<ExtraChest> leaderCardEffect;


    /**
     *this attribute is the list of marbles just extracted from the markettray
     */
    private List<Marbles> buffer;

    /** this is the constructor, the warehouse is created as a matrix then transform
     * in a diagonal matrix
     */
    public WarehouseDepots(){
        leaderCardEffect= new ArrayList<>();
        buffer= new ArrayList<>();
        warehouse = new Resources[sizex][sizey];
        warehouse[0][1] = null;
        warehouse[0][2] = null;
        warehouse[1][2] = null;
    }



    /** this method check if an insertion from the market into the warehouse
     * is legal in accord with the rules of the game
     * @param row the row where i want put my resources
     * @param res the type of Resources i want insert
     */
    public boolean checkInsertion(int row, Resources res){
        //controllo indice
        if(row>2) return false;

        //controllo che la riga non sia usata da altre biglie
        if(warehouse[row][0]!=null && !warehouse[row][0].toString().equals(res.toString())) return false;

        //controlla se non esista nessun'altra pallina di quel tipo in altre righe
        for(int i=0;i<3;i++) {
            if (row != i && warehouse[i][0] != null && warehouse[i][0].toString().equals(res.toString()))
                return false;
        }


        return insertResources(row,res);
    }

    /** this method allow to insert a resource in the warehouse matrix
     * @param row where
     * @param res
     * @return
     */

    public boolean insertResources (int row, Resources res) {
        int i = 0;
        while (i<3 && warehouse[row][i]!=null){ i++;}

        if(i<row+1){
            warehouse[row][i] = res;
            return true;
        }
        return false;
    }

    /** this method check if the player can do a change of depots in accord with the rules
     * @param row1 the first row that player want change
     * @param row2 the second row that player want to change
     */
    public boolean checkExchange( int row1, int row2){
        int dim1 = 0;
        int dim2= 0;

        while (dim1<3 &&warehouse[row1][dim1]!=null){ dim1++;}
        while (dim2<3 &&warehouse[row2][dim2]!=null){ dim2++;}

        if(dim1>row2+1 || dim2>row1+1)return false;

        int max;
        if(dim1>dim2) max=dim1; else  max=dim2;

        for(int i=0; i<max; i++){
            Resources temp= warehouse[row1][i];

            warehouse[row1][i]=warehouse[row2][i];
            warehouse[row2][i]= temp;
        }
        return true;
    }

    /**
     * this method delete the resources inside the warehouse when player use them
     * @param res the resources you want delete.
     */
    public boolean delete(Resources res){
        int i = -1;
        int j = warehouse.length-1;

        for(int x=0;x<sizex;x++){ if(warehouse[x][0]!= null && warehouse[x][0].getClass().equals(res.getClass()))i=x;}
        if(i==-1) return false;
        else{
            while (warehouse[i][j]==null) j--;
            warehouse[i][j]=null;
            return true;
        }
    }

    /**
     * this method add the effect of activeted LeaderCard
     * @param resources type of resource stored
     */
    public void addleaderCardEffect(Resources resources) {
        leaderCardEffect.add(new ExtraChest(resources));
    }

    /**
     * this method is a getter of the number of a resource in the warehouse and ExtraChest
     * @param res type of resource
     * @return the number of the selected resouce
     */
    public int getNumResources(Resources res){
        int sum=0;
        int j=0;

        for (ExtraChest chest: leaderCardEffect){
            if(chest.getResources().getClass().equals(res.getClass())){
                sum+=chest.getnum();
            }
        }

        for(int x=0;x<sizex;x++){
            if(warehouse[x][0]!= null && warehouse[x][0].getClass().equals(res.getClass())){
                while(j<sizey && warehouse[x][j]!=null)j++;
            }
            sum+=j;
            j=0;
        }
        return sum;
    }

    /**
     * this method is a getter of the number of a resource in the warehouse
     * @param res type of resource
     * @return the number of the selected resouce
     */
    public int getWarehouseNumResources(Resources res) {
        int sum = 0;
        int index = -1;

        int j=0;
        for (int x = 0; x < sizex; x++) {
            if (warehouse[x][0] != null && warehouse[x][0].getClass().equals(res.getClass())) {
                while (j < sizey && warehouse[x][j] != null) j++;
            }
            sum += j;
            j = 0;
        }
        return sum;
    }

    public List<ExtraChest> getLeaderCardEffect(){
        return leaderCardEffect;
    }

    public Resources[][] getWarehouse() {
        return warehouse;
    }

    public List<Marbles> getBuffer() {
        return buffer;
    }

    public void addToBuffer(Marbles marbles){
        buffer.add(marbles);
    }

    public void removeFirstfromBuffer(){
        buffer.remove(0);
    }
}