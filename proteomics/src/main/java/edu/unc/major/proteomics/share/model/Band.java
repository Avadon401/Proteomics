package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.view.client.ProvidesKey;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Band extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Bait bait;
	private String name;
	private Set<TppProtein> proteins = new HashSet<TppProtein>();
	private Integer proteinCount;
	
	public Band() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public Bait getBait() {
		return bait;
	}
	
	public void setBait(final Bait bait) {
		this.bait = bait;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	
	public Set<TppProtein> getProteins() {
		return proteins;
	}
	
	public void setProteins(final Set<TppProtein> proteins) {
		this.proteins = proteins;
	}
	
	public Integer getProteinCount() {
		return proteinCount;
	}
	
	public void setProteinCount(final Integer proteinCount) {
		this.proteinCount = proteinCount;
	}
	
}
