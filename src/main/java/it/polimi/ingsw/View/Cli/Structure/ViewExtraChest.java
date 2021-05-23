package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

import java.util.ArrayList;
import java.util.List;

public class ViewExtraChest {

    PlayerBoard pb;
    String color = Color.ANSI_BRIGHTWHITE.escape();

    private static final int MAX_VERT_TILES = 3; //rows.
    private static final int MAX_HORIZ_TILES = 9; //cols.

    String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public ViewExtraChest(PlayerBoard playerBoard) {
        pb = playerBoard;
        fillTiles();
        updateObject();
    }

    private void fillTiles() {
        for (int i = 0; i < MAX_VERT_TILES; i++) {
            for (int j = 0; j < MAX_HORIZ_TILES; j++) {
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
        String resource = null;
        if(pb.getExtrachest().isEmpty()) System.out.println(Color.ANSI_RED.escape() + "You don't have an extrachest" + Color.ANSI_BRIGHTWHITE.escape());
        else {
            for (int i = 0; i < pb.getExtrachest().size(); i++) {
                if (pb.getExtrachest().containsKey("Servants")) {
                    color = Color.ANSI_BRIGHTPURPLE.escape();
                    resource = "Servants";
                } else if (pb.getExtrachest().containsKey("Shields")) {
                    color = Color.ANSI_BRIGHTBLUE.escape();
                    resource = "Shields";
                } else if (pb.getExtrachest().containsKey("Coins")) {
                    color = Color.ANSI_YELLOW.escape();
                    resource = "Coins";
                } else if (pb.getExtrachest().containsKey("Stones")) {
                    color = Color.ANSI_BRIGHDARK.escape();
                    resource = "Stones";
                }
                for (int j = 0; j < pb.getExtrachest().get(resource); j++) {
                    tiles[1][j * 4 + 2] = "●" + Color.ANSI_BRIGHTWHITE.escape();
                }
                plot();
            }

        }
    }

    public void plot() {
        if (color != null) {
            System.out.print(Color.ANSI_BRIGHTWHITE.escape());
            for (int r = 0; r < MAX_VERT_TILES; r++) {
                System.out.println();
                for (int c = 0; c < MAX_HORIZ_TILES; c++) {
                    System.out.print(color + tiles[r][c]);
                }
            }
            System.out.println(Color.ANSI_BRIGHTWHITE.escape() + "\n");
        }
    }
}