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
import edu.unc.major.proteomics.share.model.SiRNAVal;
import edu.unc.major.proteomics.share.model.TppProtein;
import edu.unc.major.proteomics.share.service.SiRNAValService;

public class SiRNAValServiceImpl extends ProteomicsServlet implements SiRNAValService{

	private static final long serialVersionUID = 1L;

	public PageResults<SiRNAVal> getByGeneSymbolsPage(Set<String> geneSymbols, int start, int length) {
		if (geneSymbols == null || geneSymbols.size() == 0) return null;
		Set<Long> geneIds = new HashSet<Long>(geneSymbols.size());
		for (String geneSymbol : geneSymbols) {
			if (DataStore.getGeneNames().containsKey(geneSymbol))
				geneIds.addAll(DataStore.getGeneNames().get(geneSymbol));
		}

		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.getNamedQuery("SiRNAVal.byGene.byId");
		q.setParameterList("id", geneIds);
		q.setFirstResult(start);
		q.setMaxResults(length);
		List<SiRNAVal> siRNA = new ArrayList<SiRNAVal>(q.list());
		
		q = session.getNamedQuery("SiRNAVal.byGene.byId,size");
		q.setParameterList("id", geneIds);
		int size = ((Long) q.uniqueResult()).intValue();
		
		PageResults<SiRNAVal> results = new PageResults<SiRNAVal>(siRNA,size);
		
		return results;
	}
	
}
