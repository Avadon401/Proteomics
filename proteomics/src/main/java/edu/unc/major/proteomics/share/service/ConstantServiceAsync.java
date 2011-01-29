package edu.unc.major.proteomics.share.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConstantServiceAsync {
	public void getMatchedSymbols(String[] geneNames, AsyncCallback<Boolean[]> callback);
}
