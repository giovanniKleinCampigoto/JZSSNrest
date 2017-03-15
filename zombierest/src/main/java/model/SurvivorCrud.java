/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import util.JSONUtil;

/**
 *
 * @author giovanni
 */
public class SurvivorCrud {

    private JSONUtil json = new JSONUtil();
    private ArrayList<Survivor> survivors = new ArrayList<>();
    private ArrayList<Inventory> inventories = new ArrayList<>();
    private ArrayList<Inventory> JSONinventories = new ArrayList<>();

    /**
     * Generic random generator for raffling the survivor inventory
     *
     * @return return a Integer varying in the size of the inventories for
     * raffling the survivor inventory
     */
    public int randomGenerator(int key) {
        Random r = new Random();
        int k = r.nextInt(key);
        return k;
    }

    /**
     * Retrieves a list of survivors from the .json files using JSONUtil class..
     *
     * @return returns a list of created survivors and their inventories
     */
    public ArrayList<Survivor> retrieveSurvivors() {
        survivors = json.retrieveSurvivors();
        return survivors;
    }

    /**
     * Create the survivors in the database
     */
    public void saveSurvivors() {

        survivors = retrieveSurvivors();
      
        Transaction trns = null;
        HibernateUtil util;

        util = HibernateUtil.getInstance();
        Session session = util.getSession();

        
        try {
            trns = session.beginTransaction();

            for (int i = 0; i < survivors.size(); i++) {
                session.save(survivors.get(i));
            }

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Fetches all the survivors from the database
     *
     * @return returns a list with all the fetched survivors from the database
     */
    public List<Survivor> fetchSurvivorsFromDB() {

        HibernateUtil instance;
        instance = HibernateUtil.getInstance();

        Session session = instance.getSession();
        Transaction t = null;
        List<Survivor> list = null;

        try {
            t = session.beginTransaction();
            list = session.createCriteria(Survivor.class).list();

            t.commit();

        } catch (HibernateException ex) {
            if (t != null) {
                t.rollback();
            }
            Logger.getLogger("com").info("Exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Retrieves from the database a random survivor
     *
     * @return returns a random survivor from database
     */
    public Survivor retrieveRandomSurvivor() {
        Survivor survivor;

        survivor = fetchSurvivorsFromDB().get(randomGenerator(fetchSurvivorsFromDB().size()));

        return survivor;
    }

    /**
     * Updates survivor
     *
     * @param survivor Survivor to be updated
     */
    public void updateSurvivor(Survivor survivor) {
        HibernateUtil instance;
        instance = HibernateUtil.getInstance();

        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.update(survivor);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Updates inventory
     *
     * @param inventory Inventory to be updated
     */
    public void updateInventory(Inventory inventory) {
        HibernateUtil instance;
        instance = HibernateUtil.getInstance();

        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.update(inventory);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Fetch a survivor by id...
     *
     * @return returns the inventory of the designated survivor
     */
    public Inventory getInventoryById(int idsurvivor) {

        HibernateUtil instance;
        instance = HibernateUtil.getInstance();

        Session session = instance.getSession();
        Transaction t = null;
        Inventory inventory = null;

        try {
            t = session.beginTransaction();
            String queryString = "from Inventory where idsurvivor = :id";
            Query query = session.createQuery(queryString);
            query.setInteger("id", idsurvivor);
            inventory = (Inventory) query.uniqueResult();
        } catch (HibernateException ex) {
            if (t != null) {
                t.rollback();
            }
            Logger.getLogger("com").info("Exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return inventory;
    }

    /**
     * Deletes infected survivor inventory
     *
     * @param survivor Survivor inventory to be deleted
     */
    public void deleteSurvivorInventory(Survivor survivor) {
        Inventory inventory;

        inventory = getInventoryById(survivor.getIdsurvivor());

        HibernateUtil instance;
        instance = HibernateUtil.getInstance();

        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.delete(inventory);
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Adds inventories to the existing survivors
     */
    public void saveInventories() {
        JSONinventories = json.retrieveInventories();
        
        ArrayList<Survivor> survivors = new ArrayList<>();
        ArrayList<Inventory> inventories = new ArrayList<>();

        survivors.addAll(fetchSurvivorsFromDB());

        for (int i = 0; i < survivors.size(); i++) {
            Inventory inventory;
            //Raffle the survivor inventory
            inventory = JSONinventories.get(i);
            //Sets the survivor inventory
            inventory.setSurvivor(survivors.get(i));
            //Adding inventories into container...
            inventories.add(inventory);
        }

        Transaction trns = null;
        HibernateUtil util;

        util = HibernateUtil.getInstance();
        Session session = util.getSession();

        try {
            trns = session.beginTransaction();

            for (int i = 0; i < inventories.size(); i++) {
                session.save(inventories.get(i));
            }

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a determined item quantity for it's key in the designated
     * inventory
     *
     * @param inventory inventory to be searched
     * @param key can be one of the itens: water,food,meds or ammo
     * @return iten quantity
     */
    public int getItemsQuantity(Inventory inventory, String key) {
        int item = 0;

        if (key.equals("water")) {
            item = inventory.getWater();
        }
        if (key.equals("food")) {
            item = inventory.getFood();
        }
        if (key.equals("meds")) {
            item = inventory.getMeds();
        }
        if (key.equals("ammo")) {
            item = inventory.getAmmo();
        }

        return item;
    }

    /**
     * Set's trade inventory in the designated key
     *
     * @param inv survivor's inventory
     * @param key designated item type
     * @param ammount designated item ammount
     * @return returns updated inventory
     */
    public Inventory setTradeInventory(Inventory inv, String key, int ammount) {

        if (key.equals("water")) {
            inv.setWater(ammount);
        }
        if (key.equals("food")) {
            inv.setFood(ammount);
        }
        if (key.equals("meds")) {
            inv.setMeds(ammount);
        }
        if (key.equals("ammo")) {
            inv.setAmmo(ammount);
        }
        return inv;
    }
    
    public List<Inventory> fetchInventoriesFromDB() {

        HibernateUtil instance;
        instance = HibernateUtil.getInstance();

        Session session = instance.getSession();
        Transaction t = null;
        List<Inventory> list = null;

        try {
            t = session.beginTransaction();
            list = session.createCriteria(Inventory.class).list();

            t.commit();

        } catch (HibernateException ex) {
            if (t != null) {
                t.rollback();
            }
            Logger.getLogger("com").info("Exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return list;
    }
    
}