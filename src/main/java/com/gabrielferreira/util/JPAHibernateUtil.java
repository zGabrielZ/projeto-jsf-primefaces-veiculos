package com.gabrielferreira.util;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAHibernateUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static EntityManagerFactory emf = null;
	
	// Não vai ser preciso instanciar, caso chama a classe JPAHibernateUtil, ja vai iniciar a conexão
	
	static {
		init();
	}
	
	private static void init() {
		try {
			if(emf == null) {
				emf = Persistence.createEntityManagerFactory("Projeto-Veiculos");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Prover a parte de persistencia
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
