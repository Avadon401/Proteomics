package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.sf.gilead.pojo.gwt.LightEntity;

public class TppProtein extends LightEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer geneID;
	private String proteinDec;
	private Float probability;
	private Integer totalNumPeptides;
	private Integer uniqueNumPeptides;
	private Float xpressRatioMean;
	private Float xpressRatioStdDev;
	private Set<TppProteinIndProtein> identifiers = new HashSet<TppProteinIndProtein>();
	
	public TppProtein() {}
	
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
	
	public String getProteinDec() {
		return proteinDec;
	}
	
	public void setProteinDec(final String proteinDec) {
		this.proteinDec = proteinDec;
	}
	
	public Float getProbability() {
		return probability;
	}
	
	public void setProbability(final Float probability) {
		this.probability = probability;
	}
	
	public Integer getTotalNumPeptides() {
		return totalNumPeptides;
	}
	
	public void setTotalNumPeptides(final Integer totalNumPeptides) {
		this.totalNumPeptides = totalNumPeptides;
	}
	
	public Integer getUniqueNumPeptides() {
		return uniqueNumPeptides;
	}
	
	public void setUniqueNumPeptides(final Integer uniqueNumPeptides) {
		this.uniqueNumPeptides = uniqueNumPeptides;
	}
	
	public Float getXpressRatioMean() {
		return xpressRatioMean;
	}
	
	public void setXpressRatioMean(final Float xpressRatioMean) {
		this.xpressRatioMean = xpressRatioMean;
	}
	
	public Float getXpressRatioStdDev() {
		return xpressRatioStdDev;
	}
	
	public void setXpressRatioStdDev(final Float xpressRatioStdDev) {
		this.xpressRatioStdDev = xpressRatioStdDev;
	}
	
	public Set<TppProteinIndProtein> getIdentifiers() {
		return identifiers;
	}
	
	public void setIdentifiers(final Set<TppProteinIndProtein> identifiers) {
		this.identifiers = identifiers;
	}
	
	
}
