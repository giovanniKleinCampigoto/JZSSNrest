package com.giovanni.zombierest.services;

import java.util.ArrayList;
import java.util.List;

import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.model.SurvivorCrud;

public class SurvivorService {
	
	ArrayList<Survivor> list = new ArrayList<>();
	
	public ArrayList<Survivor> getAllSurvivors(){
		SurvivorCrud sc = new SurvivorCrud();
		list.addAll(sc.fetchSurvivorsFromDB());
		return list;
	}
}
