package com.gabrielferreira.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gabrielferreira.util.FacesMessages;

@FacesConverter("dataConverter")
public class DataConverter implements Converter{
	
	private SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Date data = null;
		if(value != null && !value.equals("")) {
			// DD/MM/YYYY
			try {
				data = stf.parse(value);
			} catch (Exception e) {
				FacesMessages.adicionarMensagem("frmCadastro:msg",FacesMessage.SEVERITY_ERROR,"Formato incorreto : DD/MM/YYYY",null);
			}
		}
		
		return data;
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
