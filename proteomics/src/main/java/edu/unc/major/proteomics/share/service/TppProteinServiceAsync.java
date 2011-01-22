package edu.unc.major.proteomics.share.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.unc.major.proteomics.share.model.TppProtein;

public interface TppProteinServiceAsync {
	public void getProteins(AsyncCallback<List<TppProtein>> callback);
}
