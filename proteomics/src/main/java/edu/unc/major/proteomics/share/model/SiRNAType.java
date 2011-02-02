package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SiRNAType extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String type;
	
	public SiRNAType() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(final String type) {
		this.type = type;
	}
	
}
