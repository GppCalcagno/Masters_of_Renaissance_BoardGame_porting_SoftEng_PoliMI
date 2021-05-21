package it.polimi.ingsw.View.Cli.Structure;

import it.polimi.ingsw.Client.PlayerBoard;
import it.polimi.ingsw.View.Cli.Color;

public class ViewMarbleBuffer {

    PlayerBoard pb;

    private static final int MAX_HORIZ_TILES = 4; //cols.

    String tiles[] = new String[MAX_HORIZ_TILES];

    String color;

    public ViewMarbleBuffer(PlayerBoard playerBoard) {
        pb = playerBoard;
        updateObject();
    }

    private void updateObject() {
        for(int i=0; i<MAX_HORIZ_TILES; i++) tiles[i] = " ";
        for(int i=0; i<pb.getMarbleBuffer().size(); i++) {
           if (pb.getMarbleBuffer().get(i) != null) {
                if (pb.getMarbleBuffer().get(i).equals("Servants")) {
                    color = Color.ANSI_BRIGHTPURPLE.escape();
                } else if (pb.getMarbleBuffer().get(i).equals("Shields")) {
                    color = Color.ANSI_BRIGHTBLUE.escape();
                } else if (pb.getMarbleBuffer().get(i).equals("Coins")) {
                    color = Color.ANSI_BRIGHTYELLOW.escape();
                } else if (pb.getMarbleBuffer().get(i).equals("Stones")) {
                    color = Color.ANSI_BRIGHDARK.escape();
                } else if (pb.getMarbleBuffer().get(i).equals("FaithMarker")) {
                    color = Color.ANSI_RED.escape();
                } else color = Color.ANSI_BRIGHTWHITE.escape();

                tiles[i] = color + "●" + Color.ANSI_BRIGHTWHITE.escape();
            }
        }
    }

    public void plot(){
        for(int i=0; i<MAX_HORIZ_TILES; i++){
            System.out.print( "\t" +tiles[i]);
        }
        System.out.println("\n");
    }
}
