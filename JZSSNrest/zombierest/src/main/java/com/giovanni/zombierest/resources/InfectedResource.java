package com.giovanni.zombierest.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.resources.beans.SurvivorFilterBean;
import com.giovanni.zombierest.services.SurvivorService;

@Path("/")

@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class InfectedResource {
	SurvivorService ss = new SurvivorService();

	/**
	 * Flags the designated infected survivor endpoint is /survivors/{survivorId}/reportinfected/{infectedId}
	 * @param survivorFilterBean filter to get the parameters from the uri
	 * @return flagged survivor after he/she is flagged 3 times
	 */
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{flaggedId}")
	public String flagInfected(@BeanParam SurvivorFilterBean survivorFilterBean) {
		return ss.flagInfectedSurvivor(survivorFilterBean.getSurvivorId(), survivorFilterBean.getFlaggedId());
	}
}
