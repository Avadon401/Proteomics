package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Experiment extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Bait bait;
	private String name;
	private Protocol biologicalMaterial;
	private Protocol affinityPurification;
	private Protocol peptidePreparation;
	private Protocol lcms;
	private String notes;
	//private date
	
	public Experiment() {}
	
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
	
	public String getNiceName() {
		if (name.contains("---")) {
			return name.substring(name.indexOf("---")+3);
		}
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public Protocol getBiologicalMaterial() {
		return biologicalMaterial;
	}
	
	public void setBiologicalMaterial(final Protocol biologicalMaterial) {
		this.biologicalMaterial = biologicalMaterial;
	}
	
	public Protocol getAffinityPurification() {
		return affinityPurification;
	}
	
	public void setAffinityPurification(final Protocol affinityPurification) {
		this.affinityPurification = affinityPurification;
	}
	
	public Protocol getPeptidePreparation() {
		return peptidePreparation;
	}
	
	public void setPeptidePreparation(final Protocol peptidePreparation) {
		this.peptidePreparation = peptidePreparation;
	}
	
	public Protocol getLcms() {
		return lcms;
	}
	
	public void setLcms(final Protocol lcms) {
		this.lcms = lcms;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(final String notes) {
		this.notes = notes;
	}
	
	public String getBiologicalMaterialName(){
		return biologicalMaterial != null ? biologicalMaterial.getName() : "";
	}
	
	public String getPeptidePreparationName(){
		return peptidePreparation != null ? peptidePreparation.getName() : "";
	}
	
	public String getAffinityPurificationName(){
		return affinityPurification != null ? affinityPurification.getName() : "";
	}
	
	public String getLcmsName(){
		return lcms != null ? lcms.getName() : "";
	}
}

