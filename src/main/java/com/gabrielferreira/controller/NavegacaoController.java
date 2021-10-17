package com.gabrielferreira.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class NavegacaoController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void home() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/HomePrincipal.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void cadastroMarca() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/marca/CadastroMarca.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void consultaMarca() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/marca/ConsultaMarca.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void cadastroPais() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/pais/CadastroPaises.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void consultaPais() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/pais/ConsultaPaises.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void cadastroTipoCarro() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/tipo-carro/CadastroTipoCarro.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void consultaTipoCarro() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/tipo-carro/ConsultaTipoCarro.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void tipoCarro() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/tipo-carro/relatorio/GerarTipoCarro.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void marca() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/marca/relatorio/GerarMarca.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
	public void veiculos() {
	    ExternalContext externalContext = getExternalContext();
	    try {
	          externalContext.redirect(externalContext.getRequestContextPath()
	                + "/veiculos/relatorio/GerarVeiculos.xhtml");
	    } catch (IOException e) {
	          e.printStackTrace();
	    }
	}
	
}
