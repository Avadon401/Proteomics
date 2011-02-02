package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class CellLine extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public CellLine() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
}