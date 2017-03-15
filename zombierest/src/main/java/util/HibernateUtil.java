/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author giovanni
 */
public class HibernateUtil {

	private static HibernateUtil instance = null;
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory buildSessionFactory() {
		try {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("hibernate.cfg.xml").build();

			Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();

			return metadata.getSessionFactoryBuilder().build();

		} catch (

		HibernateException he) {
			System.err.println("Erro ao criar a conexao com o base de dados: " + he);
			throw new ExceptionInInitializerError(he);
		}

	}

	

	/**
	 * Returns the current session factory
	 *
	 * @return returns the current session factory
	 */
	public static SessionFactory getSessionFactory() {
		return buildSessionFactory();
	}

	/**
	 * Returns the current session
	 *
	 * @return returns the current session
	 */
	public static Session getSession() {
		return getSessionFactory().openSession();
	}

	/**
	 * Returns a instance (object) of this class
	 *
	 * @return Returns a instance (object) of this class
	 */
	public static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	// Self explanatory
	public static void testConnection() {
		getSessionFactory().openSession();
	}

	public static void closeConnection() {
		getSessionFactory().close();
	}

}
