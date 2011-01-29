package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NcbiGeneServiceAsync {
	public void getGeneSymbols(AsyncCallback<Set<String>> callback);
}