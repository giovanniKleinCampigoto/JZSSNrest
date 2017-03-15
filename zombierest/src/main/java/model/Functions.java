/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author giovanni
 */
public class Functions {

    private static SurvivorCrud sc;
    private static Survivor sr;
    private static ArrayList<Survivor> array = new ArrayList<>();
    private static Map<String, Integer> map;
    private static ArrayList<Integer> infected = new ArrayList<>();

    /**
     * Flags an survivor as infected and deletes it's inventory after 3 reports
     *
     * @param flagger Survivor flagging infected one
     * @param flaggedSurvivor The flagged survivor
     */
    public void flagInfectedSurvivor(Survivor flagger, Survivor flaggedSurvivor) {
        Survivor surv = null;
        Inventory inventory = null;

        if (flagger.equals(flaggedSurvivor)) {
            System.out.println("A survivor can't flag himself");
        }
        if (surv == null) {
            surv = flagger;
            System.out.println("Survivor " + flaggedSurvivor.getName() + " flagged...");
            array.add(surv);
            surv = null;
            if (array.size() == 3) {
            	sc = new SurvivorCrud();

                flaggedSurvivor.setInfected(true);
                System.out.println(flaggedSurvivor.getName() + " is infected...");
                
                inventory = sc.getInventoryById(flaggedSurvivor.getIdsurvivor());                
                int points = inventory.getAmmo() + inventory.getFood() * 3 + inventory.getMeds() * 2 + inventory.getWater() * 4;

                infected.add(points);
                sc.updateSurvivor(flaggedSurvivor);
                sc.deleteSurvivorInventory(flaggedSurvivor);

                sc = null;
                array.clear();
            }
        }
    }

    /**
     *
     */
    public void initializeTable(Map map) {
        map.put("water", 4);
        map.put("food", 3);
        map.put("meds", 2);
        map.put("ammo", 1);
    }

    /**
     * Adds survivors in the database with their respective inventories
     */
    public void addSurvivorsToDatabase() {
        sc.saveSurvivors();
        sc.saveInventories();
    }

    /**
     * Percentage of infected/non-infected survivors
     *
     * @param value number of survivors
     * @return returns percentage
     */
    public double percentage(int value) {
        double per = value;

        per = per * value / 100;
        return per;
    }

    /**
     * Updates a random survivor location
     */
    public void updateSurvivorLocation() {
        sr = sc.retrieveRandomSurvivor();
        sr.setLonx(2000);
        sr.setLony(-500);
        sc.updateSurvivor(sr);
    }

    /**
     * Trade function (You can only trade type of item each time)
     *
     * @param trader Survivor that is trading
     * @param wantsToTrade Survivor that is wanting to trade
     * @param tradeT trade item
     * @param ammountT trade item ammount
     * @param tradeWTT want's to trade item
     * @param ammountWTT want's to trade ammount
     */
    public void trade(Survivor trader, Survivor wantsToTrade, String tradeT, int ammountT, String tradeWTT, int ammountWTT) {
        Inventory invT, invWTT;
        sc = new SurvivorCrud();
        map = new HashMap<>();
        initializeTable(map);

        invT = sc.getInventoryById(trader.getIdsurvivor());
        invWTT = sc.getInventoryById(wantsToTrade.getIdsurvivor());

        System.out.println("Survivor " + wantsToTrade.getName() + " wants to trade " + tradeWTT + " X [" + ammountWTT + "] " + " with " + trader.getName() + " for " + tradeT + " X [" + ammountT + "] ");

        int pointsT, pointsWTT;

        //verifies itens ammount in Trader inventory
        if (ammountT > sc.getItemsQuantity(invT, tradeT)) {
            System.out.println("Itens ammount from TRADER it's too damn high!");
            //verifies itens ammount in WTT inventory

        } else if (ammountWTT > sc.getItemsQuantity(invWTT, tradeWTT)) {
            System.out.println("Itens ammount from WANT'S TO TRADE it's too damn high!");
        } else {
            pointsT = ammountT * map.get(tradeT);
            pointsWTT = ammountWTT * map.get(tradeWTT);

            if (pointsT == pointsWTT) {

                int tradedAmmountT, tradedAmmountWTT, aux1, aux2;

                tradedAmmountT = Math.abs(sc.getItemsQuantity(invT, tradeWTT) - ammountWTT);
                tradedAmmountWTT = Math.abs(sc.getItemsQuantity(invWTT, tradeT) - ammountT);

                sc.setTradeInventory(invT, tradeWTT, tradedAmmountT);
                sc.setTradeInventory(invWTT, tradeT, tradedAmmountWTT);

                aux1 = Math.abs(sc.getItemsQuantity(invT, tradeT) - ammountT);
                aux2 = Math.abs(sc.getItemsQuantity(invWTT, tradeWTT) - ammountWTT);

                sc.setTradeInventory(invT, tradeT, aux1);
                sc.setTradeInventory(invWTT, tradeWTT, aux2);

                sc.updateInventory(invWTT);
                sc.updateInventory(invT);

                System.out.println("Trade is completed...");
            }

        }

    }
    
    public ArrayList<Integer> getInfected(){
        return infected;
    }
    
}
