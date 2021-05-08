package it.polimi.ingsw.Network.message;

public class MessageRequestNumPlayers extends Message{
    private static final long serialVersionUID = 5203402987810310308L;

    public MessageRequestNumPlayers(String nickname) {
        super(nickname, MessageType.REQUESTNUMPLAYERS);
    }
}
