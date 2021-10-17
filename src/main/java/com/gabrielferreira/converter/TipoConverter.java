package com.gabrielferreira.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.service.TipoService;
import com.gabrielferreira.util.FacesMessages;

@Named
@RequestScoped
public class TipoConverter implements Converter{
	
	@Inject
	private TipoService tipoService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Tipo tipo = null;
		
		if(value != null) {
			if(value.contains("Selecione")) {
				FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,"Selecione um tipo",null);
			} else {
				tipo = tipoService.pesquisarPorId(new Integer(value));
			}
		}
		
		return tipo;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Tipo tipo = (Tipo) value;
			return String.valueOf(tipo.getId());
		}
		
		return null;
	}

}
