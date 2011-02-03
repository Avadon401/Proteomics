package edu.unc.major.proteomics.share.service;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.TppProtein;

public interface TppProteinServiceAsync {
	public void getProteins(AsyncCallback<List<TppProtein>> callback);
	
	public void getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length, AsyncCallback<PageResults<TppProtein>> callback);
	
	public void getListByBandId(List<Long> bandIds, final int start, final int length, AsyncCallback<PageResults<List<TppProtein>>> callback);
}
