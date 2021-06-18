package com.gabrielferreira.entidade.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String modelo;
	private String cor;
	private String tipoCarro;
	private String marca;

}
