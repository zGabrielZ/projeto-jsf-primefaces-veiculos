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
import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.exception.RegraDeNegocioException;
import com.gabrielferreira.service.MarcaService;
import com.gabrielferreira.service.PaisService;
import com.gabrielferreira.service.VeiculoService;
import com.gabrielferreira.util.FacesMessages;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class MarcaController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MarcaService marcaService;
	
	@Inject
	private PaisService paisService;
	
	@Inject
	private VeiculoService veiculoService;
	
	private Marca marca;
	
	private Pais pais;
	
	private List<Marca> marcas;
	
	private List<Veiculo> veiculos;
	
	private Marca marcaSelecionado;
	
	private String mensagem;
	
	@PostConstruct
	public void iniciar() {
		marca = new Marca();
		pais = new Pais();
		marca.setPais(pais);
		pais.getMarcas().add(marca);
		marcas = marcaService.getMarcasPais();
		verificarParametro();
	}
	
	public List<SelectItem> getPaises(){
		return paisService.getPaisesMarcas();
	}
	
	private void verificarParametro() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idMarca = params.get("codigoMarca");
		
		if(idMarca != null) {
			marca = marcaService.pesquisarPorId(Integer.parseInt(idMarca));
		}
		
	}
	
	public void inserirOuAtualizar() {
		if(marca.getId() == null) {
			inserir();
		} else {
			atualizar();
		}
	}
	
	public void novo() {
		marca = new Marca();
		pais = new Pais();
		marca.setPais(pais);
		pais.getMarcas().add(marca);
	}
	
	private void inserir() {
		try {
			marcaService.inserir(marca);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Cadastrado com sucesso !!",null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	private void atualizar() {
		try {
			marcaService.atualizar(marca);
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_INFO,"Atualizado com sucesso !!",null);
			novo();
		} catch (RegraDeNegocioException e) {
			FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
		}
	}
	
	public void remover() {
		marcaService.remover(marcaSelecionado);
		FacesMessages.adicionarMensagem("frmConsulta:msg",FacesMessage.SEVERITY_INFO,"Deletado com sucesso !!",null);
		iniciar();
	}
	
	public void pegarRegistro(Marca marca) {
		marcaSelecionado = marca;
		List<Veiculo> veiculos = veiculoService.findVeiculosByMarca(marcaSelecionado.getId());
		if(veiculos.isEmpty()) {
			mensagem = "Tem certeza que deseja excluir esta marca ?";
		} else {
			mensagem = "Tem certeza que deseja excluir esta marca ?, tem veículos associados.";
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('meuDialogo').show();");
	}

}
