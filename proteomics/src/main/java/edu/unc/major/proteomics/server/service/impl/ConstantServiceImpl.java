package edu.unc.major.proteomics.server.service.impl;

import edu.unc.major.proteomics.server.DataStore;
import edu.unc.major.proteomics.server.ProteomicsServlet;
import edu.unc.major.proteomics.share.service.ConstantService;

public class ConstantServiceImpl extends ProteomicsServlet implements ConstantService{

	private static final long serialVersionUID = 1L;
	
	public Boolean[] getMatchedSymbols(String[] geneNames) {
		Boolean[] matches = new Boolean[geneNames.length];
		for (int i = 0; i < geneNames.length; ++i) {
			matches[i] = DataStore.getGeneNames().containsKey(geneNames[i]);
		}
		return matches;
	}

}
