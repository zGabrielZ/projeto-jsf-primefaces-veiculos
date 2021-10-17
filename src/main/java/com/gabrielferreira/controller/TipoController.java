package com.gabrielferreira.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.exception.RegraDeNegocioException;
import com.gabrielferreira.service.TipoService;
import com.gabrielferreira.service.VeiculoService;
import com.gabrielferreira.util.FacesMessages;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class TipoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoService tipoService;
	
	@Inject
	private VeiculoService veiculoService;
	
	private Tipo tipo;
	
	private List<Tipo> tipos;
	
	private List<Veiculo> veiculos;
	
	private Tipo tipoSelecionado;
	
	private String mensagem;
	
	@PostConstruct
	public void iniciar() {
		tipo = new Tipo();
		tipos = tipoService.getTipos();
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idTipoCarro = params.get("codigoTipoCarro");
		String idTipoCarroVeiculos = params.get("codigoTipoCarroVeiculos");
		if(idTipoCarro != null) {
			tipo = tipoService.pesquisarPorId(Integer.parseInt(idTipoCarro));
		}

		if(idTipoCarroVeiculos != null) {
			veiculos = veiculoService.findVeiculosByTipoCarro(Integer.parseInt(idTipoCarroVeiculos));
		}

	}
	
	public void inserirOuAtualizar() {
		if(tipo.getId() == null) {
			inserir();
		} else {
			atualizar();
		}
	}
	
	public void novo() {
		tipo = new Tipo();
	}
	
	private void inserir() {
		try {
			tipoService.inserir(tipo);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Cadastrado com sucesso !!",null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	private void atualizar() {
		try {
			tipoService.atualizar(tipo);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Atualizado com sucesso !!",null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	public void remover() {
		tipoService.remover(tipoSelecionado);
		FacesMessages.adicionarMensagem("frmConsulta:msg",FacesMessage.SEVERITY_INFO,"Deletado com sucesso !!",null);
		iniciar();
	}
	
	public void pegarRegistro(Tipo tipo) {
		tipoSelecionado = tipo;
		List<Veiculo> veiculos = veiculoService.findVeiculosByTipoCarro(tipoSelecionado.getId());
		if(veiculos.isEmpty()) {
			mensagem = "Tem certeza que deseja excluir este tipo de carro ?";
		} else {
			mensagem = "Tem certeza que deseja excluir este tipo de carro ?, tem veículos associados.";
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('meuDialogo').show();");
	}

}
