package edu.unc.major.proteomics.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unc.major.proteomics.server.ProteomicsServlet;
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
}
