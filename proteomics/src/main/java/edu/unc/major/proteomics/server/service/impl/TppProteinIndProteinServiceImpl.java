package edu.unc.major.proteomics.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.unc.major.proteomics.server.persistence.HibernateUtil;
import edu.unc.major.proteomics.share.model.TppProteinIndProtein;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinService;

public class TppProteinIndProteinServiceImpl extends RemoteServiceServlet implements TppProteinIndProteinService{

	private static final long serialVersionUID = 1L;

	public List<TppProteinIndProtein> getProteins() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from TppProteinIndProtein");
		q.setMaxResults(5);
		List<TppProteinIndProtein> proteins = new ArrayList<TppProteinIndProtein>(q.list());
		session.getTransaction().commit();
		return proteins;
	}
	
}
