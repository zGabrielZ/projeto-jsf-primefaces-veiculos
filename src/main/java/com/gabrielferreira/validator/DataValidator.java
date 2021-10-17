package com.gabrielferreira.validator;

import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.sun.faces.util.MessageFactory;

@Named
@RequestScoped
public class DataValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date date = (Date) value;
		if(date != null) {
			
			Object label = MessageFactory.getLabel(context, component);
			
			Date dataAtual = new Date();
			Calendar calendarDate = getCalendar(date);
			Calendar calendarDataAtual = getCalendar(dataAtual);
			
			if(calendarDate.after(calendarDataAtual)) {
				String descricaoErro = label + ": Não é possível cadastrar esta data, a data não pode ser maior que a data atual !";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,descricaoErro,null);
				throw new ValidatorException(message);
			}
			
		}
	}
	
	private Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}
