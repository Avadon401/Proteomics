package edu.unc.major.proteomics.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unc.major.proteomics.server.DataStore;
import edu.unc.major.proteomics.server.ProteomicsServlet;
import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.service.BandService;

public class BandServiceImpl extends ProteomicsServlet implements BandService{

	private static final long serialVersionUID = 1L;

	public Band getById(final long id) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.getNamedQuery("Band.byId");
		q.setParameter("id", id);
		Band band = (Band) q.uniqueResult();	
		return band;
	}
	
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
		Set<Band> bands = new HashSet<Band>(q.list());	
		return bands;
	}
	
	public PageResults<Band> getByGeneSymbolsPage(Set<String> geneSymbols, final int start, final int length) {
		if (geneSymbols == null || geneSymbols.size() == 0) return null;
		Set<Long> geneIds = new HashSet<Long>(geneSymbols.size());
		for (String geneSymbol : geneSymbols) {
			if (DataStore.getGeneNames().containsKey(geneSymbol))
				geneIds.addAll(DataStore.getGeneNames().get(geneSymbol));
		}

		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.getNamedQuery("Band.byGene.byId");
		q.setParameterList("id", geneIds);
		q.setFirstResult(start);
		q.setMaxResults(length);
		List<Band> genes = new ArrayList<Band>(q.list());
		
		q = session.getNamedQuery("Band.byGene.byId,size");
		q.setParameterList("id", geneIds);
		int size = ((Long) q.uniqueResult()).intValue();
		
		PageResults<Band> results = new PageResults<Band>(genes,size);
		
		return results;
	}
	
	
	
}
