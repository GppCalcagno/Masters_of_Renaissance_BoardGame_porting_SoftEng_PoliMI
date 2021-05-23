package it.polimi.ingsw.model.requirements;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;


public class RequirementsProduction implements Requirements {
    /**
     * this attribute is the Map of the required resources
     */
    private HashMap<String, Integer> reqMap = new HashMap<>();

    /**
     * check if the card Production requirements are met,
     * it also takes away the resources from the player
     * @param player is the player who uses the Card
     * @return 1 if the resources requirements are met, 0 otherwise
     */
    @Override
    public boolean checkResources(Player player) {
        int currentRes;
        //qui ho hardcodato le risorse (nel caso fosse possibile cambiare)
        Resources[] type = new Resources[4];
        type[0] = new Coins();
        type[1] = new Servants();
        type[2] = new Stones();
        type[3] = new Shields();

        for (Resources res : type) {
            currentRes = 0;
            if (reqMap.containsKey(res.toString())) {
                currentRes = player.getStrongbox().getNumResources(res) + player.getWarehouse().getNumResources(res);

                if (currentRes < reqMap.get(res.toString()))
                    return false;
            }
        }
        return true;
    }

    @Override
    public void showReq() {
        System.out.println(reqMap);
    }

    /**
     * this method is a getter of the RequirementsProduction Map of the card
     * @return the RequirementsProductionMap
     */
    public Map<String, Integer> getReqMap() {
        return reqMap;
    }

    /**
     * this methos adds a request to the card characteristics
     * @param rec type of resource requested
     * @param num num of resource requested
     */
    public void addRequirementsProduction(Resources rec, int num){
        reqMap.put(rec.toString(),num);
    }

    /**
     * check if the card purchase requirements are met,
     * it also takes away the resources from the player
     * @param player is the player who uses the Card
     * @return 1 if the resources requirements are met, 0 otherwise
     */
    public boolean checkBuy(Player player) {
        HashMap<String, Integer> tempMap= (HashMap<String, Integer>) reqMap.clone();

        for (Resources res: player.getLeaderCardEffectDiscount()) {
            if (tempMap.containsKey(res.toString())) {
                if(tempMap.get(res.toString())>0){
                    tempMap.put(res.toString(),tempMap.get(res.toString())-1);
                }
            }
        }

        int currentRes;
        //qui ho hardcodato le risorse (nel caso fosse possibile cambiare)
        Resources[] type = new Resources[4];
        type[0] = new Coins();
        type[1] = new Servants();
        type[2] = new Stones();
        type[3] = new Shields();

        for (Resources res : type) {
            currentRes = 0;
            if (tempMap.containsKey(res.toString())) {
                currentRes = player.getStrongbox().getNumResources(res) + player.getWarehouse().getNumResources(res);
                if (currentRes < tempMap.get(res.toString()))
                    return false;
            }
        }
        return true;
    }

    /**
     * this methos check if list of resources are equals to requirements
     * @param warehouseMap list of resoureces on WareHouse
     * @param strongboxMap list of resources on StrongBox
     * @return
     */
    public boolean checkResources(Player player,Map<String,Integer> warehouseMap, Map<String,Integer> strongboxMap, Map<String,Integer> extraChestMap, boolean isBuying) {
        //qui ho hardcodato le risorse
        Resources[] listResources = {new Coins(), new Servants(), new Shields(), new Stones()};

        HashMap<String, Integer> tempMap = (HashMap<String, Integer>) reqMap.clone();


        if(isBuying) {
            for (Resources res : player.getLeaderCardEffectDiscount()) {
                if (tempMap.containsKey(res.toString())) {
                    if(tempMap.get(res.toString())>0)
                        tempMap.put(res.toString(), tempMap.get(res.toString()) - 1);
                }
            }
        }

        for (String x : tempMap.keySet()) {
            if (warehouseMap.containsKey(x)) tempMap.put(x, tempMap.get(x) - warehouseMap.get(x));
            if (strongboxMap.containsKey(x)) tempMap.put(x, tempMap.get(x) - strongboxMap.get(x));
            if (extraChestMap.containsKey(x)) tempMap.put(x, tempMap.get(x) - extraChestMap.get(x));

        }

        //tutte le risorse sono state indicate nella giusta quantità
        for (String x : tempMap.keySet()) {
            if (tempMap.get(x) != 0) return false;
        }


        //non sono state indicate risorse extra
        for(String x: warehouseMap.keySet()){
            if(!tempMap.containsKey(x))return false;
        }

        for(String x: strongboxMap.keySet()){
            if(!tempMap.containsKey(x))return false;
        }

        for(String x: extraChestMap.keySet()){
            if(!tempMap.containsKey(x))return false;
        }
        return true;
    }



}
