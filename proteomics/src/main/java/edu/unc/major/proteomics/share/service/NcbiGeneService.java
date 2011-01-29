package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

public interface NcbiGeneService extends RemoteService{
	public Set<String> getGeneSymbols();
}
