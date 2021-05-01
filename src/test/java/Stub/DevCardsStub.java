package Stub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DevCardsStub {
    private List<DevelopmentCard> cardList;

    public DevCardsStub() throws IOException {
        Gson gson = new GsonBuilder().create();

        String devCards1gin = Files.readString(Paths.get("src/test/java/Stub/ExampleCardsJSON/DevCards1g.json"));
        DevelopmentCard devCards1g = gson.fromJson(devCards1gin, DevelopmentCard.class);

        String devCards2pin = Files.readString(Paths.get("src/test/java/Stub/ExampleCardsJSON/DevCards2p.json"));
        DevelopmentCard devCards2p = gson.fromJson(devCards2pin, DevelopmentCard.class);

        String devCards3bin = Files.readString(Paths.get("src/test/java/Stub/ExampleCardsJSON/DevCards3b.json"));
        DevelopmentCard devCards3b = gson.fromJson(devCards3bin, DevelopmentCard.class);

        cardList.add(devCards1g);
        cardList.add(devCards2p);
        cardList.add(devCards3b);
    }

    public List<DevelopmentCard> getCardList() {
        return cardList;
    }
}
