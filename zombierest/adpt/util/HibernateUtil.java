/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

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

    static {
        try {
            Configuration configuration = new Configuration().configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException he) {
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
        return sessionFactory;
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

    //Self explanatory
    public static void testConnection() {
        getSessionFactory().openSession();
    }
    
    public static void closeConnection(){
        getSessionFactory().close();
    }

}
