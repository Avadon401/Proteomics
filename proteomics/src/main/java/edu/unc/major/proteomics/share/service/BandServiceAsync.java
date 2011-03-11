package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;

public interface BandServiceAsync {
	public void getByGeneSymbols(Set<String> geneSymbols, AsyncCallback<Set<Band>> callback);
	
	public void getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length, AsyncCallback<PageResults<Band>> callback);
	
	public void getById(final long id, AsyncCallback<Band> callback);
}
