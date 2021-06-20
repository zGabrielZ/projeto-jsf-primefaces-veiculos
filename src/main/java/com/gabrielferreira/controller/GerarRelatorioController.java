package com.gabrielferreira.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.gabrielferreira.entidade.search.MarcaSearch;
import com.gabrielferreira.entidade.search.TipoCarroSearch;
import com.gabrielferreira.entidade.search.VeiculoSearch;
import com.gabrielferreira.service.VeiculoService;
import com.gabrielferreira.util.FacesMessages;

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
	
	@Inject
	private VeiculoService veiculoService;
	
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
		veiculoService.getGerarRelatorioVeiculo(veiculoSearch.getModelo(), veiculoSearch.getCor(), veiculoSearch.getTipoCarro(), 
				veiculoSearch.getMarca());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório de veículos gerado com sucesso !",
				null);
		veiculoSearch = new VeiculoSearch();
	}

}
