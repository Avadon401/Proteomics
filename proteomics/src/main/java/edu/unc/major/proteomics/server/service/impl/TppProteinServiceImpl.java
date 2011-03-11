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
import edu.unc.major.proteomics.share.model.TppProtein;
import edu.unc.major.proteomics.share.service.TppProteinService;

public class TppProteinServiceImpl extends ProteomicsServlet implements TppProteinService{

	private static final long serialVersionUID = 1L;

	public List<TppProtein> getProteins() {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from TppProtein");
		q.setMaxResults(5);
		List<TppProtein> proteins = new ArrayList<TppProtein>(q.list());
		session.getTransaction().commit();
		return proteins;
	}
	
	public PageResults<TppProtein> getByGeneSymbolsPage(Set<String> geneSymbols, final int start, final int length) {
		if (geneSymbols == null || geneSymbols.size() == 0) return null;
		Set<Long> geneIds = new HashSet<Long>(geneSymbols.size());
		for (String geneSymbol : geneSymbols) {
			if (DataStore.getGeneNames().containsKey(geneSymbol))
				geneIds.addAll(DataStore.getGeneNames().get(geneSymbol));
		}

		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.getNamedQuery("TppProtein.byGene.byId");
		q.setParameterList("id", geneIds);
		q.setFirstResult(start);
		q.setMaxResults(length);
		List<TppProtein> genes = new ArrayList<TppProtein>(q.list());
		
		q = session.getNamedQuery("TppProtein.byGene.byId,size");
		q.setParameterList("id", geneIds);
		int size = ((Long) q.uniqueResult()).intValue();
		
		PageResults<TppProtein> results = new PageResults<TppProtein>(genes,size);
		
		return results;
	}
	
	public PageResults<List<TppProtein>> getListByBandId(List<Long> bandIds, final int start, final int length) {
		if (bandIds == null || bandIds.size() == 0) return null;

		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<List<TppProtein>> ori = new ArrayList<List<TppProtein>>();
		List<List<TppProtein>> sorted = new ArrayList<List<TppProtein>>();
		
		for (Long id : bandIds) {
			Query q = session.getNamedQuery("TppProtein.byBand.byId");
			q.setParameter("id", id);	
			ori.add(q.list());
		}
		
		Set<Long> geneIds = new HashSet<Long>();

		for (int i = 0; i < ori.size(); i++) {
			for (int j = 0; j < ori.get(i).size(); j++) {
				TppProtein first = ori.get(i).get(j);
				if (!geneIds.contains(first.getGene().getId())) {
					geneIds.add(first.getGene().getId());		
					List<TppProtein> row = new ArrayList<TppProtein>();
					row.add(first);
					for (int k = i+1; k < ori.size(); k++) {
						for (TppProtein p : ori.get(k)) {
							if (p.getGene().getId() == first.getGene().getId()) {
								row.add(p);
								break;
							}
						}
					}
					sorted.add(row);
				}
			}
		}
		
		List<List<TppProtein>> output = new ArrayList<List<TppProtein>>();
		for (int i = start; i < Math.min(start+length,sorted.size()); i++) {
			output.add(sorted.get(i));
		}
		System.out.println(output.size());
		PageResults<List<TppProtein>> results = new PageResults<List<TppProtein>>(output,sorted.size());
		
		return results;
		//return new PageResults<List<TppProtein>>(new ArrayList<List<TppProtein>>(),sorted.size());
	}
}
