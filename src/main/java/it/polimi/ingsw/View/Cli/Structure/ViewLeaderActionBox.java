package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

public class ViewLeaderActionBox {

    PlayerBoard pb;

    private static final int MAX_VERT_TILES = 3; //rows.
    private static final int MAX_HORIZ_TILES = 25; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public ViewLeaderActionBox(PlayerBoard playerBoard){
        pb = playerBoard;
        checkNumber();
    }

    private void checkNumber(){
        if(pb.getLeaderCard().size()<3){
            fillTilesGame();
            updateObjectGame();
        }
        else{
            fillTilesInit();
            updateObjectinit();
        }
    }

    private void fillTilesInit(){
        for(int i=0; i<MAX_VERT_TILES; i++){
            for(int j=0; j<MAX_HORIZ_TILES; j++){
                tiles[i][j] = " ";
            }
        }

        //first column
        tiles[0][0] = "╔";
        tiles[1][0] = "║";
        tiles[2][0] = "╚";

        //second column
        tiles[0][6] = "╦";
        tiles[1][6] = "║";
        tiles[2][6] = "╩";

        //third column
        tiles[0][12] = "╦";
        tiles[1][12] = "║";
        tiles[2][12] = "╩";

        //fourth column
        tiles[0][18] = "╦";
        tiles[1][18] = "║";
        tiles[2][18] = "╩";

        //fifth column
        tiles[0][24] = "╗";
        tiles[1][24] = "║";
        tiles[2][24] = "╝";

        for(int i=0; i<3; i=i+2){
            for(int j=1; j<20; j=j+6){
                tiles[i][j] = "═";
                tiles[i][j+1] = "═";
                tiles[i][j+2] = "═";
                tiles[i][j+3] = "═";
                tiles[i][j+4] = "═";
            }
        }
    }

    private void updateObjectinit() {
        for(int j=1; j<20; j=j+6){
            tiles[1][j] = String.valueOf(pb.getLeaderCard().get(j/6).charAt(0));
            tiles[1][j+1] = String.valueOf(pb.getLeaderCard().get(j/6).charAt(1));
            tiles[1][j+2] = String.valueOf(pb.getLeaderCard().get(j/6).charAt(2));
            tiles[1][j+3] = String.valueOf(pb.getLeaderCard().get(j/6).charAt(3));
            tiles[1][j+4] = String.valueOf(pb.getLeaderCard().get(j/6).charAt(4));

        }
    }

    private void fillTilesGame(){
        for(int i=0; i<MAX_VERT_TILES; i++){
            for(int j=0; j<MAX_HORIZ_TILES; j++){
                tiles[i][j] = " ";
            }
        }

        //first column
        tiles[0][0] = "╔";
        tiles[1][0] = "║";
        tiles[2][0] = "╚";

        //second column
        tiles[0][6] = "╦";
        tiles[1][6] = "║";
        tiles[2][6] = "╩";

        //third column
        tiles[0][12] = "╗";
        tiles[1][12] = "║";
        tiles[2][12] = "╝";

        for(int i=0; i<3; i=i+2){
            for(int j=1; j<8; j=j+6){
                tiles[i][j] = "═";
                tiles[i][j+1] = "═";
                tiles[i][j+2] = "═";
                tiles[i][j+3] = "═";
                tiles[i][j+4] = "═";
            }
        }
    }

    private void updateObjectGame(){
        if(pb.getLeaderCard().size()!=0) {
            for (int j = 1; j < 8; j = j + 6) {
                    tiles[1][j] = String.valueOf(pb.getLeaderCard().get(j / 6).charAt(0));
                    tiles[1][j + 1] = String.valueOf(pb.getLeaderCard().get(j / 6).charAt(1));
                    tiles[1][j + 2] = String.valueOf(pb.getLeaderCard().get(j / 6).charAt(2));
                    tiles[1][j + 3] = String.valueOf(pb.getLeaderCard().get(j / 6).charAt(3));
                    tiles[1][j + 4] = String.valueOf(pb.getLeaderCard().get(j / 6).charAt(4));
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
