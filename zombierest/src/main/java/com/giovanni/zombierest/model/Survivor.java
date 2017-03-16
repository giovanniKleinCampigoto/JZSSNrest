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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giovanni
 */
@XmlRootElement
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
	@Column(name = "created")
	private Date created;

	public Survivor() {
	}

	public Survivor(Integer idsurvivor, Integer age, Integer lonx, Integer lony, String name, char gender,
			Boolean infected, Date created) {
		this.idsurvivor = idsurvivor;
		this.age = age;
		this.lonx = lonx;
		this.lony = lony;
		this.name = name;
		this.gender = gender;
		this.infected = infected;
		this.created = created;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
