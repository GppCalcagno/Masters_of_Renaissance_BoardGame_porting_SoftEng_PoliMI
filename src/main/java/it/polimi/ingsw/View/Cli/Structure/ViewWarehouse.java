package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewWarehouse {
    private static final int MAX_VERT_TILES = 7; //rows.
    private static final int MAX_HORIZ_TILES = 13; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    List<String > weapons = new ArrayList<>();

    public ViewWarehouse(){
        fillTiles();
        loadObject();
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

    private void loadObject(){
        for(int i=0; i<6; i++) weapons.add("●");
    }

    private void updateObject(){
        //todo collegamenti a oggetti esistenti
        int k=1;

        tiles[1][2] = Color.ANSI_BRIGHTYELLOW.escape() + weapons.get(k) + Color.ANSI_BRIGHTWHITE.escape();
        tiles[3][2] = Color.ANSI_BRIGHTPURPLE.escape() + weapons.get(k) + Color.ANSI_BRIGHTWHITE.escape();
        tiles[3][6] = Color.ANSI_BRIGHTPURPLE.escape() + weapons.get(k) + Color.ANSI_BRIGHTWHITE.escape();
        tiles[5][2] = Color.ANSI_BRIGHDARK.escape() + weapons.get(k) + Color.ANSI_BRIGHTWHITE.escape();
        tiles[5][6] = Color.ANSI_BRIGHDARK.escape() + weapons.get(k) + Color.ANSI_BRIGHTWHITE.escape();
        tiles[5][10] = Color.ANSI_BRIGHDARK.escape() + weapons.get(k) + Color.ANSI_BRIGHTWHITE.escape();

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
