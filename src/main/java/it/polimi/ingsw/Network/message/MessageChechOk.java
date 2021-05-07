package it.polimi.ingsw.Network.message;

public class MessageChechOk extends Message{
    private static final long serialVersionUID = 8964831408927170793L;
    private boolean check;

    public MessageChechOk(String nickname, boolean check) {
        super(nickname, MessageType.CHECKOK);
        this.check=check;
    }

    public boolean isCheck() {
        return check;
    }
}
