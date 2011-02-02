package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SiRNAExperiment extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Pathway pathway;
	private String fileName;
	private Set<SiRNAData> data = new HashSet<SiRNAData>();
	
	public SiRNAExperiment() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}
	
	public Pathway getPathway() {
		return pathway;
	}
	
	public void setPathway(final Pathway pathway) {
		this.pathway = pathway;
	}
	
	public Set<SiRNAData> getData() {
		return data;
	}
	
	public void setData(final Set<SiRNAData> data) {
		this.data = data;
	}
	
}
