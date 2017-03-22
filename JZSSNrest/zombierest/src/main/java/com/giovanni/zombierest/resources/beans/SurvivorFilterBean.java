package com.giovanni.zombierest.resources.beans;

import javax.ws.rs.FormParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * A class to filter certain parameters, using it on the methods in the survivor resource and subsequent
 * subresources...
 * @author giovanni
 *
 */
public class SurvivorFilterBean {
	@QueryParam("start")
	int start;
	@QueryParam("size")
	int size;
	@PathParam("survivorId")
	int survivorId;
	@PathParam("flaggedId")
	int flaggedId;
	
	@PathParam("with")
	int with;
	
	public int getWith() {
		return with;
	}

	public void setWith(int with) {
		this.with = with;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSurvivorId() {
		return survivorId;
	}

	public void setSurvivorId(int survivorId) {
		this.survivorId = survivorId;
	}

	public int getFlaggedId() {
		return flaggedId;
	}

	public void setFlaggedId(int flaggedId) {
		this.flaggedId = flaggedId;
	}

}
