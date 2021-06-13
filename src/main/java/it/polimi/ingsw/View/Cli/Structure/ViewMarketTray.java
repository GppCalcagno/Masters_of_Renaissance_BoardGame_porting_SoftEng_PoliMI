package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewMarketTray {

    PlayerBoard pb;

    private static final int MAX_VERT_TILES = 11; //rows.
    private static final int MAX_HORIZ_TILES = 22; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    List<String > weapons = new ArrayList<>();


    public ViewMarketTray(PlayerBoard playerBoard) {
        pb = playerBoard;
        fillTiles();
        updateObjects();
    }


    private void fillTiles() {

        for(int i=0; i<MAX_VERT_TILES; i++){
            for(int j=0; j<MAX_HORIZ_TILES; j++){
                tiles[i][j]=" ";
            }
        }
        //spazio per la biglia 'fuori'
        tiles[0][17] = "╔";
        tiles[0][18] = "═";
        tiles[0][19] = "═";
        tiles[0][20] = "═";
        tiles[0][21] = "╗";
        tiles[1][17] = "║";
        tiles[1][21] = "║";
        tiles[2][17] = "╚";
        tiles[2][18] = "═";
        tiles[2][19] = "═";
        tiles[2][20] = "═";
        tiles[2][21] = "╝";


        //prima riga struttura principale
        tiles[3][0] = "╔";
        tiles[3][1] = "═";
        tiles[3][2] = "═";
        tiles[3][3] = "═";
        tiles[3][4] = "╦";
        tiles[3][5] = "═";
        tiles[3][6] = "═";
        tiles[3][7] = "═";
        tiles[3][8] = "╦";
        tiles[3][9] = "═";
        tiles[3][10] = "═";
        tiles[3][11] = "═";
        tiles[3][12] = "╦";
        tiles[3][13] = "═";
        tiles[3][14] = "═";
        tiles[3][15] = "═";
        tiles[3][16] = "╗";

        //seconda riga struttura principale
        tiles[5][0] = "╠";
        tiles[5][1] = "═";
        tiles[5][2] = "═";
        tiles[5][3] = "═";
        tiles[5][4] = "╬";
        tiles[5][5] = "═";
        tiles[5][6] = "═";
        tiles[5][7] = "═";
        tiles[5][8] = "╬";
        tiles[5][9] = "═";
        tiles[5][10] = "═";
        tiles[5][11] = "═";
        tiles[5][12] = "╬";
        tiles[5][13] = "═";
        tiles[5][14] = "═";
        tiles[5][15] = "═";
        tiles[5][16] = "╣";

        //terza riga struttura principale
        tiles[7][0] = "╠";
        tiles[7][1] = "═";
        tiles[7][2] = "═";
        tiles[7][3] = "═";
        tiles[7][4] = "╬";
        tiles[7][5] = "═";
        tiles[7][6] = "═";
        tiles[7][7] = "═";
        tiles[7][8] = "╬";
        tiles[7][9] = "═";
        tiles[7][10] = "═";
        tiles[7][11] = "═";
        tiles[7][12] = "╬";
        tiles[7][13] = "═";
        tiles[7][14] = "═";
        tiles[7][15] = "═";
        tiles[7][16] = "╣";

        //quarta riga struttura principale
        tiles[9][0] = "╚";
        tiles[9][1] = "═";
        tiles[9][2] = "═";
        tiles[9][3] = "═";
        tiles[9][4] = "╩";
        tiles[9][5] = "═";
        tiles[9][6] = "═";
        tiles[9][7] = "═";
        tiles[9][8] = "╩";
        tiles[9][9] = "═";
        tiles[9][10] = "═";
        tiles[9][11] = "═";
        tiles[9][12] = "╩";
        tiles[9][13] = "═";
        tiles[9][14] = "═";
        tiles[9][15] = "═";
        tiles[9][16] = "╝";

        //separatori verticali
        for(int i=0; i<3; i++){
            for(int j=0; j<17; j=j+4){
                tiles[2*(2+i)][j] = "║";
            }
        }

        //frecce sx
        tiles[4][18] = "<";
        tiles[4][19] = "-";
        tiles[6][18] = "<";
        tiles[6][19] = "-";
        tiles[8][18] = "<";
        tiles[8][19] = "-";

        //frecce up
        tiles[10][2] = "⇑";
        tiles[10][6] = "⇑";
        tiles[10][10] = "⇑";
        tiles[10][14] = "⇑";

    }


    private void updateObjects(){

        String color;

        //remaining marble
        if ("Servants".equals(pb.getRemainingMarble())){
            color = Color.ANSI_BRIGHTPURPLE.escape();
        }
        else if("Shields".equals(pb.getRemainingMarble())){
            color = Color.ANSI_BRIGHTBLUE.escape();
        }
        else if("Coins".equals(pb.getRemainingMarble())){
            color = Color.ANSI_BRIGHTYELLOW.escape();
        }
        else if("Stones".equals(pb.getRemainingMarble())){
            color = Color.ANSI_BRIGHDARK.escape();
        }
        else if("FaithMarker".equals(pb.getRemainingMarble())){
            color = Color.ANSI_BRIGHTRED.escape();
        }
        else color = Color.ANSI_BRIGHTWHITE.escape();

        tiles[1][19] = color + "●" + Color.ANSI_BRIGHTWHITE.escape();


        //market tray
        for(int i=4; i<9; i=i+2){
            for(int j=2; j<15; j=j+4){
                if ("Servants".equals(pb.getMarketTray()[(i-4)/2][(j-2)/4])) {
                    color = Color.ANSI_BRIGHTPURPLE.escape();
                }
                else if("Shields".equals(pb.getMarketTray()[(i-4)/2][(j-2)/4])){
                    color = Color.ANSI_BRIGHTBLUE.escape();
                }
                else if("Coins".equals(pb.getMarketTray()[(i-4)/2][(j-2)/4])){
                    color = Color.ANSI_BRIGHTYELLOW.escape();
                }
                else if("Stones".equals(pb.getMarketTray()[(i-4)/2][(j-2)/4])){
                    color = Color.ANSI_BRIGHDARK.escape();
                }
                else if("FaithMarker".equals(pb.getMarketTray()[(i-4)/2][(j-2)/4])){
                    color = Color.ANSI_BRIGHTRED.escape();
                }
                else color = Color.ANSI_BRIGHTWHITE.escape();

                tiles[i][j] = color + "●" + Color.ANSI_BRIGHTWHITE.escape();
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
