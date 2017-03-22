package com.giovanni.zombierest.services;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.giovanni.zombierest.model.Inventory;
import com.giovanni.zombierest.model.Survivor;

/**
 * Report service for reports creation
 * @author giovanni
 *
 */
public class ReportService {

	private static SurvivorService ss = new SurvivorService();
	private static InventoryService is = new InventoryService();
	private static ArrayList<Double> list = new ArrayList<>();
	private static ArrayList<Survivor> inf = new ArrayList<>();
	private static ArrayList<Survivor> ninf = new ArrayList<>();
	private static ArrayList<Survivor> all = new ArrayList<>();
	private static ArrayList<Inventory> inv = new ArrayList<>();

	/**
	 * Returns the percentage of infected survivors
	 * @return returns the percentage of infected survivors
	 */
	public String percentageInfected() {
		DecimalFormat df = new DecimalFormat("0.##");

		if(all.isEmpty())
			all.addAll(ss.getAllSurvivors());
		if(inf.isEmpty())
			inf.addAll(ss.getAllInfected());
		
		double total = all.size();
		double infected = inf.size();

		String dx = df.format(infected/total);
		return dx;
	}

	/**
	 * Returns the percentage of non-infected survivors
	 * @return returns the percentage of non-infected survivors
	 */
	public String percentageNonInfected() {
		DecimalFormat df = new DecimalFormat("0.##");

		if(all.isEmpty())
			all.addAll(ss.getAllSurvivors());
		if(ninf.isEmpty())
			ninf.addAll(ss.getAllNonInfectedSurvivors());
		
		double total = all.size();
		double nonInfected = ninf.size();

		String dx = df.format(nonInfected/total);
		return dx;
	}
	
	/**
	 * Returns lost points from infected survivors
	 * @return returns lost points from infected survivors
	 */
	public String lostPoints(){
		int points = 0;
		
		if (inf.isEmpty())
			inf.addAll(ss.getAllInfected());
		
	    for (int i = 0; i < inf.size(); i++) {
			points += inf.get(i).getLostpoints();
		}
	    return "Lost points due to infection: " + points;
	}
	
	/**
	 * Returns average resource per survivor
	 * @return returns average resource per survivor
	 */
	public ArrayList<Double> averageResource(){
		double avgWater = 0,avgFood = 0,avgMeds = 0,avgAmmo = 0, size = 0;
		
		if(inv.isEmpty())
			inv.addAll(is.getAllInventories());
		
		size = inv.size();
		
		for (int i = 0; i < is.getAllInventories().size(); i++) {
			avgWater += Double.valueOf(inv.get(i).getWater());
			avgFood += Double.valueOf(inv.get(i).getFood());
			avgMeds += Double.valueOf(inv.get(i).getMeds());
			avgAmmo += Double.valueOf(inv.get(i).getAmmo());
		}
		
		list.add(Math.floor(avgWater /= size));
		list.add(Math.floor(avgFood /= size));
		list.add(Math.floor(avgMeds /= size));
		list.add(Math.floor(avgAmmo /= size));
		
		return list;
	}
}
