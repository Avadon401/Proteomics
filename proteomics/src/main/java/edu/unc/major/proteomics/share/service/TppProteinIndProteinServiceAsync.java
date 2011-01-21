package edu.unc.major.proteomics.share.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unc.major.proteomics.share.model.TppProteinIndProtein;

public interface TppProteinIndProteinServiceAsync {
	public void getProteins(AsyncCallback<List<TppProteinIndProtein>> callback);
}
