package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.sf.gilead.pojo.gwt.LightEntity;

public class NcbiGene extends LightEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String geneName;
	private String description;
	private Set<NcbiAlias> aliases = new HashSet<NcbiAlias>();
	private Set<NcbiGo> goTerms = new HashSet<NcbiGo>();
	
	public NcbiGene () {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getGeneName() {
		return geneName;
	}
	
	public void setGeneName(final String geneName) {
		this.geneName = geneName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public Set<NcbiAlias> getAliases() {
		return aliases;
	}
	
	public void setAliases(final Set<NcbiAlias> aliases) {
		this.aliases = aliases;
	}
	
	public Set<NcbiGo> getGoTerms() {
		return goTerms;
	}
	
	public void setGoTerms(final Set<NcbiGo> goTerms) {
		this.goTerms = goTerms;
	}
}
