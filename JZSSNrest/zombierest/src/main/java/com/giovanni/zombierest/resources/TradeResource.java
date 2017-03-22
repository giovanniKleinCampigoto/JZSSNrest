package com.giovanni.zombierest.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import com.giovanni.zombierest.resources.beans.SurvivorFilterBean;
import com.giovanni.zombierest.services.SurvivorService;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TradeResource {
	SurvivorService ss = new SurvivorService();

	/**  Endpoint is /survivors/{idWantsToTrade}/trade/{with}
	 *
	 * @param survivorFilterBean
	 * @return
	 * @throws JSONException 
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{with}")
	public String trade(@BeanParam SurvivorFilterBean survivorFilterBean,String json) throws JSONException {
		
		return ss.trade(survivorFilterBean.getSurvivorId(),survivorFilterBean.getWith(),json);
	}
}
