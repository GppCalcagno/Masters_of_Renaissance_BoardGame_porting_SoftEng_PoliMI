package Stub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.card.LeaderAction;
import it.polimi.ingsw.model.card.leadereffect.ChestLeader;
import it.polimi.ingsw.model.card.leadereffect.DiscountLeader;
import it.polimi.ingsw.model.card.leadereffect.ProductionLeader;
import it.polimi.ingsw.model.card.leadereffect.TrasformationMarbleLeader;
import it.polimi.ingsw.model.game.LeaderCardDeck;
import it.polimi.ingsw.model.producible.*;
import it.polimi.ingsw.model.requirements.RequestedLevelDevelopmentCards;
import it.polimi.ingsw.model.requirements.RequestedResources;
import it.polimi.ingsw.model.requirements.RequestedTypeDevelopmentCards;
import it.polimi.ingsw.model.requirements.Requirements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderCardDeckStub extends LeaderCardDeck {
    private List<LeaderAction> leaderCardList;

    public LeaderCardDeckStub() throws IOException {
        this.leaderCardList = new ArrayList<>();

        RuntimeTypeAdapterFactory<Requirements> requirementsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Requirements.class)
                .registerSubtype(RequestedResources.class)
                .registerSubtype(RequestedLevelDevelopmentCards.class)
                .registerSubtype(RequestedTypeDevelopmentCards.class);
        RuntimeTypeAdapterFactory<Resources> resourcesRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Resources.class)
                .registerSubtype(Coins.class)
                .registerSubtype(Servants.class)
                .registerSubtype(Shields.class)
                .registerSubtype(Stones.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(requirementsRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(resourcesRuntimeTypeAdapterFactory)
                .create();

        Reader discount = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettDiscountLeader_LeaderCards.json"));
        //String discount = Files.readString(Paths.get("cardJSON/leaderCard/VettDiscountLeader_LeaderCards.json"));
        DiscountLeader[] discountLeaders = gson.fromJson(discount, DiscountLeader[].class);

        Reader chest = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettChestLeader_LeaderCards.json"));
        //String chest = Files.readString(Paths.get("cardJSON/leaderCard/VettChestLeader_LeaderCards.json"));
        ChestLeader[] chestLeaders = gson.fromJson(chest, ChestLeader[].class);

        Reader production = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettProductionLeader_LeaderCards.json"));
        //String production = Files.readString(Paths.get("cardJSON/leaderCard/VettProductionLeader_LeaderCards.json"));
        ProductionLeader[] productionLeaders = gson.fromJson(production, ProductionLeader[].class);

        Reader transformation = new InputStreamReader(LeaderAction.class.getResourceAsStream("/cardJSON/leaderCard/VettTrasformationMarbleLeader_LeaderCards.json"));
        //String transformation = Files.readString(Paths.get("cardJSON/leaderCard/VettTrasformationMarbleLeader_LeaderCards.json"));
        TrasformationMarbleLeader[] transformationMarbleLeaders = gson.fromJson(transformation, TrasformationMarbleLeader[].class);

        this.leaderCardList.addAll(Arrays.asList(discountLeaders));
        this.leaderCardList.addAll(Arrays.asList(chestLeaders));
        this.leaderCardList.addAll(Arrays.asList(productionLeaders));
        this.leaderCardList.addAll(Arrays.asList(transformationMarbleLeaders));
    }

    public LeaderAction getLeaderCardList(int i) {
        return leaderCardList.get(i);
    }

    public List<LeaderAction> getLeaderCardList(){
        return this.leaderCardList;
    }
}
