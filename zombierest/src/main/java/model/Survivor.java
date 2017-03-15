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
import javax.persistence.Table;

/**
 *
 * @author giovanni
 */
@Entity
@Table(name = "survivor")
public class Survivor implements Serializable {
   
    @Id
    @Column(name = "idsurvivor")
    @GeneratedValue
    private Integer idsurvivor;
    @Column(name = "age")
    private Integer age;
    @Column(name = "lonx")
    private Integer lonx;
    @Column(name = "lony")
    private Integer lony;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private char gender;
    @Column(name = "infected")
    private Boolean infected;

    public Survivor() {
    }

    public Survivor(Integer idsurvivor, Integer age, Integer lonx, Integer lony, String name, char gender, Boolean infected) {
        this.idsurvivor = idsurvivor;
        this.age = age;
        this.lonx = lonx;
        this.lony = lony;
        this.name = name;
        this.gender = gender;
        this.infected = infected;
    }

    public Integer getIdsurvivor() {
        return idsurvivor;
    }

    public void setIdsurvivor(Integer idsurvivor) {
        this.idsurvivor = idsurvivor;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getLonx() {
        return lonx;
    }

    public void setLonx(Integer lonx) {
        this.lonx = lonx;
    }

    public Integer getLony() {
        return lony;
    }

    public void setLony(Integer lony) {
        this.lony = lony;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Boolean getInfected() {
        return infected;
    }

    public void setInfected(Boolean infected) {
        this.infected = infected;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idsurvivor);
        hash = 67 * hash + Objects.hashCode(this.age);
        hash = 67 * hash + Objects.hashCode(this.lonx);
        hash = 67 * hash + Objects.hashCode(this.lony);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + this.gender;
        hash = 67 * hash + Objects.hashCode(this.infected);
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
        final Survivor other = (Survivor) obj;
        if (this.gender != other.gender) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.idsurvivor, other.idsurvivor)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.lonx, other.lonx)) {
            return false;
        }
        if (!Objects.equals(this.lony, other.lony)) {
            return false;
        }
        if (!Objects.equals(this.infected, other.infected)) {
            return false;
        }
        return true;
    }

    
}
