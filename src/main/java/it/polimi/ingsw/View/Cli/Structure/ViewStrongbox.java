package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewStrongbox {

    Map<String, Integer> strongbox;

    private static final int MAX_VERT_TILES = 9; //rows.
    private static final int MAX_HORIZ_TILES = 10; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    List<String > weapons = new ArrayList<>();

    public ViewStrongbox(Map<String, Integer> strongbox){
        this.strongbox = strongbox;
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
        tiles[0][9] = "╗";
        tiles[1][9] = "║";
        tiles[2][9] = "╣";
        tiles[3][9] = "║";
        tiles[4][9] = "╣";
        tiles[5][9] = "║";
        tiles[6][9] = "╣";
        tiles[7][9] = "║";
        tiles[8][9] = "╝";

        //horizontal separators
        for(int i=0; i<9; i=i+2){
            for(int j=1; j<9; j++){
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

        tiles[5][2] = weapons.get(0);
        tiles[7][2] = weapons.get(1);
        tiles[1][2] = weapons.get(2);
        tiles[3][2] = weapons.get(3);

        if(strongbox.get("Servants")!=null){
            if(strongbox.get("Servants")>99){
                tiles[1][5] = String.valueOf(strongbox.get("Servants") / 100);
                tiles[1][6] = String.valueOf((strongbox.get("Servants") % 100) /10);
                tiles[1][7] = String.valueOf(strongbox.get("Servants") %10);
            }
            else if(strongbox.get("Servants")>9 && strongbox.get("Servants")<=99) {
                tiles[1][6] = String.valueOf(strongbox.get("Servants") / 10);
                tiles[1][7] = String.valueOf(strongbox.get("Servants") % 10);
            }
            else tiles[1][7] = String.valueOf(strongbox.get("Servants"));
        }
        else tiles[1][7] = "0";
        if(strongbox.get("Shields")!=null){
            if(strongbox.get("Shields")>99){
                tiles[3][5] = String.valueOf(strongbox.get("Shields") / 100);
                tiles[3][6] = String.valueOf((strongbox.get("Shields") % 100) /10);
                tiles[3][7] = String.valueOf(strongbox.get("Shields") %10);
            }
            else if(strongbox.get("Shields")>9 && strongbox.get("Shields")<=99) {
                tiles[3][6] = String.valueOf(strongbox.get("Shields") / 10);
                tiles[3][7] = String.valueOf(strongbox.get("Shields") % 10);
            }
            else tiles[3][7] = String.valueOf(strongbox.get("Shields"));
        }
        else tiles[3][7] = "0";
        if(strongbox.get("Coins")!=null){
            if(strongbox.get("Coins")>99){
                tiles[5][5] = String.valueOf(strongbox.get("Coins") / 100);
                tiles[5][6] = String.valueOf((strongbox.get("Coins") % 100) /10);
                tiles[5][7] = String.valueOf(strongbox.get("Coins") %10);
            }
            else if(strongbox.get("Coins")>9 && strongbox.get("Coins")<=99) {
                tiles[5][6] = String.valueOf(strongbox.get("Coins") / 10);
                tiles[5][7] = String.valueOf(strongbox.get("Coins") % 10);
            }
            else tiles[5][7] = String.valueOf(strongbox.get("Coins"));
        }
        else tiles[5][7] = "0";
        if(strongbox.get("Stones")!=null){
            if(strongbox.get("Stones")>99){
                tiles[7][5] = String.valueOf(strongbox.get("Stones") / 100);
                tiles[7][6] = String.valueOf((strongbox.get("Stones") % 100) /10);
                tiles[7][7] = String.valueOf(strongbox.get("Stones") %10);
            }
            else if(strongbox.get("Stones")>9 && strongbox.get("Stones")<=99) {
                tiles[7][6] = String.valueOf(strongbox.get("Stones") / 10);
                tiles[7][7] = String.valueOf(strongbox.get("Stones") % 10);
            }
            else tiles[7][7] = String.valueOf(strongbox.get("Stones"));
        }
        else tiles[7][7] = "0";

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
