package Stub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.card.leadereffect.ChestLeader;
import it.polimi.ingsw.card.leadereffect.DiscountLeader;
import it.polimi.ingsw.card.leadereffect.ProductionLeader;
import it.polimi.ingsw.card.leadereffect.TrasformationMarbleLeader;
import it.polimi.ingsw.game.LeaderCardDeck;
import it.polimi.ingsw.producible.*;
import it.polimi.ingsw.requirements.RequestedLevelDevelopmentCards;
import it.polimi.ingsw.requirements.RequestedResources;
import it.polimi.ingsw.requirements.RequestedTypeDevelopmentCards;
import it.polimi.ingsw.requirements.Requirements;

import java.io.IOException;
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

        String discount = Files.readString(Paths.get("src/cardJSON/leaderCard/VettDiscountLeader_LeaderCards.json"));
        DiscountLeader[] discountLeaders = gson.fromJson(discount, DiscountLeader[].class);

        String chest = Files.readString(Paths.get("src/cardJSON/leaderCard/VettChestLeader_LeaderCards.json"));
        ChestLeader[] chestLeaders = gson.fromJson(chest, ChestLeader[].class);

        String production = Files.readString(Paths.get("src/cardJSON/leaderCard/VettProductionLeader_LeaderCards.json"));
        ProductionLeader[] productionLeaders = gson.fromJson(production, ProductionLeader[].class);

        String transformation = Files.readString(Paths.get("src/cardJSON/leaderCard/VettTrasformationMarbleLeader_LeaderCards.json"));
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
