package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Bait extends LightEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private NcbiGene gene;
	private String mutation;
	private Set<Band> bands = new HashSet<Band>();
	
	public Bait() {}
	
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
	
	public String getMutation() {
		return mutation;
	}
	
	public void setMutation(final String mutation) {
		this.mutation = mutation;
	}
	
	
	public Set<Band> getBands() {
		return bands;
	}
	
	public void setBands(final Set<Band> bands) {
		this.bands = bands;
	}
	
	
}

