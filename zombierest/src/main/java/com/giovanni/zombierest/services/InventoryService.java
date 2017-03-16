package com.giovanni.zombierest.services;

import java.util.ArrayList;

import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.model.SurvivorCrud;

public class InventoryService {

	ArrayList<Inventory> list = new ArrayList<>();

	public ArrayList<Inventory> getAllInventories() {
		SurvivorCrud sc = new SurvivorCrud();
		list.addAll(sc.fetchInventoriesFromDB());
		return list;
	}
}
