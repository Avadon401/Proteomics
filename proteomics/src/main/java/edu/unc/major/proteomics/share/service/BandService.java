package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;

public interface BandService extends RemoteService{
	public Set<Band> getByGeneSymbols(Set<String> geneSymbols);
	
	public PageResults<Band> getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length);
	
	public Band getById(final long id);
}