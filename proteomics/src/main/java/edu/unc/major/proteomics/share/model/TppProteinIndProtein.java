package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class TppProteinIndProtein extends LightEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer geneID;
	private String geneName;
	private String identifier;
	
	public TppProteinIndProtein () {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public Integer getGeneID() {
		return geneID;
	}
	
	public void setGeneID(final Integer geneID) {
		this.geneID = geneID;
	}
	
	public String getGeneName() {
		return geneName;
	}
	
	public void setGeneName(final String geneName) {
		this.geneName = geneName;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}
}
