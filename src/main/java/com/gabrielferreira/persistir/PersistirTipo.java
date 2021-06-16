package com.gabrielferreira.persistir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.util.EntityManagerUtil;

public class PersistirTipo {

	public static void main(String[] args) {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		List<Tipo> tipos = new ArrayList<Tipo>();
		
		Tipo tipo1 = new Tipo(null, "Hatch", null);
		Tipo tipo2 = new Tipo(null, "Sedan", null);
		Tipo tipo3 = new Tipo(null, "SUV", null);
		Tipo tipo4 = new Tipo(null, "Crossover", null);
		Tipo tipo5 = new Tipo(null, "Minivan", null);
		Tipo tipo6 = new Tipo(null, "Picape (Truck)", null);
		Tipo tipo7 = new Tipo(null, "Wagon", null);
		Tipo tipo8 = new Tipo(null, "Diesel", null);
		Tipo tipo9 = new Tipo(null, "Elétrico", null);
		Tipo tipo10 = new Tipo(null, "Híbrido", null);
		Tipo tipo11 = new Tipo(null, "Conversível", null);
		Tipo tipo12 = new Tipo(null, "Cupê (Coupe)", null);
		Tipo tipo13 = new Tipo(null, "Luxo", null);
		
		tipos.addAll(Arrays.asList(tipo1,tipo2,tipo3,tipo4,tipo5,tipo6,tipo7,tipo8,tipo9,tipo10,tipo11,tipo12,tipo13));
		
		for(Tipo tipo : tipos) {
			entityManager.getTransaction().begin();
			entityManager.persist(tipo);
			System.out.println("Tipo persistido : " + tipo);
			entityManager.getTransaction().commit();
		}
		
	}
	
}
