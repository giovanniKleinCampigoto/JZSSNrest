/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Inventory;
import model.Survivor;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A class that reads .json files
 *
 * @author giovanni
 */
public class JSONUtil {

    // Path to the .json files
    private static final String SURVIVORSJSON = "//home//giovanni//NetBeansProjects//ZSSN//ZSSN//json//survivors.json";
    private static final String INVENTORIESJSON = "//home//giovanni//NetBeansProjects//ZSSN//ZSSN//json//inventories.json";

    //Survivors and inventories object containers
    ArrayList<Survivor> survivors = new ArrayList<>();
    ArrayList<Inventory> inventories = new ArrayList<>();

    //Instantiate json a parser
    JSONParser parser = new JSONParser();

    /** A Class that reads .json files
     * 
     * @param key .json array keys
     * @param file .json file path
     * @return returns a .json array for object mananging
     * @throws FileNotFoundException If pathing to the archive is wrong
     * @throws IOException If any kind of I/O error occurs...
     * @throws ParseException Any kind of parsing error in the .json files
     */
    public JSONArray JSONreader(String key, String file) throws FileNotFoundException, IOException, ParseException {

        //Standard Java file reader
        FileReader survivorsReader = new FileReader(file);

        //JSONObject, for java context
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(survivorsReader);

        //JSONArray, for java context
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);

        //Returning generic JSON Array
        return jsonArray;
    }

    /**
     * Retrieve survivor .json list
     *
     * @return Return an ArrayList objects (survivors)
     */
    public ArrayList<Survivor> retrieveSurvivors() {
        try {
            for (Object survivor : this.JSONreader("survivors", SURVIVORSJSON)) {

                JSONObject jsonSurvivor = (JSONObject) survivor;
                String name = (String) jsonSurvivor.get("name");
                int age = (int) (long) jsonSurvivor.get("age");
                String gender = (String) jsonSurvivor.get("gender");
                int lonx = (int) (long) jsonSurvivor.get("lonx");
                int lony = (int) (long) jsonSurvivor.get("lony");
                Boolean infected = (Boolean) jsonSurvivor.get("infected?");
                Survivor surv = new Survivor();
                surv.setAge(age);
                surv.setGender(gender.charAt(0));
                surv.setLonx(lonx);
                surv.setLony(lony);
                surv.setName(name);
                surv.setInfected(infected);
                survivors.add(surv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return survivors;
    }

    /**
     * Retrieve inventories from .json files
     *
     * @return Return an ArrayList of objects (inventories)
     */
    public ArrayList<Inventory> retrieveInventories() {
        try {
            for (Object inventory : this.JSONreader("inventories", INVENTORIESJSON)) {

                JSONObject jsonInventory = (JSONObject) inventory;
                int water = (int) (long) jsonInventory.get("water");
                int food = (int) (long) jsonInventory.get("food");
                int meds = (int) (long) jsonInventory.get("meds");
                int ammo = (int) (long) jsonInventory.get("ammo");
                Inventory inv = new Inventory();
                inv.setAmmo(ammo);
                inv.setFood(food);
                inv.setWater(water);
                inv.setMeds(meds);
                inventories.add(inv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventories;
    }

}
