package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewFaithTrack {

    PlayerBoard pb;
    String nickname;

    private static final int MAX_VERT_TILES = 9; //rows.
    private static final int MAX_HORIZ_TILES = 126; //cols.

    private static String color1 = Color.ANSI_BRIGHDARK.escape();
    private static String color2 = Color.ANSI_BRIGHDARK.escape();
    private static String color3 = Color.ANSI_BRIGHDARK.escape();

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public ViewFaithTrack(PlayerBoard playerBoard, String nickname){
        this.nickname = nickname;
        pb = playerBoard;
        fillTiles();
        updateObject();
    }

    private void fillTiles(){

        String green = Color.ANSI_GREEN.escape();
        String red = Color.ANSI_BRIGHTRED.escape();


        //fill with blank spaces
        for (int i = 0; i < MAX_VERT_TILES; i++) {
            for (int j = 0; j < MAX_HORIZ_TILES; j++) {
                tiles[i][j] = " ";
            }
        }

        //line of the track
        for(int j=0; j<MAX_HORIZ_TILES-1; j=j+5){
            tiles[5][j] = "╚";
            tiles[5][j+1] = "═";
            tiles[5][j+2] = "═";
            tiles[5][j+3] = "═";
            tiles[5][j+4] = "╝";
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
        if(pb.getPlayerList().size()>1){
        for(String name : pb.getPlayerList()){
            if(pb.getPlayersPopFavoriteTile().get(name)[0]){
                if(pb.getPlayersPopFavoriteTile().get(nickname)[0]) color1 = green;
                else color1 = red;
            }
        }
        }
        else{
            if(pb.getBlackCrossToken()>=8 && pb.getPlayersPopFavoriteTile().get(nickname)[0]) color1 = green;
            else if(pb.getBlackCrossToken()>=8 && !pb.getPlayersPopFavoriteTile().get(nickname)[0]) color1 = red;
        }

        tiles[7][34] = Color.ANSI_BRIGHTYELLOW.escape() +"2"+color1;
        tiles[6][25] = color1 + "║";
        tiles[7][25] = color1 + "║";
        tiles[8][25] = color1+ "╚";
        tiles[8][26] = "═";
        tiles[8][27] = "═";
        tiles[8][28] = "═";
        tiles[8][29] = "═";
        tiles[8][30] = "═";
        tiles[8][31] = "═";
        tiles[8][32] = "═";
        tiles[8][33] = "═";
        tiles[8][34] = "═";
        tiles[8][35] = "═";
        tiles[8][36] = "═";
        tiles[8][37] = "═";
        tiles[8][38] = "═";
        tiles[8][39] = "═";
        tiles[8][40] = "═";
        tiles[8][41] = "═";
        tiles[8][42] = "═";
        tiles[8][43] = "═";
        tiles[8][44] = "╝";
        tiles[7][44] = "║";
        tiles[6][44] = "║";
        tiles[6][42] = "✞";


        //pope space 2

        if(pb.getPlayerList().size()>1){
            for(String name : pb.getPlayerList()){
                if(pb.getPlayersPopFavoriteTile().get(name)[1]){
                    if(pb.getPlayersPopFavoriteTile().get(nickname)[1]) color2 = green;
                    else color2 = red;
                }
            }
        }
        else{
            if(pb.getBlackCrossToken()>=16 && pb.getPlayersPopFavoriteTile().get(nickname)[1]) color2 = green;
            else if(pb.getBlackCrossToken()>=16 && !pb.getPlayersPopFavoriteTile().get(nickname)[1]) color2 = red;
        }

        tiles[7][72] = Color.ANSI_BRIGHTYELLOW.escape() +"3" + color2;
        tiles[6][60] = color2 + "║";
        tiles[7][60] = color2 + "║";
        tiles[8][60] = color2 + "╚";
        tiles[8][61] = "═";
        tiles[8][62] = "═";
        tiles[8][63] = "═";
        tiles[8][64] = "═";
        tiles[8][65] = "═";
        tiles[8][66] = "═";
        tiles[8][67] = "═";
        tiles[8][68] = "═";
        tiles[8][69] = "═";
        tiles[8][70] = "═";
        tiles[8][71] = "═";
        tiles[8][72] = "═";
        tiles[8][73] = "═";
        tiles[8][74] = "═";
        tiles[8][75] = "═";
        tiles[8][76] = "═";
        tiles[8][77] = "═";
        tiles[8][78] = "═";
        tiles[8][79] = "═";
        tiles[8][80] = "═";
        tiles[8][81] = "═";
        tiles[8][82] = "═";
        tiles[8][83] = "═";
        tiles[8][84] = "╝";
        tiles[7][84] = "║";
        tiles[6][84] = "║";
        tiles[6][82] = "✞";

        //pope space 3

        if(pb.getPlayerList().size()>1){
            for(String name : pb.getPlayerList()){
                if(pb.getPlayersPopFavoriteTile().get(name)[2]){
                    if(pb.getPlayersPopFavoriteTile().get(nickname)[2]) color3 = green;
                    else color3 = red;
                }
            }
        }
        else{
            if(pb.getBlackCrossToken()>=24 && pb.getPlayersPopFavoriteTile().get(nickname)[2]) color3 = green;
            else if(pb.getBlackCrossToken()>=24 && !pb.getPlayersPopFavoriteTile().get(nickname)[2]) color3 = red;
        }

        tiles[7][109] = Color.ANSI_BRIGHTYELLOW.escape() +"4" + color3;
        tiles[6][95] = color3 + "║";
        tiles[7][95] = color3 + "║";
        tiles[8][95] = color3 + "╚";
        tiles[8][96] = "═";
        tiles[8][97] = "═";
        tiles[8][98] = "═";
        tiles[8][99] = "═";
        tiles[8][100] = "═";
        tiles[8][101] = "═";
        tiles[8][102] = "═";
        tiles[8][103] = "═";
        tiles[8][104] = "═";
        tiles[8][105] = "═";
        tiles[8][106] = "═";
        tiles[8][107] = "═";
        tiles[8][108] = "═";
        tiles[8][109] = "═";
        tiles[8][110] = "═";
        tiles[8][111] = "═";
        tiles[8][112] = "═";
        tiles[8][113] = "═";
        tiles[8][114] = "═";
        tiles[8][115] = "═";
        tiles[8][116] = "═";
        tiles[8][117] = "═";
        tiles[8][118] = "═";
        tiles[8][119] = "═";
        tiles[8][120] = "═";
        tiles[8][121] = "═";
        tiles[8][122] = "═";
        tiles[8][123] = "═";
        tiles[8][124] = "╝" + Color.ANSI_BRIGHTWHITE.escape();
        tiles[7][124] = "║";
        tiles[6][124] = "║";
        tiles[6][122] = "✞";
    }

    private void updateObject() {
        if (pb.getPlayerList().size() > 1) {
            int i = 4;
            for (String player : pb.getPlayerList()) {
                if (pb.getPlayersFaithMarkerPosition().get(player) < 25) {
                    tiles[i][2 + pb.getPlayersFaithMarkerPosition().get(player) * 5] = "♔";  //♙
                    tiles[i][125] = player;
                    i--;
                }
            }
        }
        else if(pb.getPlayerList().size()==1){
            if(pb.getPlayersFaithMarkerPosition().get(pb.getCurrentPlayer())<25) tiles[4][2 + pb.getPlayersFaithMarkerPosition().get(pb.getCurrentPlayer())*5 ] = "♔";
            tiles[4][125] = pb.getCurrentPlayer();
            if(pb.getBlackCrossToken()<25) tiles[3][2 + pb.getBlackCrossToken()*5] = "♔";
            tiles[3][125] = "Lorenzo";
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
