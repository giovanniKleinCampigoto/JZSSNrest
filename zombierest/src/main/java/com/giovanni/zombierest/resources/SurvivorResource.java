package com.giovanni.zombierest.resources;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.giovanni.zombierest.services.SurvivorService;


import com.giovanni.zombierest.model.Survivor;



@Path("/survivors")
public class SurvivorResource {
	
	SurvivorService ss = new SurvivorService();
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ArrayList<Survivor> getSurvivors(){
		 return ss.getAllSurvivors();
	}
		
}
