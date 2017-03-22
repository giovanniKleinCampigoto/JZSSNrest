package com.giovanni.zombierest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;

import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.util.HibernateUtil;
import com.giovanni.zombierest.util.JSONUtil;

public class SurvivorService {
	
	private static Map<String, Integer> map = new HashMap<>();
	private static ArrayList<Integer> array = new ArrayList<>();
	private static InventoryService is;
	private JSONUtil ju;

	/**
	 * Create the survivors in the database
	 * 
	 * @throws JSONException
	 */
	public Survivor createSurvivor(String json) throws JSONException {

		Transaction trns = null;
		HibernateUtil util;

		Survivor survivor;
		Inventory inventory;

		ju = new JSONUtil();
		survivor = ju.handleJsonSurvivor(json);
		inventory = ju.handleJsonInventory(json, survivor);
		survivor.setLostpoints(0);
		
		util = HibernateUtil.getInstance();
		Session session = util.getSession();

		try {
			trns = session.beginTransaction();
			session.save(survivor);
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
		return survivor;
	}
	
	
	public List<Survivor> getAllSurvivors() {

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Session session = instance.getSession();
		Transaction t = null;
		List<Survivor> list = null;

		try {
			t = session.beginTransaction();
			list = session.createQuery("from Survivor").list();

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
	 * Fetches all the survivors from the database
	 *
	 * @return returns a list with all the fetched survivors from the database
	 */
	public List<Survivor> getAllNonInfectedSurvivors() {

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Session session = instance.getSession();
		Transaction t = null;
		List<Survivor> list = null;

		try {
			t = session.beginTransaction();
			list = session.createQuery("from Survivor where infected = :inf").setParameter("inf", false).list();

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
	
	
	public List<Survivor> getAllInfected() {

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Session session = instance.getSession();
		Transaction t = null;
		List<Survivor> list = null;

		try {
			t = session.beginTransaction();
			list = session.createQuery("from Survivor where infected = :inf").setParameter("inf", true).list();

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
	 * Fetch a survivor by id...
	 *
	 * @return returns the inventory of the designated survivor
	 */
	public Survivor getSurvivor(int idsurvivor) {

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Session session = instance.getSession();
		Transaction t = null;
		Survivor survivor = null;

		try {

			t = session.beginTransaction();
			survivor = (Survivor) session.createQuery("from Survivor where idsurvivor = :id")
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
		return survivor;
	}

	/**
	 * Updates survivor
	 *
	 * @param survivor
	 *            Survivor to be updated
	 */
	public Survivor updateSurvivor(Survivor survivor) {
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
		return survivor;
	}

	/**
	 * Deletes designated survivor, provided the id
	 * 
	 * @param idsurvivor
	 *            survivor id
	 * @return returns the deleted survivor
	 */
	public Survivor deleteSurvivor(int idsurvivor) {
		Survivor survivor;

		survivor = getSurvivor(idsurvivor);

		HibernateUtil instance;
		instance = HibernateUtil.getInstance();

		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			tx = session.beginTransaction();
			session.delete(survivor);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return survivor;
	}

	/**
	 * Basic pagination for survivor service...
	 * 
	 * @param start
	 *            starting index
	 * @param size
	 *            size of the page
	 * @return returns a sublist with the survivors paginated
	 */
	public List<Survivor> getAllSurvivorsPaginated(int start, int size) {
		if (start + size > getAllNonInfectedSurvivors().size())
			return new ArrayList<Survivor>();
		return getAllNonInfectedSurvivors().subList(start, start + size);
	}

	/**
	 * Flags an survivor as infected and deletes it's inventory after 3 reports
	 *
	 * @param flagger
	 *            Survivor flagging infected one
	 * @param flaggedSurvivor
	 *            The flagged survivor
	 */
	public String flagInfectedSurvivor(int flaggerId, int flaggedId) {
		Inventory inventory = null;
		String message = " ";

		if (getSurvivor(flaggerId) == null && getSurvivor(flaggedId) == null) {
			message = "Flagger or flagged don't exist!";
		} else {

			if (flaggerId == flaggedId) {
				message = "A survivor can't flag himself";
			}
			if (!array.contains(flaggerId)) {
				array.add(flaggerId);
				message = "Survivor " + getSurvivor(flaggerId).getName() + " reports that "
						+ getSurvivor(flaggedId).getName() + " is infected!   " + array.toString();

				if (array.size() == 3) {
					Survivor surv = new Survivor();
					surv = getSurvivor(flaggedId);

					surv.setInfected(true);
					is = new InventoryService();
					inventory = is.getInventory(surv.getIdsurvivor());
					int points = inventory.getAmmo() + inventory.getFood() * 3 + inventory.getMeds() * 2
							+ inventory.getWater() * 4;

					message = "Hold on to your brains! " + surv.getName() + ", is a zombie!";

					surv.setLostpoints(points);
					updateSurvivor(surv);
					is.deleteInventory(surv);

					surv = null;
					array.clear();
				}
			}
		}
		return message;
	}

	/**
	 *  Trade function (You can only trade one type of item each time) eg: (water x meds) or (meds x ammo),
	 *	is not possible yet doing (1 water => 1 food and 1 ammo)
	 * @param idWantsToTrade
	 * @param idTrader
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public String trade(int idWantsToTrade, int idTrader, String json) throws JSONException {
		ju = new JSONUtil();
		is = new InventoryService();
		
		String keyTradeWTT = ju.parseJsonString(json, "tradeWTT");
		String keyTradeT = ju.parseJsonString(json, "tradeT");
		int ammountWTT = ju.parseJsonInteger(json, "ammountWTT");
		int ammountT = ju.parseJsonInteger(json, "ammountT");
		
		Inventory invT, invWTT;
		initializeTable(map);
		String message = "";
		Survivor trader = getSurvivor(idTrader);
		Survivor wantsToTrade = getSurvivor(idWantsToTrade);

		
		invT = is.getInventory(trader.getIdsurvivor());
		invWTT = is.getInventory(wantsToTrade.getIdsurvivor());

		int pointsT, pointsWTT;

		// verifies items ammount in Trader inventory
		if (ammountT > is.getItemsQuantity(invT, keyTradeT)) {
			message = "Trader doesen't have " + ammountT + " of " + keyTradeT;			
			// verifies items ammount in WTT inventory

		} else if (ammountWTT > is.getItemsQuantity(invWTT, keyTradeWTT)) {
			message = "Want's to trade doesen't have " + ammountWTT + " of " + keyTradeWTT;
		} else {
			pointsT = ammountT * map.get(keyTradeT);
			pointsWTT = ammountWTT * map.get(keyTradeWTT);

			if (pointsT == pointsWTT) {

				int tradedAmmountT, tradedAmmountWTT, recievedAmmountT, recievedAmmountWTT;

				
				
				tradedAmmountWTT = Math.abs(is.getItemsQuantity(invWTT, keyTradeWTT) - ammountWTT); //WATER
				
				tradedAmmountT = Math.abs(is.getItemsQuantity(invT, keyTradeT) - ammountT); //meds 
				
				is.setTradeInventory(invT, keyTradeT, tradedAmmountT);				
				is.setTradeInventory(invWTT, keyTradeWTT, tradedAmmountWTT);
				
				recievedAmmountWTT = Math.abs(is.getItemsQuantity(invWTT, keyTradeT) + ammountT);				
				recievedAmmountT = Math.abs(is.getItemsQuantity(invT, keyTradeWTT) + ammountWTT);
				
				is.setTradeInventory(invT, keyTradeWTT, recievedAmmountT);				
				is.setTradeInventory(invWTT, keyTradeT, recievedAmmountWTT);

				message = "recievedammountT(tex) " + recievedAmmountT +  " recievedammountT(ajax) " + 
				recievedAmmountWTT + " invT(tex): " + invT.getWater() + " invWTT (ajax)" + invWTT.getMeds();
				
				is.updateInventory(invWTT);
				is.updateInventory(invT);

				message = "Trade is completed...";
			}
			else{
				message = "Trading " + keyTradeWTT + " " + ammountWTT + " for " + keyTradeT + " " + ammountT + " is not possible, because points doesen't match";  
			}
		}
		return message;

	}

	public void initializeTable(Map map) {
		map.put("water", 4);
		map.put("food", 3);
		map.put("meds", 2);
		map.put("ammo", 1);
	}

}
