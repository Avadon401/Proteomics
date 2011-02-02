package edu.unc.major.proteomics.share.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SiRNAData extends LightEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private SiRNAType type;
	private NcbiGene gene;
	private NcbiAlias alias;
	private CellLine cellLine;
	private Integer num;
	private SiRNAExperiment experiment;
	private Set<SiRNAVal> vals = new HashSet<SiRNAVal>();
	
	public SiRNAData() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public SiRNAType getType() {
		return type;
	}
	
	public void setType(final SiRNAType type) {
		this.type = type;
	}
	
	public NcbiGene getGene() {
		return gene;
	}
	
	public void setGene(final NcbiGene gene) {
		this.gene = gene;
	}
	
	public NcbiAlias getAlias() {
		return alias;
	}
	
	public void setAlias(final NcbiAlias alias) {
		this.alias = alias;
	}
	
	public CellLine getCellLine() {
		return cellLine;
	}
	
	public void setCellLine(final CellLine cellLine) {
		this.cellLine = cellLine;
	}
	
	public Integer getNum() {
		return num;
	}
	
	public void setNum(final Integer num) {
		this.num = num;
	}
	
	public SiRNAExperiment getExperiment() {
		return experiment;
	}
	
	public void setExperiment(final SiRNAExperiment experiment) {
		this.experiment = experiment;
	}
	
	public Set<SiRNAVal> getVals() {
		return vals;
	}
	
	public void setVals(final Set<SiRNAVal> vals) {
		this.vals = vals;
	}
	
}
