package it.polimi.ingsw.Network.message;

import it.polimi.ingsw.model.marbles.Marbles;

import java.util.List;

public class MessageExtractedMarbles extends Message{
    private static final long serialVersionUID = -3848730508677921232L;

    private List<Marbles> marblesList;

    public MessageExtractedMarbles(String nickname, List<Marbles> marblesList) {
        super(nickname, MessageType.EXTRACTEDMARBLESLIST);
        this.marblesList = marblesList;
    }

    public List<Marbles> getMarblesList() {
        return marblesList;
    }
}
