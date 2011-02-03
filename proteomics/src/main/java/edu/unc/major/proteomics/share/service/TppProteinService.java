package edu.unc.major.proteomics.share.service;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.TppProtein;

public interface TppProteinService extends RemoteService{
	public List<TppProtein> getProteins();
	
	public PageResults<TppProtein> getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length);
	
	public PageResults<List<TppProtein>> getListByBandId(List<Long> bandIds, final int start, final int length);
}
