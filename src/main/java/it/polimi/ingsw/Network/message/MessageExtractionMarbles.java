package it.polimi.ingsw.Network.message;

public class MessageExtractionMarbles extends Message {
    private static final long serialVersionUID = 8042579921096981359L;

    private char colrowextract;

    private int numextract;


    public MessageExtractionMarbles(String nickname, char colrowextract, int numextract) {
        super(nickname, MessageType.EXTRACTIONMARBLES);
        this.colrowextract = colrowextract;
        this.numextract = numextract;
    }

    public char getColrowextract() {
        return colrowextract;
    }

    public int getNumextract() {
        return numextract;
    }
}
