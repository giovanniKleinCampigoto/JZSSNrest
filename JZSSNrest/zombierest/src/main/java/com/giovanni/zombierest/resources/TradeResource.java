package com.giovanni.zombierest.resources;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

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
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{with}")
	public String trade(@BeanParam SurvivorFilterBean survivorFilterBean,String json) throws FileNotFoundException, JSONException, IOException, ParseException{
		
		return ss.trade(survivorFilterBean.getSurvivorId(),survivorFilterBean.getWith(),json);
	}
}
