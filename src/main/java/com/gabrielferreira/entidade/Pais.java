package com.gabrielferreira.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tab_pais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude= {"marcas"})
public class Pais implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String sigla;
	
	private String nome;
	
	@OneToMany(mappedBy="pais",fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Marca> marcas = new ArrayList<Marca>();

}
