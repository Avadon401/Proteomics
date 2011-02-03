package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.SiRNAVal;

public interface SiRNAValService extends RemoteService{
	public PageResults<SiRNAVal> getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length);
}
