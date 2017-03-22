/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giovanni.zombierest.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.giovanni.zombierest.model.Survivor;
import com.giovanni.zombierest.model.Inventory;

/**
 * Utility class for parsing json files as it is sent by the client...
 *
 * @author giovanni
 */
public class JSONUtil {

	/**
	 * Parses a .json survivor
	 * @param json json string to be parsed
	 * @return given survivor
	 * @throws JSONException if json file fails to be parsed
	 */
	public Survivor handleJsonSurvivor(String json) throws JSONException {
		JSONObject jsonSurvivor = new JSONObject(json);

		String name = (String) jsonSurvivor.get("name");
		Integer age = (Integer) jsonSurvivor.get("age");
		Integer lonx = (Integer) jsonSurvivor.get("lonx");
		Integer lony = (Integer) jsonSurvivor.get("lony");
		String aux = (String) jsonSurvivor.get("gender");
		char gender = aux.charAt(0);
		Boolean infected = (Boolean) jsonSurvivor.get("infected");

		Survivor survivor = new Survivor();
		survivor.setAge(age);
		survivor.setInfected(infected);
		survivor.setGender(gender);
		survivor.setLonx(lonx);
		survivor.setLony(lony);
		survivor.setName(name);

		return survivor;
	}
	

	/**
	 * Parses a .json inventory
	 * @param json json string to be parsed
	 * @return given inventory
	 * @throws JSONException if json file fails to be parsed
	 */
	public Inventory handleJsonInventory(String json, Survivor survivor) throws JSONException {
		JSONObject jsonSurvivor = new JSONObject(json);

		JSONObject jsonInventory = jsonSurvivor.getJSONObject("inventory");

		Integer water = jsonInventory.getInt("water");
		Integer food = jsonInventory.getInt("food");
		Integer meds = jsonInventory.getInt("meds");
		Integer ammo = jsonInventory.getInt("ammo");
		Inventory inventory = new Inventory();
		inventory.setAmmo(ammo);
		inventory.setFood(food);
		inventory.setMeds(meds);
		inventory.setWater(water);
		inventory.setSurvivor(survivor);

		return inventory;
	}
	

	/**
	 * Parses given string on a json entry
	 * @param json json string
	 * @param value value to be parsed in the json file
	 * @return given string
	 * @throws JSONException if json file fails to be parsed
	 */
	public String parseJsonString(String json,String value) throws JSONException {
		JSONObject jsonString = new JSONObject(json);

		String string = jsonString.getString(value);

		return string;
	}

	/**
	 * Parses given integer on a json entry
	 * @param json json string
	 * @param value value to be parsed in the json file
	 * @return given integer
	 * @throws JSONException if json file fails to be parsed
	 */
	public Integer parseJsonInteger(String json,String value) throws JSONException {
		JSONObject jsonString = new JSONObject(json);

		Integer integer = jsonString.getInt(value);

		return integer;
	}
	
	
	public JSONArray parseJsonArray(String path) throws FileNotFoundException, IOException, ParseException, JSONException{
		JSONParser parser = new JSONParser();

		String json = parser.parse(new FileReader(path)).toString();
		
		JSONArray jsonArray = new JSONArray(json);
		
		return jsonArray;
	}

}
