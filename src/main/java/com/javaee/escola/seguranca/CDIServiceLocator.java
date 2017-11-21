package com.javaee.escola.seguranca;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Possibilita a obtencao de um bean CDI dentro de um objeto nao gerenciado pelo
 * CDI.
 */
public class CDIServiceLocator {
	
	private static Log LOGGER = LogFactory.getLog(CDIServiceLocator.class);

	private static BeanManager getBeanManager() {
		BeanManager beanManager = null;
		InitialContext initialContext = null;
		try {
			initialContext = new InitialContext();
			// JNDI name defined by spec
			return (BeanManager) initialContext.lookup("java:comp/BeanManager");
		} catch (final Exception ex0) {
			// LOGGER.error("Erro de LookUP[java:comp/BeanManager]: " +
			// ex0.getMessage(), ex0);
			try {
				// JNDI name used by Tomcat and Jetty
				beanManager = (BeanManager) initialContext.lookup("java:comp/env/BeanManager");
			} catch (final Exception ex1) {
				// LOGGER.error("Erro de LookUP[java:comp/env/BeanManager] " +
				// ex1.getMessage(), ex1);
				try {
					return CDI.current().getBeanManager();
				} catch (final Exception ex2) {
					LOGGER.error("Erro de LookUP[CDI.current().getBeanManager()]: " + ex2.getMessage(), ex2);
					return null;
				}
			}
		} finally {
			if (initialContext != null) {
				try {
					initialContext.close();
				} catch (final NamingException ignored) {
					LOGGER.error("Erro ao finalizar CDIServiceLocator: " + ignored.getMessage(), ignored);
				}
			}
		}

		return beanManager;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		BeanManager bm = getBeanManager();
		if (bm != null) {
			Set<Bean<?>> beans = (Set<Bean<?>>) bm.getBeans(clazz);

			if (beans == null || beans.isEmpty()) {
				return null;
			}

			Bean<T> bean = (Bean<T>) beans.iterator().next();

			CreationalContext<T> ctx = bm.createCreationalContext(bean);
			T o = (T) bm.getReference(bean, clazz, ctx);

			return o;
		}
		return null;
	}

}
