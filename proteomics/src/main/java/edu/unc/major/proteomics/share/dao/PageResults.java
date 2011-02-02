package edu.unc.major.proteomics.share.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResults<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<T> results;
	private int size;
	
	public PageResults() {
		this(new ArrayList<T> (), 0);
	}
	
	public PageResults(final List<T> results, final int size) {
		this.results = results;
		this.size = size;
	}
	
	public List<T> getResults() {
		return results;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	public void setSize(final int size) {
		this.size = size;
	}
}
