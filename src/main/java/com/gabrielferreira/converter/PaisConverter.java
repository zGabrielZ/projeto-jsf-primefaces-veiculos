package com.gabrielferreira.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.gabrielferreira.entidade.Pais;
import com.gabrielferreira.service.PaisService;

@Named
@RequestScoped
public class PaisConverter implements Converter{
	
	@Inject
	private PaisService paisService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pais pais = null;
		
		if(value != null) {
			if(value.contains("Selecione")) {
				return null;
			} else {
				pais = paisService.pesquisarPorId(new Integer(value));
			}
		}
		
		return pais;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Pais pais = (Pais) value;
			return String.valueOf(pais.getId());
		}
		
		return null;
	}

}
