package edu.unc.major.proteomics.client.ui.widget;

import edu.unc.major.proteomics.client.Application;

public class InputListWidgetSearch extends InputListWidget {
	public void getMatches(String[] geneNames) {
		this.geneNames = geneNames;
		Application.constantService.getMatchedSymbols(geneNames, callback);
	}
};
