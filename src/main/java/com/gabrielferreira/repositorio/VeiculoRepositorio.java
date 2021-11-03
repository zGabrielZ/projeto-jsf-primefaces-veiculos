package com.gabrielferreira.repositorio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.repositorio.generico.RepositorioGenerico;

public class VeiculoRepositorio extends RepositorioGenerico<Veiculo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Veiculo> listagem(){
		String jpql = "SELECT v FROM Veiculo v left join v.tipo t left join v.marca m order by v.id desc";
		TypedQuery<Veiculo> query = entityManager.createQuery(jpql, Veiculo.class);
		List<Veiculo> veiculos = query.getResultList();
		return veiculos;
	}
	
	public List<Veiculo> findVeiculosByTipo(Integer idCarro){
		String jpql = "SELECT v FROM Veiculo v join v.tipo t left join v.marca m where t.id = :idCarro";
		TypedQuery<Veiculo> query = entityManager.createQuery(jpql, Veiculo.class);
		query.setParameter("idCarro", idCarro);
		List<Veiculo> veiculos = query.getResultList();
		return veiculos;
	}
	
	public List<Veiculo> findVeiculosByMarca(Integer idMarca){
		String jpql = "SELECT v FROM Veiculo v join v.marca m where m.id = :idMarca";
		TypedQuery<Veiculo> query = entityManager.createQuery(jpql, Veiculo.class);
		query.setParameter("idMarca", idMarca);
		List<Veiculo> veiculos = query.getResultList();
		return veiculos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> getVeiculos(String modelo, String cor, String tipoCarro, String marca){
		Query query = entityManager.createNamedQuery("Veiculos.findAll");
		query.setParameter("modelo", "%"+modelo+"%");
		query.setParameter("cor", "%"+cor+"%");
		query.setParameter("tipo", "%"+tipoCarro+"%");
		query.setParameter("marca", "%"+marca+"%");
		
		List<Object[]> objs = query.getResultList();
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 
			 Veiculo veiculo = new Veiculo();
			 Marca marcaVeiculos = new Marca();
			 Tipo tipoVeiculos = new Tipo();
			 
			 
			 
			 veiculo.setId((Integer) aux[0]);
			 veiculo.setModelo((String)aux[1]);
			 
			 marcaVeiculos.setNome((String)aux[2]);
			 veiculo.setMarca(marcaVeiculos);
			 
			 veiculo.setPreco((BigDecimal) aux[3]);
			 veiculo.setDataLancamento((Date) aux[4]);
			 
			 tipoVeiculos.setTipoCarro((String) aux[5]);
			 veiculo.setTipo(tipoVeiculos);
			 
			 veiculo.setCor((String) aux[6]);
		     veiculos.add(veiculo);
		}
	
		return veiculos;
	}

}
