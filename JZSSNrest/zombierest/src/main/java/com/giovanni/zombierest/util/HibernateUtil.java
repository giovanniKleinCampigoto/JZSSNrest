/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giovanni.zombierest.util;

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

	/**
	 * Builds an session factory for DB acess, this is used for CRUD operations in the database
	 * using Hibernate framework...
	 * @return returns given session factory...
	 */
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
	 * Opens a session and returns it
	 *
	 * @return returns the current session
	 */
	public static Session getSession() {
		return getSessionFactory().openSession();
	}

	/**
	 * Returns a instance (object) of this utility class, so it isn't mapped again in the memory..
	 *
	 * @return Returns a instance (object) of this utility class
	 */
	public static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	// Tests the connection
	public static void testConnection() {
		getSessionFactory().openSession();
	}

	// Closes the connection
	public static void closeConnection() {
		getSessionFactory().close();
	}

}
