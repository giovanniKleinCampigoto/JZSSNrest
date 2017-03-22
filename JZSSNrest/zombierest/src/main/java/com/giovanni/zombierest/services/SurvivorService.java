package com.giovanni.zombierest.services;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

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
	 * Trade function
	 * @param idWantsToTrade id of the survivor who want's to trade
	 * @param idTrader id of the trader
	 * @param json json file with data of the trade
	 * @return string declarating the state of the trade
	 * @throws JSONException bad json
	 * @throws FileNotFoundException self explanatory
	 * @throws IOException something went wrong with I/O
	 * @throws ParseException bad json
	 */
	public String trade(int idWantsToTrade, int idTrader, String json)
			throws JSONException, FileNotFoundException, IOException, ParseException {
		ju = new JSONUtil();
		is = new InventoryService();
		String message = " ";
		Inventory trade_inventory1, trade_inventory2;

		trade_inventory1 = ju.handleJsonTradeInventory(ju.parseJsonArrayResponse(json).get(0).toString(),
				getSurvivor(idTrader));
		trade_inventory2 = ju.handleJsonTradeInventory(ju.parseJsonArrayResponse(json).get(1).toString(),
				getSurvivor(idWantsToTrade));

		if (!pointsAreEqual(trade_inventory1, trade_inventory2)) {
			message = "Points doesen't match, check the trade file...";
		} else {

			Inventory invT, invWTT;

			Survivor trader = getSurvivor(idTrader);
			Survivor wantsToTrade = getSurvivor(idWantsToTrade);

			invT = is.getInventory(trader.getIdsurvivor());
			invWTT = is.getInventory(wantsToTrade.getIdsurvivor());

			// Method that verifies the amount of resources on the inventories,
			// based on what is being sent on trade file
			// If the amount is greater than the survivor inventory, trade gets
			// blocked..
			message = verifyAmmount("Trade failed because... ", trade_inventory1, trade_inventory2, invT, invWTT,
					trader, wantsToTrade);

			makeTrade(trade_inventory1, trade_inventory2, invT, invWTT);

			is.updateInventory(invWTT);
			is.updateInventory(invT);

			message = "Trade is completed...";
		}
		return message;
	}

	/**
	 *  Makes the trade
	 * @param trade_inventory1 trade inventory in the first position of the file
	 * @param trade_inventory2 trade inventory in the seccond position of the file
	 * @param invT inventory of the trader
	 * @param invWTT inventory of the survivor who want's to trade
	 */
	private void makeTrade(Inventory trade_inventory1, Inventory trade_inventory2, Inventory invT, Inventory invWTT) {
		int[] tradedAmmountWTT = new int[4];
		int[] tradedAmmountT = new int[4];
		int[] recievedAmmountWTT = new int[4];
		int[] recievedAmmountT = new int[4];

		tradedAmmountWTT[0] = Math.abs(is.getItemsQuantity(invWTT, "water") - trade_inventory2.getWater());
		tradedAmmountWTT[1] = Math.abs(is.getItemsQuantity(invWTT, "food") - trade_inventory2.getFood());
		tradedAmmountWTT[2] = Math.abs(is.getItemsQuantity(invWTT, "meds") - trade_inventory2.getMeds());
		tradedAmmountWTT[3] = Math.abs(is.getItemsQuantity(invWTT, "ammo") - trade_inventory2.getAmmo());

		tradedAmmountT[0] = Math.abs(is.getItemsQuantity(invT, "water") - trade_inventory1.getWater());
		tradedAmmountT[1] = Math.abs(is.getItemsQuantity(invT, "food") - trade_inventory1.getFood());
		tradedAmmountT[2] = Math.abs(is.getItemsQuantity(invT, "meds") - trade_inventory1.getMeds());
		tradedAmmountT[3] = Math.abs(is.getItemsQuantity(invT, "ammo") - trade_inventory1.getAmmo());

		is.setTradeInventory(invWTT, "water", tradedAmmountWTT[0]);
		is.setTradeInventory(invWTT, "food", tradedAmmountWTT[1]);
		is.setTradeInventory(invWTT, "meds", tradedAmmountWTT[2]);
		is.setTradeInventory(invWTT, "ammo", tradedAmmountWTT[3]);

		is.setTradeInventory(invT, "water", tradedAmmountT[0]);
		is.setTradeInventory(invT, "food", tradedAmmountT[1]);
		is.setTradeInventory(invT, "meds", tradedAmmountT[2]);
		is.setTradeInventory(invT, "ammo", tradedAmmountT[3]);

		recievedAmmountWTT[0] = Math.abs(is.getItemsQuantity(invWTT, "water") + trade_inventory1.getWater());
		recievedAmmountWTT[1] = Math.abs(is.getItemsQuantity(invWTT, "food") + trade_inventory1.getFood());
		recievedAmmountWTT[2] = Math.abs(is.getItemsQuantity(invWTT, "meds") + trade_inventory1.getMeds());
		recievedAmmountWTT[3] = Math.abs(is.getItemsQuantity(invWTT, "ammo") + trade_inventory1.getAmmo());

		recievedAmmountT[0] = Math.abs(is.getItemsQuantity(invT, "water") + trade_inventory2.getWater());
		recievedAmmountT[1] = Math.abs(is.getItemsQuantity(invT, "food") + trade_inventory2.getFood());
		recievedAmmountT[2] = Math.abs(is.getItemsQuantity(invT, "meds") + trade_inventory2.getMeds());
		recievedAmmountT[3] = Math.abs(is.getItemsQuantity(invT, "ammo") + trade_inventory2.getAmmo());

		is.setTradeInventory(invT, "water", recievedAmmountT[0]);
		is.setTradeInventory(invT, "food", recievedAmmountT[1]);
		is.setTradeInventory(invT, "meds", recievedAmmountT[2]);
		is.setTradeInventory(invT, "ammo", recievedAmmountT[3]);

		is.setTradeInventory(invWTT, "water", recievedAmmountWTT[0]);
		is.setTradeInventory(invWTT, "food", recievedAmmountWTT[1]);
		is.setTradeInventory(invWTT, "meds", recievedAmmountWTT[2]);
		is.setTradeInventory(invWTT, "ammo", recievedAmmountWTT[3]);
	}

	/**
	 * Verifies ammount of resources
	 * @param message simple message
	 * @param trade_inventory1 trade inventory in the first position of the file
	 * @param trade_inventory2 trade inventory in the seccond position of the file
	 * @param invT inventory of the trader
	 * @param invWTT inventory of the survivor who want's to trade
	 * @param trader trader
	 * @param wantsToTrade survivor who wan'ts to trade
	 * @return
	 */
	private String verifyAmmount(String message, Inventory trade_inventory1, Inventory trade_inventory2, Inventory invT,
			Inventory invWTT, Survivor trader, Survivor wantsToTrade) {
		if (!(trade_inventory1.getWater() <= invT.getWater() && trade_inventory1.getFood() <= invT.getFood()
				&& trade_inventory1.getMeds() <= invT.getMeds() && trade_inventory1.getAmmo() <= invT.getAmmo())) {
			message += trader.getName() + " doesen't have the necessary ammount of resources,  ";
		} else {
			message = "";
		}

		if (!(trade_inventory2.getWater() <= invWTT.getWater() && trade_inventory2.getFood() <= invWTT.getFood()
				&& trade_inventory2.getMeds() <= invWTT.getMeds() && trade_inventory2.getAmmo() <= invWTT.getAmmo())) {
			message += wantsToTrade.getName() + " doesen't have the necessary ammount of resources";
		} else {
			message = "";
		}
		return message;
	}

	/**
	 * Verifies if points are equal
	 * @param trade_inventory1 trade inventory in the first position of the file
	 * @param trade_inventory2 trade inventory in the seccond position of the file
	 * @return returns if points are equal or not
	 */
	private Boolean pointsAreEqual(Inventory trade_inventory1, Inventory trade_inventory2) {
		long points1 = 0;
		long points2 = 0;

		if (trade_inventory1.getWater() > 0 || trade_inventory2.getWater() > 0) {
			points1 += trade_inventory1.getWater() * 4;
			points2 += trade_inventory2.getWater() * 4;
		}
		if (trade_inventory1.getFood() > 0 || trade_inventory2.getFood() > 0) {
			points1 += trade_inventory1.getFood() * 3;
			points2 += trade_inventory2.getFood() * 3;
		}
		if (trade_inventory1.getMeds() > 0 || trade_inventory2.getMeds() > 0) {
			points1 += trade_inventory1.getMeds() * 2;
			points2 += trade_inventory2.getMeds() * 2;
		}
		if (trade_inventory1.getAmmo() > 0 || trade_inventory2.getAmmo() > 0) {
			points1 += trade_inventory1.getAmmo();
			points2 += trade_inventory2.getAmmo();
		}

		return points1 == points2;
	}

}
