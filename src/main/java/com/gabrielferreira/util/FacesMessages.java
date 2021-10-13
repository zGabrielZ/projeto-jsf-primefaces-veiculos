package com.gabrielferreira.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FacesMessages {

	public static void adicionarMensagem(String clientId, Severity severity,String sumario, String detalhe) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(severity, sumario, detalhe);
		facesContext.addMessage(clientId, facesMessage);
	}
	
}
