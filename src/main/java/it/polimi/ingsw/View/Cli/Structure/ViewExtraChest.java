package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewExtraChest {

    PlayerBoard pb;

    private static final int MAX_VERT_TILES = 3; //rows.
    private static final int MAX_HORIZ_TILES = 9; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public ViewExtraChest(PlayerBoard playerBoard){
        pb = playerBoard;
        fillTiles();
        updateObject();
    }

    private void fillTiles(){
        for(int i=0; i<MAX_VERT_TILES; i++){
            for(int j=0; j<MAX_HORIZ_TILES; j++){
                tiles[i][j] = " ";
            }
        }

        //first line
        tiles[0][0] = "╔";
        tiles[0][1] = "═";
        tiles[0][2] = "═";
        tiles[0][3] = "═";
        tiles[0][4] = "╦";
        tiles[0][5] = "═";
        tiles[0][6] = "═";
        tiles[0][7] = "═";
        tiles[0][8] = "╗";

        //second line
        tiles[2][0] = "╚";
        tiles[2][1] = "═";
        tiles[2][2] = "═";
        tiles[2][3] = "═";
        tiles[2][4] = "╩";
        tiles[2][5] = "═";
        tiles[2][6] = "═";
        tiles[2][7] = "═";
        tiles[2][8] = "╝";

        //vertical separators
        tiles[1][0] = "║";
        tiles[1][4] = "║";
        tiles[1][8] = "║";
    }

    private void updateObject() {

        String color;

        if (pb.getExtrachest().get("Servants")!=null) {
            color = Color.ANSI_BRIGHTPURPLE.escape();
        }
        else if(pb.getExtrachest().get("Shields")!=null){
            color = Color.ANSI_BRIGHTBLUE.escape();
        }
        else if(pb.getExtrachest().get("Coins")!=null){
            color = Color.ANSI_BRIGHTYELLOW.escape();
        }
        else if(pb.getExtrachest().get("Stones")!=null){
            color = Color.ANSI_BRIGHDARK.escape();
        }
        else color = Color.ANSI_BRIGHTWHITE.escape();

        tiles[1][2] = color + "●" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[1][6] = color + "●" + Color.ANSI_BRIGHTWHITE.escape();
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
