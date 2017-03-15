package com.giovanni.zombierest.resources;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/survivors")
public class SurvivorResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String helloWorld(){
		return "Hello World";
	}

}
