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
	private Long geneID;
	
	public Bait() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public Long getGeneID() {
		return geneID;
	}
	
	public void setGeneID(final Long geneID) {
		this.geneID = geneID;
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
	
	public String getMutatedName() {
		String val = getGene().getGeneName();
		if (!"".equals(getMutation())) {
			val += " (" + getMutation() + ")";
		}
		return val;
	}
	
	
}

