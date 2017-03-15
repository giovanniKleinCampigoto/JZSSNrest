/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
 *
 * @author giovanni
 */
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idinventory")
    private Integer inventoryid;
    @Column(name = "water")
    private Integer water;
    @Column(name = "food")
    private Integer food;
    @Column(name = "meds")
    private Integer meds;
    @Column(name = "ammo")
    private Integer ammo;
    @Column(name = "created")
    private Date created;
    
    @OneToOne
    @JoinColumn(name = "idsurvivor")
    private Survivor survivor;

    public Inventory() {
    }

    public Integer getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(Integer inventoryid) {
        this.inventoryid = inventoryid;
    }

    public Integer getWater() {
        return water;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public Integer getMeds() {
        return meds;
    }

    public void setMeds(Integer meds) {
        this.meds = meds;
    }

    public Integer getAmmo() {
        return ammo;
    }

    public void setAmmo(Integer ammo) {
        this.ammo = ammo;
    }

    public Survivor getSurvivor() {
        return survivor;
    }

    public void setSurvivor(Survivor survivor) {
        this.survivor = survivor;
    }
    
    public Date getCreated(){
    	return created;
    }
    
    public void setCreated(Date created){
    	this.created = created;
    }
}
