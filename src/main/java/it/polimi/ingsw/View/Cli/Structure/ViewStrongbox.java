package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewStrongbox {
    private static final int MAX_VERT_TILES = 9; //rows.
    private static final int MAX_HORIZ_TILES = 8; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    List<String > weapons = new ArrayList<>();

    public ViewStrongbox(){
        fillTiles();
        loadObject();
        updateObject();
    }

    private void fillTiles() {
        //fill with blank spaces
        for (int i = 0; i < MAX_VERT_TILES; i++) {
            for (int j = 0; j < MAX_HORIZ_TILES; j++) {
                tiles[i][j] = " ";
            }
        }

        //first column
        tiles[0][0] = "╔";
        tiles[1][0] = "║";
        tiles[2][0] = "╠";
        tiles[3][0] = "║";
        tiles[4][0] = "╠";
        tiles[5][0] = "║";
        tiles[6][0] = "╠";
        tiles[7][0] = "║";
        tiles[8][0] = "╚";

        //second column
        tiles[0][7] = "╗";
        tiles[1][7] = "║";
        tiles[2][7] = "╣";
        tiles[3][7] = "║";
        tiles[4][7] = "╣";
        tiles[5][7] = "║";
        tiles[6][7] = "╣";
        tiles[7][7] = "║";
        tiles[8][7] = "╝";

        //horizontal separators
        for(int i=0; i<9; i=i+2){
            for(int j=1; j<7; j++){
                tiles[i][j] = "═";
            }
        }

        //x simbols
        for(int i=1; i<8; i=i+2) tiles[i][4] = "X";

    }

    private void loadObject(){

        weapons.add(Color.ANSI_BRIGHTYELLOW.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape());
        weapons.add(Color.ANSI_BRIGHDARK.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape());
        weapons.add(Color.ANSI_BRIGHTPURPLE.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape());
        weapons.add(Color.ANSI_BRIGHTBLUE.escape() + "●" + Color.ANSI_BRIGHTWHITE.escape());

    }

    private void updateObject(){
        //todo
        int k=0;

        for(int i=1; i<8; i=i+2){
            tiles[i][2] = weapons.get(k);
            k++;
        }

        for(int i=1; i<8; i=i+2) tiles[i][5] = String.valueOf(i);
    }

    public void plot() {
        System.out.print( Color.ANSI_BRIGHTWHITE.escape());
        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES; c++) {
                System.out.print(tiles[r][c]);
            }
        }
    }
}
