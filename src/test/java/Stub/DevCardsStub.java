package Stub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderAction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DevCardsStub {
    private List<DevelopmentCard> cardList;

    public DevCardsStub() throws IOException {

        cardList = new ArrayList<>();

        Gson gson = new GsonBuilder().create();

        Reader devCards1gin = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/ExampleCardsJSON/DevCards1g.json"));
        //String devCards1gin = Files.readString(Paths.get("cardJSON/ExampleCardsJSON/DevCards1g.json"));
        DevelopmentCard devCards1g = gson.fromJson(devCards1gin, DevelopmentCard.class);

        Reader devCards2pin = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/ExampleCardsJSON/DevCards2p.json"));
        //String devCards2pin = Files.readString(Paths.get("cardJSON/ExampleCardsJSON/DevCards2p.json"));
        DevelopmentCard devCards2p = gson.fromJson(devCards2pin, DevelopmentCard.class);

        Reader devCards3bin = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/ExampleCardsJSON/DevCards3b.json"));
        //String devCards3bin = Files.readString(Paths.get("cardJSON/ExampleCardsJSON/DevCards3b.json"));
        DevelopmentCard devCards3b = gson.fromJson(devCards3bin, DevelopmentCard.class);

        cardList.add(devCards1g);
        cardList.add(devCards2p);
        cardList.add(devCards3b);
    }

    public List<DevelopmentCard> getCardList() {
        return cardList;
    }
}
