package edu.unc.major.proteomics.share.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.unc.major.proteomics.share.model.TppProtein;

public interface TppProteinService extends RemoteService{
	public List<TppProtein> getProteins();
}
