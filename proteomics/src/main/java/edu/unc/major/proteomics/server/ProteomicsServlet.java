package edu.unc.major.proteomics.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Session;

public abstract class ProteomicsServlet extends PersistentRemoteService {
	
	public HibernateUtil gileadHibernateUtil;
	
	public void init(final ServletConfig sc) throws ServletException {
		super.init(sc);
		gileadHibernateUtil = new HibernateUtil(edu.unc.major.proteomics.server.persistence.HibernateUtil.getSessionFactory());
		PersistentBeanManager persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(gileadHibernateUtil);
		setBeanManager(persistentBeanManager);
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
		DataStore.initializeConstraints(session);
	}
}
