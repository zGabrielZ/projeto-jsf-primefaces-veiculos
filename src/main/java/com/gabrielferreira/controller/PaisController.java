package com.gabrielferreira.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.exception.RegraDeNegocioException;
import com.gabrielferreira.service.MarcaService;
import com.gabrielferreira.service.PaisService;
import com.gabrielferreira.util.FacesMessages;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PaisController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisService paisService;
	
	@Inject
	private MarcaService marcaService;
	
	private Pais pais;
	
	private List<Pais> paises;
	
	private List<Marca> marcas;
	
	private Pais paisSelecionado;
	
	@PostConstruct
	public void iniciar() {
		pais = new Pais();
		paises = paisService.getPaises();
		marcas = new ArrayList<Marca>();
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idPais = params.get("codigoPaises");
		String idPaisMarcas = params.get("codigoPaisMarca");
		
		if(idPais != null) {
			pais = paisService.pesquisarPorId(Integer.parseInt(idPais));
		}
		
		if(idPaisMarcas != null) {
			marcas = marcaService.getMarcasByPais(Integer.parseInt(idPaisMarcas));
		}
		
	}
	
	public void inserirOuAtualizar() {
		if(pais.getId() == null) {
			inserir();
		} else {
			atualizar();
		}
	}
	
	public void novo() {
		pais = new Pais();
	}
	
	private void inserir() {
		try {
			paisService.inserir(pais);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Cadastrado com sucesso !!",null);
			pais = new Pais();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	private void atualizar() {
		try {
			paisService.atualizar(pais);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Atualizado com sucesso !!",null);
			pais = new Pais();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	public void remover() {
		paisService.remover(paisSelecionado);
		FacesMessages.adicionarMensagem("frmConsulta:msg",FacesMessage.SEVERITY_INFO,"Deletado com sucesso !!",null);
		iniciar();
	}
	
	public void pegarRegistro(Pais pais) {
		paisSelecionado = pais;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('meuDialogo').show();");
	}

}
