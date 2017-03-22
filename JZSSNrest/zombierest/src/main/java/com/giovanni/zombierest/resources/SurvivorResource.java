package com.giovanni.zombierest.resources;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.giovanni.zombierest.services.InventoryService;
import com.giovanni.zombierest.services.SurvivorService;
import com.giovanni.zombierest.util.JSONUtil;
import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.resources.beans.SurvivorFilterBean;

@Path("/survivors")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML } )
public class SurvivorResource {

	SurvivorService ss = new SurvivorService();
	InventoryService is = new InventoryService();
	Map<Survivor,Inventory> map = new HashMap<>();
	JSONUtil ju = new JSONUtil();
	Survivor survivor;
	Inventory inventory;
	
	@POST
	public Survivor createSurvivor(String json) throws JSONException{
		Survivor survivor = ss.createSurvivor(json);
		return survivor;
	}
	
	@GET
	public List<Survivor> getSurvivors(@BeanParam SurvivorFilterBean survivorFilterBean) {
		if (survivorFilterBean.getStart() >= 0 && survivorFilterBean.getSize() > 0)
			return ss.getAllSurvivorsPaginated(survivorFilterBean.getStart(), survivorFilterBean.getSize());

		return ss.getAllNonInfectedSurvivors();
	}

	@GET
	@Path("/{survivorId}")
	public Survivor getSurvivor(@BeanParam SurvivorFilterBean survivorFilterBean, @Context UriInfo uriInfo) {
		Survivor survivor = ss.getSurvivor(survivorFilterBean.getSurvivorId());		
		survivor.addLink(getUriForSelf(uriInfo, survivor), "self");
		survivor.addLink(getUriForInventory(uriInfo, survivor), "inventory");
		survivor.addLink(getUriForReportInfected(uriInfo, survivor), "reportinfected");
		survivor.addLink(getUriForTrade(uriInfo, survivor), "trade");
		return survivor;
	}
	
	private String getUriForTrade(UriInfo uriInfo, Survivor survivor) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(SurvivorResource.class)
				.path(SurvivorResource.class, "trade")
				.path(InfectedResource.class)
				.resolveTemplate("survivorId",survivor.getIdsurvivor())
				.path("traderId")
				.build();
		return uri.toString();
	}
	
	private String getUriForReportInfected(UriInfo uriInfo, Survivor survivor) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(SurvivorResource.class)
				.path(SurvivorResource.class, "flagSurvivor")
				.path(InfectedResource.class)
				.resolveTemplate("survivorId",survivor.getIdsurvivor())
				.path("flaggedSurvivor")
				.build();
		return uri.toString();
	}

	private String getUriForInventory(UriInfo uriInfo, Survivor survivor) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(SurvivorResource.class)
				.path(Long.toString(survivor.getIdsurvivor()))
				.path("inventories")
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Survivor survivor) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(SurvivorResource.class)
				.path(Long.toString(survivor.getIdsurvivor()))
				.build()
				.toString();
		return uri;
	}

	@PUT
	@Path("/{survivorId}")
	public Survivor updateSurvivor(@BeanParam SurvivorFilterBean survivorFilterBean, Survivor survivor) {
		survivor.setIdsurvivor(survivorFilterBean.getSurvivorId());
		return ss.updateSurvivor(survivor);
	}

	@DELETE
	@Path("/{survivorId}")
	public void deleteSurvivor(@BeanParam SurvivorFilterBean survivorFilterBean) {
		ss.deleteSurvivor(survivorFilterBean.getSurvivorId());
	}
	
	@Path("/{survivorId}/reportinfected")
	public InfectedResource flagSurvivor() {
		return new InfectedResource();
	}
	
	@Path("/{survivorId}/trade")
	public TradeResource trade() {
		return new TradeResource();
	}

	
	@GET
	@Path("/{survivorId}/inventories")
	public Inventory getInventory(@BeanParam SurvivorFilterBean survivorFilterBean) {
		return is.getInventory(survivorFilterBean.getSurvivorId());
	}
	
}
