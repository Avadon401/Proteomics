package edu.unc.major.proteomics.server.service.impl;

import java.util.Set;

import edu.unc.major.proteomics.server.DataStore;
import edu.unc.major.proteomics.server.ProteomicsServlet;
import edu.unc.major.proteomics.share.service.NcbiGeneService;

public class NcbiGeneServiceImpl extends ProteomicsServlet implements NcbiGeneService{

	private static final long serialVersionUID = 1L;

	public Set<String> getGeneSymbols() {
		return DataStore.getGeneNames();
	}
	
}
