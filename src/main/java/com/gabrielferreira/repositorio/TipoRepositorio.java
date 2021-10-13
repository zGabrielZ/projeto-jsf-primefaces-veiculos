package com.gabrielferreira.repositorio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.repositorio.generico.RepositorioGenerico;

public class TipoRepositorio extends RepositorioGenerico<Tipo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Tipo> getTipos(String tipoCarro){
		Query query = getEntityManager().createNamedQuery("Tipos.findAll");
		query.setParameter("tipoCarro", "%"+tipoCarro+"%");
		
		List<Object[]> objs = query.getResultList();
		List<Tipo> tipos = new ArrayList<Tipo>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 
			 Tipo tipo = new Tipo();
			 
			 tipo.setId((Integer) aux[0]);
			 tipo.setTipoCarro((String)aux[1]);
		     tipos.add(tipo);
		}
	
		return tipos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> getVeiculos(Integer idTipo){
		Query query = getEntityManager().createNamedQuery("Tipos.findById");
		query.setParameter("idTipo", idTipo);
		
		List<Object[]> objs = query.getResultList();
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		
		for (Object[] o : objs) {
			 Object[] aux = o;
			 
			 Veiculo veiculo = new Veiculo();
			 Marca marcaVeiculos = new Marca();
			  
			 veiculo.setId((Integer) aux[0]);
			 veiculo.setModelo((String)aux[1]);
			 veiculo.setDataLancamento((Date) aux[2]);
			 veiculo.setPreco((BigDecimal) aux[3]);
			 veiculo.setCor((String) aux[4]);
			 
			 marcaVeiculos.setNome((String)aux[5]);
			 veiculo.setMarca(marcaVeiculos);
			 
		     veiculos.add(veiculo);
		}
	
		return veiculos;
	}

}
