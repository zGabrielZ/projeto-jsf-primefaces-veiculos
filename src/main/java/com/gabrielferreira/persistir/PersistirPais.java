package com.gabrielferreira.persistir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.util.EntityManagerUtil;

public class PersistirPais {

	public static void main(String[] args) {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		List<Pais> paises = new ArrayList<Pais>();
		
		Pais pais1 = new Pais(null, "EUA", "Estados Unidos América" , null);
		Pais pais2 = new Pais(null, "ALE", "Alemanha" , null);
		Pais pais3 = new Pais(null, "ITA", "Itália" , null);
		Pais pais4 = new Pais(null, "JAP", "Japão" , null);
		Pais pais5 = new Pais(null, "FRA", "França" , null);
		Pais pais6 = new Pais(null, "CHI", "China" , null);
		
		paises.addAll(Arrays.asList(pais1,pais2,pais3,pais4,pais5,pais6));
		
		for(Pais pais : paises) {
			entityManager.getTransaction().begin();
			entityManager.persist(pais);
			System.out.println("País persistido : " + pais);
			entityManager.getTransaction().commit();
		}
		
	}
	
}
