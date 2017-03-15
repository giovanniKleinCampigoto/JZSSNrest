/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author giovanni
 */
public class Initializer {

    private static Functions functions;
    private static SurvivorCrud sc;
    private static ArrayList<Survivor> survivors;
    private static Survivor sr;

    public static void main(String args[]) {

        double percentageNon_infected;
        double percentageInfected;
        int lostPoints = 0;
        ArrayList<Integer> array;
        ArrayList<Inventory> inventories = null;

        functions = new Functions();
        sc = new SurvivorCrud();
        survivors = new ArrayList<>();

        //survivors.addAll(sc.fetchSurvivorsFromDB());

        sc.saveSurvivors();
        sc.saveInventories();
       
        /*for (int i = 0; i < 3; i++) {
            functions.flagInfectedSurvivor(survivors.get(i), survivors.get(3));
        }*/
         
        //functions.trade(survivors.get(0),survivors.get(2),"water",1,"meds",2);
        /*percentageNon_infected = functions.percentage(survivors.size());
        percentageInfected = functions.percentage(functions.getInfected().size());

        array = functions.getInfected();

        for (Integer integer : array) {
            lostPoints += array.get(integer);
        }

        int avgWater, avgFood, avgMeds, avgAmmo, totalWater = 0, totalFood = 0, totalMeds = 0, totalAmmo = 0;

        inventories.addAll(sc.fetchInventoriesFromDB());

        for (int i = 0; i < inventories.size(); i++) {
            totalWater += inventories.get(i).getWater();
            totalFood += inventories.get(i).getFood();
            totalMeds += inventories.get(i).getMeds();
            totalAmmo += inventories.get(i).getAmmo();
        }

        avgWater = totalWater / inventories.size();
        avgFood = totalFood / inventories.size();
        avgMeds = totalMeds / inventories.size();
        avgAmmo = totalAmmo / inventories.size();

    }*/
    }
}
