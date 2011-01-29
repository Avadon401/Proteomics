package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class NcbiGo extends LightEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private NcbiGene gene;
	private String goTerm;
	private String category;
	
	public NcbiGo () {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public NcbiGene getGene() {
		return gene;
	}
	
	public void setGene(final NcbiGene gene) {
		this.gene = gene;
	}
	
	public String getGoTerm() {
		return goTerm;
	}
	
	public void setGoTerm(final String goTerm) {
		this.goTerm = goTerm;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(final String category) {
		this.category = category;
	}
}

