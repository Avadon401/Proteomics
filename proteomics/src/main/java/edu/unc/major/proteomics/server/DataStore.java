package edu.unc.major.proteomics.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unc.major.proteomics.share.model.NcbiAlias;
import edu.unc.major.proteomics.share.model.NcbiGene;

public class DataStore {
	private final static Map<String,Set<Long>> geneNames = new HashMap<String,Set<Long>>();
	
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
			List<NcbiGene> genes = new ArrayList<NcbiGene>(q.list());
			for (NcbiGene i : genes) {
				Set<Long> geneIds = new HashSet<Long>();
				geneIds.add(i.getId());
				geneNames.put(i.getGeneName(), geneIds);
			}
			q = session.createQuery("from NcbiAlias a inner join a.genes");
			List<NcbiAlias> aliases = new ArrayList<NcbiAlias>(q.list());
			for (NcbiAlias i : aliases) {
				Set<Long> geneIds = new HashSet<Long>();
				for (NcbiGene j : i.getGenes()) {
					geneIds.add(j.getId());
				}				
				geneNames.put(i.getAlias(), geneIds);
			}
		}
	}
	
	public static Map<String,Set<Long>> getGeneNames() {
		return geneNames;
	}
}
