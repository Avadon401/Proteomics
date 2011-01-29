package edu.unc.major.proteomics.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unc.major.proteomics.share.model.NcbiAlias;
import edu.unc.major.proteomics.share.model.NcbiGene;
import edu.unc.major.proteomics.share.model.NcbiGo;

public class DataStore {
	private final static Set<String> geneNames = new HashSet<String>();
	
	private final static DataStore instance = new DataStore();
	
	public static DataStore getInstance() {
		return instance;
	}
	
	protected DataStore() {
	}
	
	public static synchronized void initializeConstraints(Session session) {
		if (geneNames.size() == 0) {
			session.beginTransaction();
			Query q = session.createQuery("from NcbiGene");
			//q.setMaxResults(1000);
			List<NcbiGene> genes = new ArrayList<NcbiGene>(q.list());
			for (NcbiGene i : genes) {
				geneNames.add(i.getGeneName());
			}
			q = session.createQuery("from NcbiAlias");
			//q.setMaxResults(1000);
			List<NcbiAlias> aliases = new ArrayList<NcbiAlias>(q.list());
			for (NcbiAlias i : aliases) {
				geneNames.add(i.getAlias());
			}
		}
	}
	
	public static Set<String> getGeneNames() {
		return geneNames;
	}
}
