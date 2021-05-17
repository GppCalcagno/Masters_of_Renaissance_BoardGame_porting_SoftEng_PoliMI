package it.polimi.ingsw.Network.message.UpdateMesssage;

import it.polimi.ingsw.Network.message.Message;
import it.polimi.ingsw.Network.message.MessageType;

import java.util.List;

public class MessageUpdateWhiteMarbleEffect extends Message {
    private static final long serialVersionUID = -2807318014979699841L;

    private List<String> whiteMarbleEffectList;

    public MessageUpdateWhiteMarbleEffect(String nickname, List<String> whiteMarbleEffectList) {
        super(nickname, MessageType.UPDATEWHITEMARBLEEFFECT);
        this.whiteMarbleEffectList = whiteMarbleEffectList;
    }

    public List<String> getWhiteMarbleEffectList() {
        return whiteMarbleEffectList;
    }
}
