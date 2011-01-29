package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.Set;

import net.sf.gilead.pojo.gwt.LightEntity;

public class NcbiAlias extends LightEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String alias;
	private Set<NcbiGene> genes;
	
	public NcbiAlias () {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(final String alias) {
		this.alias = alias;
	}
	
	public Set<NcbiGene> getGenes() {
		return genes;
	}
	
	public void setGenes(final Set<NcbiGene> genes) {
		this.genes = genes;
	}
}

