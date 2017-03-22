package com.giovanni.zombierest.model;

/**
 * HATEOAS model...contains the uri to the mapped endpoints and it's rel.
 * @author giovanni
 *
 */
public class Link {

	private String link,rel;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
	
	
	
}
