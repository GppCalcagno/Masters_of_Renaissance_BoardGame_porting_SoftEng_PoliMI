package it.polimi.ingsw.Network.message;

public class MessageUpdateMarketTray extends Message{
    private static final long serialVersionUID = 1842562788535859189L;
    private char direction;
    private int num;

    public MessageUpdateMarketTray(String nickname, char direction, int num) {
        super(nickname, MessageType.UPDATEMARKETTRAY);
        this.direction=direction;
        this.num=num;
    }

    public char getDirection() {
        return direction;
    }

    public int getNum() {
        return num;
    }
}
