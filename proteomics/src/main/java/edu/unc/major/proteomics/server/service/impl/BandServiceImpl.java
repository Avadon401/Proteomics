package edu.unc.major.proteomics.server.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unc.major.proteomics.server.DataStore;
import edu.unc.major.proteomics.server.ProteomicsServlet;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.service.BandService;

public class BandServiceImpl extends ProteomicsServlet implements BandService{

	private static final long serialVersionUID = 1L;

	public Set<Band> getByGeneSymbols(Set<String> geneSymbols) {
		if (geneSymbols == null || geneSymbols.size() == 0) return null;
		Set<Long> geneIds = new HashSet<Long>(geneSymbols.size());
		for (String geneSymbol : geneSymbols) {
			geneIds.addAll(DataStore.getGeneNames().get(geneSymbol));
		}
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.getNamedQuery("Band.byGene.byId");
		q.setParameterList("id", geneIds);
		Set<Band> genes = new HashSet<Band>(q.list());	
		return genes;
	}
	
}
