package com.giovanni.zombierest.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.resources.beans.SurvivorFilterBean;
import com.giovanni.zombierest.services.ReportService;

@Path("reports")
public class ReportsResource {
	ReportService rs = new ReportService();
	
	/**
	 * Produces all the reports listed in the especification of the ZSSN problem...
	 * @return given string with all the reports listed...
	 */
	@GET	
	@Produces(MediaType.TEXT_PLAIN)
	public String getReports() {
		return "Percentage of infected: " + String.valueOf(rs.percentageInfected()) + "%\n\n" +
				"Percentage of non-infected: " + String.valueOf(rs.percentageNonInfected()) + "%\n\n" +
				String.valueOf(rs.lostPoints()) + "\n\n" + 
				"Avg resource per survivor: " + "\n\n" +"avgWater: " + rs.averageResource().get(0) + "\n"+  
				"avgFood: " + rs.averageResource().get(1) + "\n" +
				"avgMeds: " + rs.averageResource().get(2) + "\n" +
				"avgAmmo: " + rs.averageResource().get(3);
	}
	
	
	
	
}
