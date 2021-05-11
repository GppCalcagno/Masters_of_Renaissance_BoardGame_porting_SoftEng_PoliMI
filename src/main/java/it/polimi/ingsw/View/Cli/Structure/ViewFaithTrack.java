package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewFaithTrack {

    PlayerBoard pb;

    private static final int MAX_VERT_TILES = 6; //rows.
    private static final int MAX_HORIZ_TILES = 125; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public ViewFaithTrack(PlayerBoard playerBoard){
        pb = playerBoard;
        fillTiles();
        updateObject();
    }

    private void fillTiles(){

        String yellow = Color.ANSI_YELLOW.escape();
        String orange = Color.ANSI_RED.escape();
        String red = Color.ANSI_BRIGHTRED.escape();


        //fill with blank spaces
        for (int i = 0; i < MAX_VERT_TILES; i++) {
            for (int j = 0; j < MAX_HORIZ_TILES; j++) {
                tiles[i][j] = " ";
            }
        }

        //line of the track
        for(int j=0; j<MAX_HORIZ_TILES; j=j+5){
            tiles[2][j] = "╚";
            tiles[2][j+1] = "═";
            tiles[2][j+2] = "═";
            tiles[2][j+3] = "═";
            tiles[2][j+4] = "╝";
        }

        //points

        tiles[0][17] = Color.ANSI_BRIGHTYELLOW.escape() + "1" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][32] = Color.ANSI_BRIGHTYELLOW.escape() + "2" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][47] = Color.ANSI_BRIGHTYELLOW.escape() + "4" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][62] = Color.ANSI_BRIGHTYELLOW.escape() + "6" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][77] = Color.ANSI_BRIGHTYELLOW.escape() + "9" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][92] = Color.ANSI_BRIGHTYELLOW.escape() + "1" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][93] = Color.ANSI_BRIGHTYELLOW.escape() + "2" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][107] = Color.ANSI_BRIGHTYELLOW.escape() + "1" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][108] = Color.ANSI_BRIGHTYELLOW.escape() + "6" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][122] = Color.ANSI_BRIGHTYELLOW.escape() + "2" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[0][123] = Color.ANSI_BRIGHTYELLOW.escape() + "0" + Color.ANSI_BRIGHTWHITE.escape();

        //pope space 1
        tiles[4][34] = Color.ANSI_BRIGHTYELLOW.escape() +"2"+yellow;
        tiles[3][25] = yellow + "║";
        tiles[4][25] = yellow + "║";
        tiles[5][25] = yellow + "╚";
        tiles[5][26] = "═";
        tiles[5][27] = "═";
        tiles[5][28] = "═";
        tiles[5][29] = "═";
        tiles[5][30] = "═";
        tiles[5][31] = "═";
        tiles[5][32] = "═";
        tiles[5][33] = "═";
        tiles[5][34] = "═";
        tiles[5][35] = "═";
        tiles[5][36] = "═";
        tiles[5][37] = "═";
        tiles[5][38] = "═";
        tiles[5][39] = "═";
        tiles[5][40] = "═";
        tiles[5][41] = "═";
        tiles[5][42] = "═";
        tiles[5][43] = "═";
        tiles[5][44] = "╝";
        tiles[4][44] = "║";
        tiles[3][44] = "║";
        tiles[3][42] = "✞";


        //pope space 2
        tiles[4][72] = Color.ANSI_BRIGHTYELLOW.escape() +"3" + orange;
        tiles[3][60] = orange + "║";
        tiles[4][60] = orange + "║";
        tiles[5][60] = orange + "╚";
        tiles[5][61] = "═";
        tiles[5][62] = "═";
        tiles[5][63] = "═";
        tiles[5][64] = "═";
        tiles[5][65] = "═";
        tiles[5][66] = "═";
        tiles[5][67] = "═";
        tiles[5][68] = "═";
        tiles[5][69] = "═";
        tiles[5][70] = "═";
        tiles[5][71] = "═";
        tiles[5][72] = "═";
        tiles[5][73] = "═";
        tiles[5][74] = "═";
        tiles[5][75] = "═";
        tiles[5][76] = "═";
        tiles[5][77] = "═";
        tiles[5][78] = "═";
        tiles[5][79] = "═";
        tiles[5][80] = "═";
        tiles[5][81] = "═";
        tiles[5][82] = "═";
        tiles[5][83] = "═";
        tiles[5][84] = "╝";
        tiles[4][84] = "║";
        tiles[3][84] = "║";
        tiles[3][82] = "✞";

        //pope space 3
        tiles[4][109] = Color.ANSI_BRIGHTYELLOW.escape() +"4" + red;
        tiles[3][95] = red + "║";
        tiles[4][95] = red + "║";
        tiles[5][95] = red + "╚";
        tiles[5][96] = "═";
        tiles[5][97] = "═";
        tiles[5][98] = "═";
        tiles[5][99] = "═";
        tiles[5][100] = "═";
        tiles[5][101] = "═";
        tiles[5][102] = "═";
        tiles[5][103] = "═";
        tiles[5][104] = "═";
        tiles[5][105] = "═";
        tiles[5][106] = "═";
        tiles[5][107] = "═";
        tiles[5][108] = "═";
        tiles[5][109] = "═";
        tiles[5][110] = "═";
        tiles[5][111] = "═";
        tiles[5][112] = "═";
        tiles[5][113] = "═";
        tiles[5][114] = "═";
        tiles[5][115] = "═";
        tiles[5][116] = "═";
        tiles[5][117] = "═";
        tiles[5][118] = "═";
        tiles[5][119] = "═";
        tiles[5][120] = "═";
        tiles[5][121] = "═";
        tiles[5][122] = "═";
        tiles[5][123] = "═";
        tiles[5][124] = "╝";
        tiles[4][124] = "║";
        tiles[3][124] = "║";
        tiles[3][122] = "✞";
    }

    private void updateObject(){
        tiles[1][2+ pb.getFaithMarker()*5] = "♔";  //♙
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
