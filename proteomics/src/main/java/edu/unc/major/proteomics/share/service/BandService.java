package edu.unc.major.proteomics.share.service;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.unc.major.proteomics.share.model.Band;

public interface BandService extends RemoteService{
	public Set<Band> getByGeneSymbols(Set<String> geneSymbols);
}