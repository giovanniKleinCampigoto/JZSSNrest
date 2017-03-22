/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giovanni.zombierest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.giovanni.zombierest.util.JSONUtil;

/**
 * Survivor model as a java class...annotations corresponds to database table, this is used for 
 * hibernate framework...
 * @author giovanni
 */
@XmlRootElement
@Entity
@Table(name = "survivor")
public class Survivor implements Serializable {

	@Id
	@Column(name = "idsurvivor")
	@GeneratedValue
	private int idsurvivor;
	@Column(name = "age")
	private int age;
	@Column(name = "lonx")
	private int lonx;
	@Column(name = "lony")
	private int lony;
	@Column(name = "name")
	private String name;
	@Column(name = "gender")
	private char gender;
	@Column(name = "infected")
	private Boolean infected;
	@Column(name = "created")
	private Date created;
	@Column(name = "lostpoints")
	private long lostpoints;
	
	//Foreign key mapping for cascade deletion of survivor and it's inventory
	@OneToOne(mappedBy = "survivor", cascade = CascadeType.REMOVE)
	private Inventory inventory;
	
	//Annotation for ignoring the property, as it does not exist on database tables...
	@Transient
	private List<Link> links = new ArrayList<>();

	public Survivor() {
	}
	
	public int getIdsurvivor() {
		return idsurvivor;
	}

	public void setIdsurvivor(int idsurvivor) {
		this.idsurvivor = idsurvivor;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLonx() {
		return lonx;
	}

	public void setLonx(int lonx) {
		this.lonx = lonx;
	}

	public int getLony() {
		return lony;
	}

	public void setLony(int lony) {
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

	public long getLostpoints() {
		return lostpoints;
	}

	public void setLostpoints(long lostpoints) {
		this.lostpoints = lostpoints;
	}


	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	/**
	 * Add link method used for HATEOAS in survivor resource...
	 * @param url uri path...
	 * @param rel rel attribute...
	 */
	public void addLink(String url, String rel){
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
	
}
