package com.gabrielferreira.repositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.repositorio.generico.RepositorioGenerico;

public class MarcaRepositorio extends RepositorioGenerico<Marca>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Marca> getMarcasPais(){
		String jpql = "SELECT m FROM Marca m join m.pais p order by m.id desc";
		TypedQuery<Marca> query = entityManager.createQuery(jpql, Marca.class);
		List<Marca> marcas = query.getResultList();
		return marcas;
	}
	
	public List<Marca> getMarcasByPais(Integer idPais){
		String jpql = "SELECT m FROM Marca m join m.pais p where p.id = :idPais";
		TypedQuery<Marca> query = entityManager.createQuery(jpql, Marca.class);
		query.setParameter("idPais", idPais);
		List<Marca> marcas = query.getResultList();
		return marcas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Marca> getMarcas(String nome, String sede, String pais){
		Query query = entityManager.createNamedQuery("Marcas.findAll");
		query.setParameter("nome", "%"+nome+"%");
		query.setParameter("sede", "%"+sede+"%");
		query.setParameter("pais", "%"+pais+"%");
		
		List<Object[]> objs = query.getResultList();
		List<Marca> marcas = new ArrayList<Marca>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 
			 Marca marca = new Marca();
			 Pais paisMarca = new Pais();
			 
			 
			 
			 marca.setId((Integer) aux[0]);
			 marca.setNome((String)aux[1]);
			 marca.setDataFundacao((Date) aux[2]);
			 marca.setSede((String) aux[3]);
			 
			 paisMarca.setSigla((String) aux[4]);			 
			 paisMarca.setNome((String) aux[5]);
			 
			 
			 marca.setPais(paisMarca);
		     marcas.add(marca);
		}
	
		return marcas;
	}

}
