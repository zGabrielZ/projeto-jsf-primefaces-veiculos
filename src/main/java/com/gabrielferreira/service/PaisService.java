package com.gabrielferreira.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.exception.RegraDeNegocioException;
import com.gabrielferreira.repositorio.PaisRepositorio;

public class PaisService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisRepositorio paisRepositorio;
	
	public void inserir(Pais pais) throws RegraDeNegocioException {
		verificarNome(pais.getNome(), getPaises());
		String sigla = pais.getSigla().toUpperCase();
		pais.setSigla(sigla);
		paisRepositorio.inserir(pais);
	}
	
	public Pais atualizar(Pais pais) throws RegraDeNegocioException {
		verificarNomeSalvoAtualizar(getPaises(), pais);
		return paisRepositorio.atualizar(pais);
	}
	
	public Pais pesquisarPorId(Integer id) {
		return paisRepositorio.pesquisarPorId(id, Pais.class);
	}
	
	public void remover(Pais pais) {
		paisRepositorio.deletarPorId(Pais.class, pais.getId());
	}
	
	public List<Pais> getPaises(){
		return paisRepositorio.listagem(Pais.class);
	}
	
	private void verificarNome(String nome, List<Pais> paises) throws RegraDeNegocioException {
		for(Pais pais : paises) {
			if(pais.getNome().equals(nome)) {
				throw new RegraDeNegocioException("Este nome já foi inserido, por favor tente outro nome !");
			}
		}
	}
	
	private void verificarNomeSalvoAtualizar(List<Pais> paises, Pais pais) throws RegraDeNegocioException {
		for(Pais p : paises) {
			if(!p.getId().equals(pais.getId())) {
				if(p.getNome().equals(pais.getNome())) {
					throw new RegraDeNegocioException("Não é possível atualizar, pois já existe este nome cadastrado !");
				}
			}
		}
	}

}
