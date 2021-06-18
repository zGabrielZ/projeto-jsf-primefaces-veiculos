package com.gabrielferreira.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.gabrielferreira.entidade.search.MarcaSearch;
import com.gabrielferreira.entidade.search.TipoCarroSearch;
import com.gabrielferreira.entidade.search.VeiculoSearch;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class GerarRelatorioController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TipoCarroSearch tipoCarroSearch;
	
	private MarcaSearch marcaSearch;
	
	private VeiculoSearch veiculoSearch;
	
	@PostConstruct
	public void init() {
		tipoCarroSearch = new TipoCarroSearch();
		marcaSearch = new MarcaSearch();
		veiculoSearch = new VeiculoSearch();
	}
	
	public void gerarRelatorioTipoCarro() {
		System.out.println("Gerar relatorio tipo de carro");
	}
	
	public void gerarRelatorioMarca() {
		System.out.println("Gerar relatorio marca");
	}
	
	public void gerarRelatorioVeiculos() {
		System.out.println("Gerar relatorio veiculos");
	}

}
