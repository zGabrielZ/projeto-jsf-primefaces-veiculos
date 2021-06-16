package com.gabrielferreira.persistir;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.util.EntityManagerUtil;

public class PersistirVeiculo {

	public static void main(String[] args) throws ParseException {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		
		List<Tipo> tipos = new ArrayList<>();
		
		List<Marca> marcas = new ArrayList<>();
		
		Tipo tipo24 = entityManager.find(Tipo.class, 24); 
		Tipo tipo15 = entityManager.find(Tipo.class, 15); 
		Tipo tipo14 = entityManager.find(Tipo.class, 14); 
		Tipo tipo17 = entityManager.find(Tipo.class, 17); 
		
		Marca marca6 = entityManager.find(Marca.class, 6); 
		Marca marca8 = entityManager.find(Marca.class, 8); 
		Marca marca9 = entityManager.find(Marca.class, 9); 
		
		Veiculo veiculo1 = new Veiculo(null, "Camaro", sdf.parse("02/01/2009"), new BigDecimal("381.700"), "Amarelo", tipo24, marca6);
		Veiculo veiculo2 = new Veiculo(null, "Cruze", sdf.parse("02/01/2008"), new BigDecimal("110.690"), "Branco", tipo15, marca6);
		Veiculo veiculo3 = new Veiculo(null, "Onix", sdf.parse("02/01/2013"), new BigDecimal("56.290"), "Vermelho", tipo14, marca6);
		Veiculo veiculo4 = new Veiculo(null, "Captur", sdf.parse("02/01/2013"), new BigDecimal("69.990"), "Laranja", tipo17, marca9);
		Veiculo veiculo5 = new Veiculo(null, "Kwid", sdf.parse("02/01/2017"), new BigDecimal("39.390"), "Laranja", tipo17, marca9);
		Veiculo veiculo6 = new Veiculo(null, "Mobi", sdf.parse("02/01/2016"), new BigDecimal("38.990"), "Verelho", tipo14, marca8);
		Veiculo veiculo7 = new Veiculo(null, "Argo", sdf.parse("02/01/2017"), new BigDecimal("54.290"), "Branco", tipo14, marca8);
		
		tipo24.getVeiculos().add(veiculo1);
		tipo15.getVeiculos().add(veiculo2);
		tipo14.getVeiculos().addAll(Arrays.asList(veiculo3,veiculo6,veiculo7));
		tipo17.getVeiculos().addAll(Arrays.asList(veiculo4,veiculo5));
		
		marca6.getVeiculos().addAll(Arrays.asList(veiculo1,veiculo2,veiculo3));
		marca8.getVeiculos().addAll(Arrays.asList(veiculo6,veiculo7));
		marca9.getVeiculos().addAll(Arrays.asList(veiculo4,veiculo5));
		
		tipos.addAll(Arrays.asList(tipo14,tipo15,tipo17,tipo24));
		marcas.addAll(Arrays.asList(marca6,marca8,marca9));
		veiculos.addAll(Arrays.asList(veiculo1,veiculo2,veiculo3,veiculo4,veiculo5,veiculo6,veiculo7));
		
		// Persistindo Veiculos 
		
		for(Veiculo veiculo : veiculos) {
			entityManager.getTransaction().begin();
			entityManager.persist(veiculo);
			System.out.println("Veiculo persistido : " + veiculo);
			entityManager.getTransaction().commit();
		}
		
		// Update no Tipo
		
		for(Tipo tipo : tipos) {
			entityManager.getTransaction().begin();
			entityManager.merge(tipo);
			System.out.println("Tipo update : " + tipo);
			entityManager.getTransaction().commit();
		}
		
		// Update na Marca

		for (Marca marca : marcas) {
			entityManager.getTransaction().begin();
			entityManager.merge(marca);
			System.out.println("Marca update : " + marca);
			entityManager.getTransaction().commit();
		}
		
		
	}
	
}
