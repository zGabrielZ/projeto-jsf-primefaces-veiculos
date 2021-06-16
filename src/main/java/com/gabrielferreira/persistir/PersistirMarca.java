package com.gabrielferreira.persistir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.util.EntityManagerUtil;

public class PersistirMarca {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		List<Marca> marcas = new ArrayList<Marca>();
		
		List<Pais> paises = new ArrayList<>();
		
		Pais pais1 = entityManager.find(Pais.class, 7);
		Pais pais2 = entityManager.find(Pais.class, 8);
		Pais pais3 = entityManager.find(Pais.class, 9);
		Pais pais5 = entityManager.find(Pais.class, 11);
		
		Marca marca1 = new Marca(null,"Chevrolet",sdf.parse("03/11/1911"),"Detroit", pais1, null);
		Marca marca2 = new Marca(null,"Volkswagen",sdf.parse("28/05/1937"),"Wolfsburg", pais2, null);
		Marca marca3 = new Marca(null,"Fiat",sdf.parse("11/07/1898"),"Turim", pais3, null);
		Marca marca4 = new Marca(null,"Renault",sdf.parse("01/10/1898"),"Boulogne-Billancourt", pais5, null);
		Marca marca5 = new Marca(null,"Ford",sdf.parse("16/06/1903"),"Michigan", pais1, null);
		
		pais1.getMarcas().addAll(Arrays.asList(marca1,marca5));
		pais2.getMarcas().add(marca2);
		pais3.getMarcas().add(marca3);
		pais5.getMarcas().add(marca4);
		
		
		marcas.addAll(Arrays.asList(marca1,marca2,marca3,marca4,marca5));
		paises.addAll(Arrays.asList(pais1,pais2,pais3,pais5));
		
		// Persistindo Marca 
		
		for(Marca marca : marcas) {
			entityManager.getTransaction().begin();
			entityManager.persist(marca);
			System.out.println("Marca persistido : " + marca);
			entityManager.getTransaction().commit();
		}
		
		// Update no Pais
		
		for(Pais pais : paises) {
			entityManager.getTransaction().begin();
			entityManager.merge(pais);
			System.out.println("País update : " + pais);
			entityManager.getTransaction().commit();
		}
		
		
		
		
	}
	
}
