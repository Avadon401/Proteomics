package edu.unc.major.proteomics.share.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.unc.major.proteomics.share.model.TppProteinIndProtein;

public interface TppProteinIndProteinService extends RemoteService{
	public List<TppProteinIndProtein> getProteins();
}
