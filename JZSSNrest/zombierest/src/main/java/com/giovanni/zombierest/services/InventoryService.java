package com.giovanni.zombierest.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.util.HibernateUtil;

/**
 * Services to CRUD, a inventory object..
 * @author giovanni
 *
 */
public class InventoryService {

	/**
	 * Creates inventory, given the survivor
	 */
	public Inventory createInventory(Inventory inventory, Survivor survivor) {

		Transaction trns = null;
		HibernateUtil util;

		util = HibernateUtil.getInstance();
		Session session = util.getSession();

		try {
			trns = session.beginTransaction();

			inventory.setSurvivor(survivor);
			session.save(inventory);
			session.getTransaction().commit();

		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return inventory;
	}

	/**
	 * Get's an inventory based on survivor id
	 *
	 * @return returns the inventory of the designated survivor
	 */
	public Inventory getInventory(int idsurvivor) {

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Session session = instance.getSession();
		Transaction t = null;
		Inventory inventory = null;

		try {

			t = session.beginTransaction();
			inventory = (Inventory) session.createQuery("from Inventory where idsurvivor = :id")
					.setParameter("id", idsurvivor).uniqueResult();
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
	 * Gets all the inventories..
	 * 
	 * @return returns all the inventories in a list...
	 */
	public List<Inventory> getAllInventories() {

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Session session = instance.getSession();
		Transaction t = null;
		List<Inventory> list = null;

		try {
			t = session.beginTransaction();
			TypedQuery<Inventory> query = session.createQuery("FROM Inventory");
			list = query.getResultList();

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
	 * 
	 * Updates inventory
	 *
	 * @param inventory
	 *            Inventory to be updated
	 */
	public Inventory updateInventory(Inventory inventory) {
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
		return inventory;
	}

	/**
	 * Deletes survivor inventory
	 *
	 * @param survivor
	 *            Survivor inventory to be deleted
	 */
	public Inventory deleteInventory(Survivor survivor) {
		Inventory inventory;

		inventory = getInventory(survivor.getIdsurvivor());

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
		return inventory;
	}

	/**
	 * Returns a determined item quantity for it's key in the designated
	 * inventory, this is used for the trade function, given an inventory
	 * the function returns the quantity of the designated resource...
	 * 
	 * @param inventory
	 *            inventory to be searched
	 * @param key
	 *            can be one of the itens: water,food,meds or ammo
	 * @return item quantity
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
	 * Set the inventory ammount of each type of item as necessary,for trading purposes...
	 *
	 * @param inv
	 *            survivor's inventory
	 * @param key
	 *            designated item type
	 * @param ammount
	 *            designated item ammount
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
	
}
