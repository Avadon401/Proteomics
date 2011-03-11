package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Protocol  extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private String type;
	
	public Protocol() {
		
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setType(final String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}