package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewStrongbox {

    PlayerBoard pb;

    private static final int MAX_VERT_TILES = 9; //rows.
    private static final int MAX_HORIZ_TILES = 9; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    List<String > weapons = new ArrayList<>();

    public ViewStrongbox(PlayerBoard playerBoard){
        pb = playerBoard;
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
        tiles[0][8] = "╗";
        tiles[1][8] = "║";
        tiles[2][8] = "╣";
        tiles[3][8] = "║";
        tiles[4][8] = "╣";
        tiles[5][8] = "║";
        tiles[6][8] = "╣";
        tiles[7][8] = "║";
        tiles[8][8] = "╝";

        //horizontal separators
        for(int i=0; i<9; i=i+2){
            for(int j=1; j<8; j++){
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

        tiles[1][2] = weapons.get(0);
        tiles[3][2] = weapons.get(1);
        tiles[5][2] = weapons.get(2);
        tiles[7][2] = weapons.get(3);

        if(pb.getStrongbox().get("Servants")!=null){
            if(pb.getStrongbox().get("Servants")>9) {
                tiles[1][5] = String.valueOf(pb.getStrongbox().get("Servants") / 10);
                tiles[1][6] = String.valueOf(pb.getStrongbox().get("Servants") % 10);
            }
            else tiles[1][6] = String.valueOf(pb.getStrongbox().get("Servants"));
        }
        else tiles[1][6] = "0";
        if(pb.getStrongbox().get("Shields")!=null){
            if(pb.getStrongbox().get("Shields")>9) {
                tiles[3][5] = String.valueOf(pb.getStrongbox().get("Shields") / 10);
                tiles[3][6] = String.valueOf(pb.getStrongbox().get("Shields") % 10);
            }
            else tiles[3][6] = String.valueOf(pb.getStrongbox().get("Shields"));
        }
        else tiles[3][6] = "0";
        if(pb.getStrongbox().get("Coins")!=null){
            if(pb.getStrongbox().get("Coins")>9) {
                tiles[5][5] = String.valueOf(pb.getStrongbox().get("Coins") / 10);
                tiles[5][6] = String.valueOf(pb.getStrongbox().get("Coins") % 10);
            }
            else tiles[5][6] = String.valueOf(pb.getStrongbox().get("Coins"));
        }
        else tiles[5][6] = "0";
        if(pb.getStrongbox().get("Stones")!=null){
            if(pb.getStrongbox().get("Stones")>9) {
                tiles[7][5] = String.valueOf(pb.getStrongbox().get("Stones") / 10);
                tiles[7][6] = String.valueOf(pb.getStrongbox().get("Stones") % 10);
            }
            else tiles[7][6] = String.valueOf(pb.getStrongbox().get("Stones"));
        }
        else tiles[7][6] = "0";

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
