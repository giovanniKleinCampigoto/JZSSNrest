/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.*;
import java.io.Serializable;
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
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.inventoryid);
        hash = 53 * hash + Objects.hashCode(this.water);
        hash = 53 * hash + Objects.hashCode(this.food);
        hash = 53 * hash + Objects.hashCode(this.meds);
        hash = 53 * hash + Objects.hashCode(this.ammo);
        hash = 53 * hash + Objects.hashCode(this.survivor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventory other = (Inventory) obj;
        if (!Objects.equals(this.inventoryid, other.inventoryid)) {
            return false;
        }
        if (!Objects.equals(this.water, other.water)) {
            return false;
        }
        if (!Objects.equals(this.food, other.food)) {
            return false;
        }
        if (!Objects.equals(this.meds, other.meds)) {
            return false;
        }
        if (!Objects.equals(this.ammo, other.ammo)) {
            return false;
        }
        if (!Objects.equals(this.survivor, other.survivor)) {
            return false;
        }
        return true;
    }
    
    
}
