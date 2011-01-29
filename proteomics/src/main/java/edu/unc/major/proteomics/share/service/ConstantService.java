package edu.unc.major.proteomics.share.service;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ConstantService extends RemoteService{
	public Boolean[] getMatchedSymbols(String[] geneNames);
}