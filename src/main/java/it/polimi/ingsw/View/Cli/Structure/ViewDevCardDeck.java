package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewDevCardDeck {

    PlayerBoard pb;

    private static final int MAX_VERT_TILES = 7; //rows.
    private static final int MAX_HORIZ_TILES = 29; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    List<String > weapons = new ArrayList<>();

    public ViewDevCardDeck(PlayerBoard playerBoard){
        pb = playerBoard;
        fillTiles();
        updateObject();
    }

    private void fillTiles(){
        for(int i=0; i<MAX_VERT_TILES; i++){
            for(int j=0; j<MAX_HORIZ_TILES; j++){
                tiles[i][j]=" ";
            }
        }
        //first column
        tiles[0][0] = "╔";
        tiles[1][0] = "║";
        tiles[2][0] = "╠";
        tiles[3][0] = "║";
        tiles[4][0] = "╠";
        tiles[5][0] = "║";
        tiles[6][0] = "╚";

        //second column
        tiles[0][7] = "╦";
        tiles[1][7] = "║";
        tiles[2][7] = "╬";
        tiles[3][7] = "║";
        tiles[4][7] = "╬";
        tiles[5][7] = "║";
        tiles[6][7] = "╩";

        //third column
        tiles[0][14] = "╦";
        tiles[1][14] = "║";
        tiles[2][14] = "╬";
        tiles[3][14] = "║";
        tiles[4][14] = "╬";
        tiles[5][14] = "║";
        tiles[6][14] = "╩";

        //fourth column
        tiles[0][21] = "╦";
        tiles[1][21] = "║";
        tiles[2][21] = "╬";
        tiles[3][21] = "║";
        tiles[4][21] = "╬";
        tiles[5][21] = "║";
        tiles[6][21] = "╩";

        //second column
        tiles[0][28] = "╗";
        tiles[1][28] = "║";
        tiles[2][28] = "╣";
        tiles[3][28] = "║";
        tiles[4][28] = "╣";
        tiles[5][28] = "║";
        tiles[6][28] = "╝";

        //horizontal separators
        for(int i=0; i<7; i=i+2){
            for(int j=1; j<23; j=j+7){
                tiles[i][j] = "═";
                tiles[i][j+1] = "═";
                tiles[i][j+2] = "═";
                tiles[i][j+3] = "═";
                tiles[i][j+4] = "═";
                tiles[i][j+5] = "═";
            }
        }
    }

    private void updateObject(){

        for(int i=1; i<6; i=i+2){
            for(int j=1; j<23; j=j+7){
                    if(pb.getDevCardDeck()[i/2][j/6][0]!=null) {
                        tiles[i][j] = String.valueOf(pb.getDevCardDeck()[i / 2][j / 6][0].charAt(0));
                        tiles[i][j + 1] = String.valueOf(pb.getDevCardDeck()[i / 2][j / 6][0].charAt(1));
                        tiles[i][j + 2] = String.valueOf(pb.getDevCardDeck()[i / 2][j / 6][0].charAt(2));
                        tiles[i][j + 3] = String.valueOf(pb.getDevCardDeck()[i / 2][j / 6][0].charAt(3));
                        tiles[i][j + 4] = String.valueOf(pb.getDevCardDeck()[i / 2][j / 6][0].charAt(4));
                        tiles[i][j + 5] = String.valueOf(pb.getDevCardDeck()[i / 2][j / 6][0].charAt(5));
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
