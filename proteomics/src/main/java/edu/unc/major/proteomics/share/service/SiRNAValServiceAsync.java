package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.SiRNAVal;

public interface SiRNAValServiceAsync {
	public void getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length, AsyncCallback<PageResults<SiRNAVal>> callback);
}
