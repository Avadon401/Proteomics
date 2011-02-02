package edu.unc.major.proteomics.share.model;

import java.io.Serializable;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SiRNAVal extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private SiRNAData data;
	private Double amount;
	private String treatment;
	private Double repOverViaMean;
	private Double repOverViaMedian;
	private Double repOverViaStd;
	private Double repOverViaZ;
	private Double repOverViaP;
	private Double repOverViaNegLogP;
	private Double viaMean;
	private Double viaMedian;
	private Double viaStd;
	private Double viaZ;
	private Double viaP;
	private Double viaNegLogP;
	
	public SiRNAVal() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getTreatment() {
		return treatment;
	}
	
	public void setTreatment(final String treatment) {
		this.treatment = treatment;
	}
	
	public SiRNAData getData() {
		return data;
	}
	
	public void setData(final SiRNAData data) {
		this.data = data;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(final Double amount) {
		this.amount = amount;
	}
	
	public Double getRepOverViaMean() {
		return repOverViaMean;
	}
	
	public void setRepOverViaMean(final Double repOverViaMean) {
		this.repOverViaMean = repOverViaMean;
	}
	
	public Double getRepOverViaMedian() {
		return repOverViaMedian;
	}
	
	public void setRepOverViaMedian(final Double repOverViaMedian) {
		this.repOverViaMedian = repOverViaMedian;
	}
	
	public Double getRepOverViaStd() {
		return repOverViaStd;
	}
	
	public void setRepOverViaStd(final Double repOverViaStd) {
		this.repOverViaStd = repOverViaStd;
	}
	
	public Double getRepOverViaZ() {
		return repOverViaZ;
	}
	
	public void setRepOverViaZ(final Double repOverViaZ) {
		this.repOverViaZ = repOverViaZ;
	}
	
	public Double getRepOverViaP() {
		return repOverViaP;
	}
	
	public void setRepOverViaP(final Double repOverViaP) {
		this.repOverViaP = repOverViaP;
	}
	public Double getRepOverViaNegLogP() {
		return repOverViaNegLogP;
	}
	
	public void setRepOverViaNegLogP(final Double repOverViaNegLogP) {
		this.repOverViaNegLogP = repOverViaNegLogP;
	}
	
	public Double getViaMean() {
		return viaMean;
	}
	
	public void setViaMean(final Double viaMean) {
		this.viaMean = viaMean;
	}
	
	public Double getViaMedian() {
		return viaMedian;
	}
	
	public void setViaMedian(final Double viaMedian) {
		this.viaMedian = viaMedian;
	}
	
	public Double getViaStd() {
		return viaStd;
	}
	
	public void setViaStd(final Double viaStd) {
		this.viaStd = viaStd;
	}
	
	public Double getViaZ() {
		return viaZ;
	}
	
	public void setViaZ(final Double viaZ) {
		this.viaZ = viaZ;
	}
	
	public Double getViaP() {
		return viaP;
	}
	
	public void setViaP(final Double viaP) {
		this.viaP = viaP;
	}
	public Double getViaNegLogP() {
		return viaNegLogP;
	}
	
	public void setViaNegLogP(final Double viaNegLogP) {
		this.viaNegLogP = viaNegLogP;
	}
}
