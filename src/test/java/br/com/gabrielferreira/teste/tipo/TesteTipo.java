package br.com.gabrielferreira.teste.tipo;

import java.util.List;

import org.junit.Test;

import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.repositorio.TipoRepositorio;

public class TesteTipo {

	public TipoRepositorio tipoRepositorio = new TipoRepositorio();
	
	@Test
	public void inserir() {
		Tipo tipo = new Tipo(null,"Teste",null);
		tipoRepositorio.inserir(tipo);
		System.out.println(tipo);
	}
	
	@Test
	public void atualizar() {
		Tipo tipo = tipoRepositorio.pesquisarPorId(27, Tipo.class);
		tipo.setTipoCarro("Teste 2");
		Tipo novoTipo = tipoRepositorio.atualizar(tipo);
		System.out.println(novoTipo);
	}
	
	@Test
	public void listagem() {
		List<Tipo> tipos = tipoRepositorio.listagem(Tipo.class);
		for(Tipo tipo : tipos) {
			System.out.println(tipo);
		}
	}
	
	@Test
	public void remover() {
		tipoRepositorio.deletarPorId(Tipo.class, 27);
		System.out.println("Tipo removido");
	}
	
}
