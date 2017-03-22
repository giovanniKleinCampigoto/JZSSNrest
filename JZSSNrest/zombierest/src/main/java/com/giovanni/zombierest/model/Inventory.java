/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giovanni.zombierest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Inventory model as a java class...annotations corresponds to database table, this is used for 
 * hibernate framework...
 * @author giovanni
 */
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "idinventory")
	private int inventoryid;
	@Column(name = "water")
	private int water;
	@Column(name = "food")
	private int food;
	@Column(name = "meds")
	private int meds;
	@Column(name = "ammo")
	private int ammo;
	@Column(name = "created")
	private Date created;

	//Foreign key
	@OneToOne
	@JoinColumn(name = "idsurvivor")
	private Survivor survivor;

	public Inventory() {
	}

	public int getInventoryid() {
		return inventoryid;
	}

	public void setInventoryid(int inventoryid) {
		this.inventoryid = inventoryid;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMeds() {
		return meds;
	}

	public void setMeds(int meds) {
		this.meds = meds;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Survivor getSurvivor() {
		return survivor;
	}

	public void setSurvivor(Survivor survivor) {
		this.survivor = survivor;
	}

}
