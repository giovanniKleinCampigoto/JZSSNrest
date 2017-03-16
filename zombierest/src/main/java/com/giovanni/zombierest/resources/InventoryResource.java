package com.giovanni.zombierest.resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.services.InventoryService;

@Path("/inventories")
public class InventoryResource {

	InventoryService ss = new InventoryService();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<Inventory> getInventories() {
		return ss.getAllInventories();
	}
}
