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
import com.gabrielferreira.service.MarcaService;
import com.gabrielferreira.service.TipoService;
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
	private TipoService tipoService;
	
	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private MarcaService marcaService;
	
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
		tipoService.getGerarRelatorioTipo(tipoCarroSearch.getTipoCarro());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório de tipos gerado com sucesso !",
				null);
		tipoCarroSearch = new TipoCarroSearch();
	}
	
	public void gerarRelatorioMarca() {
		marcaService.getGerarRelatorioMarca(marcaSearch.getNome(),marcaSearch.getSede(), marcaSearch.getPais());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório de marcas gerado com sucesso !",
				null);
		marcaSearch = new MarcaSearch();
	}
	
	public void gerarRelatorioVeiculos() {
		veiculoService.getGerarRelatorioVeiculo(veiculoSearch.getModelo(), veiculoSearch.getCor(), veiculoSearch.getTipoCarro(), 
				veiculoSearch.getMarca());
		FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_INFO, "Relatório de veículos gerado com sucesso !",
				null);
		veiculoSearch = new VeiculoSearch();
	}

}
