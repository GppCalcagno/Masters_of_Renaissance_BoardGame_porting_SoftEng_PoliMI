package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewWarehouse {

    String[][] warehouse;

    private static final int MAX_VERT_TILES = 7; //rows.
    private static final int MAX_HORIZ_TILES = 13; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public ViewWarehouse(String[][] warehouse){
        this.warehouse = warehouse;
        fillTiles();
        updateObject();
    }

    private void fillTiles(){
        //fill with blank spaces
        for(int i=0; i<MAX_VERT_TILES; i++){
            for (int j=0; j<MAX_HORIZ_TILES; j++){
                tiles[i][j]=" ";
            }
        }

        //first line
        tiles[0][0] = "╔";
        tiles[0][1] = "═";
        tiles[0][2] = "═";
        tiles[0][3] = "═";
        tiles[0][4] = "╗";

        //second line
        tiles[2][0] = "╠";
        tiles[2][1] = "═";
        tiles[2][2] = "═";
        tiles[2][3] = "═";
        tiles[2][4] = "╬";
        tiles[2][5] = "═";
        tiles[2][6] = "═";
        tiles[2][7] = "═";
        tiles[2][8] = "╗";

        //third line
        tiles[4][0] = "╠";
        tiles[4][1] = "═";
        tiles[4][2] = "═";
        tiles[4][3] = "═";
        tiles[4][4] = "╬";
        tiles[4][5] = "═";
        tiles[4][6] = "═";
        tiles[4][7] = "═";
        tiles[4][8] = "╬";
        tiles[4][9] = "═";
        tiles[4][10] = "═";
        tiles[4][11] = "═";
        tiles[4][12] = "╗";

        //fourth line
        tiles[6][0] = "╚";
        tiles[6][1] = "═";
        tiles[6][2] = "═";
        tiles[6][3] = "═";
        tiles[6][4] = "╩";
        tiles[6][5] = "═";
        tiles[6][6] = "═";
        tiles[6][7] = "═";
        tiles[6][8] = "╩";
        tiles[6][9] = "═";
        tiles[6][10] = "═";
        tiles[6][11] = "═";
        tiles[6][12] = "╝";

        //vertical separators
        tiles[1][0] = "║";
        tiles[1][4] = "║";
        tiles[3][0] = "║";
        tiles[3][4] = "║";
        tiles[3][8] = "║";
        tiles[5][0] = "║";
        tiles[5][4] = "║";
        tiles[5][8] = "║";
        tiles[5][12] = "║";
    }

    private void updateObject(){
        for(int i=1; i<6; i=i+2){
            for(int j=2; j<(i*2)+1;j=j+4){
                if(warehouse[i/2][j/4]!=null) {
                    if (warehouse[i / 2][j / 4].equals("Servants"))
                        tiles[i][j] = Color.ANSI_BRIGHTPURPLE.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape();
                    else if (warehouse[i / 2][j / 4].equals("Shields"))
                        tiles[i][j] = Color.ANSI_BRIGHTBLUE.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape();
                    else if (warehouse[i / 2][j / 4].equals("Coins"))
                        tiles[i][j] = Color.ANSI_BRIGHTYELLOW.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape();
                    else if (warehouse[i / 2][j / 4].equals("Stones"))
                        tiles[i][j] = Color.ANSI_BRIGHDARK.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape();
                }
            }
        }
    }

    public void plot() {
        System.out.print( Color.ANSI_BRIGHTWHITE.escape());
        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES; c++) {
                System.out.print(tiles[r][c]);
            }
        }
        System.out.println("\n");
    }
}
