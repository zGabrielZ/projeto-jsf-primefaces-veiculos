package com.gabrielferreira.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dataFundacaoConverter")
public class DataFundacaoConverter implements Converter{
	
	private SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Date date = (Date) value;
			String dataFormatada = stf.format(date);
			return dataFormatada;
		}
		return null;
	}

}
