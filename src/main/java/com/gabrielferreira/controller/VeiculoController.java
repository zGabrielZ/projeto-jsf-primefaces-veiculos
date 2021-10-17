package com.gabrielferreira.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.exception.RegraDeNegocioException;
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
public class VeiculoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private TipoService tipoService;
	
	@Inject
	private MarcaService marcaService;
	
	private Veiculo veiculo;
	
	private Tipo tipo;
	
	private Marca marca;
	
	private List<Veiculo> veiculos;
	
	private Veiculo veiculoSelecionado;
	
	@PostConstruct
	public void iniciar() {
		veiculo = new Veiculo();
		tipo = new Tipo();
		marca = new Marca();
		veiculo.setTipo(tipo);
		tipo.getVeiculos().add(veiculo);
		veiculo.setMarca(marca);
		marca.getVeiculos().add(veiculo);
		
		veiculos = veiculoService.getVeiculosListagem();
		verificarParametro();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idVeiculo = params.get("codigoVeiculos");
		
		if(idVeiculo != null) {
			veiculo = veiculoService.pesquisarPorId(Integer.parseInt(idVeiculo));
		}
		
	}
	
	public void inserirOuAtualizar() {
		if(veiculo.getId() == null) {
			inserir();
		} else {
			atualizar();
		}
	}
	
	public void novo() {
		veiculo = new Veiculo();
		tipo = new Tipo();
		marca = new Marca();
		veiculo.setTipo(tipo);
		tipo.getVeiculos().add(veiculo);
		veiculo.setMarca(marca);
		marca.getVeiculos().add(veiculo);
	}
	
	private void inserir() {
		try {
			veiculoService.inserir(veiculo);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Cadastrado com sucesso !!",null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	private void atualizar() {
		try {
			veiculoService.atualizar(veiculo);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Atualizado com sucesso !!",null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	public List<SelectItem> getTipos(){
		return tipoService.getTiposListagemSelectItem();
	}
	
	public List<SelectItem> getMarcas(){
		return marcaService.getMarcasListagemSelectItem();
	}
	
	public void remover() {
		veiculoService.remover(veiculoSelecionado);
		FacesMessages.adicionarMensagem("frmConsulta:msg",FacesMessage.SEVERITY_INFO,"Deletado com sucesso !!",null);
		iniciar();
	}
	
	public void pegarRegistro(Veiculo veiculo) {
		veiculoSelecionado = veiculo;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('meuDialogo').show();");
	}

}
