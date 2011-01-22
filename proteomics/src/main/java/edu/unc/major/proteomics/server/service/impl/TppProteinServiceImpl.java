package edu.unc.major.proteomics.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.unc.major.proteomics.share.model.TppProtein;
import edu.unc.major.proteomics.share.model.TppProteinIndProtein;
import edu.unc.major.proteomics.share.service.TppProteinService;

public class TppProteinServiceImpl extends PersistentRemoteService implements TppProteinService{
	
	HibernateUtil gileadHibernateUtil;
	
	public TppProteinServiceImpl() {
		gileadHibernateUtil = new HibernateUtil(edu.unc.major.proteomics.server.persistence.HibernateUtil.getSessionFactory());
		PersistentBeanManager persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(gileadHibernateUtil);
		setBeanManager(persistentBeanManager);
	}

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
