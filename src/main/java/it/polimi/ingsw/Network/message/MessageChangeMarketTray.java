package it.polimi.ingsw.Network.message;

public class MessageChangeMarketTray extends Message{
    private static final long serialVersionUID = -1886957149681741134L;

    private char colrowextract;

    private int numextract;


    public MessageChangeMarketTray(String nickname, char colrowextract, int numextract) {
        super(nickname, MessageType.CHANGEMARKETTRAY);
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
