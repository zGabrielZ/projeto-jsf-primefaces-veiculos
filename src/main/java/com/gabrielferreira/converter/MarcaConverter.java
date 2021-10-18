package com.gabrielferreira.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.service.MarcaService;

@Named
@RequestScoped
public class MarcaConverter implements Converter{
	
	@Inject
	private MarcaService marcaService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Marca marca = null;
		
		if(value != null) {
			if(value.contains("Selecione")) {
				return null;
			} else {
				marca = marcaService.pesquisarPorId(new Integer(value));
			}
		}
		
		return marca;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Marca marca = (Marca) value;
			return String.valueOf(marca.getId());
		}
		
		return null;
	}

}
