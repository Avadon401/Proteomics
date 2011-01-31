package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unc.major.proteomics.share.model.Band;

public interface BandServiceAsync {
	public void getByGeneSymbols(Set<String> geneSymbols, AsyncCallback<Set<Band>> callback);
}
