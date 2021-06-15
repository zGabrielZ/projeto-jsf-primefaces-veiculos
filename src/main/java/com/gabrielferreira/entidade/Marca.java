package com.gabrielferreira.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tab_marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude= {"pais","veiculos"})
public class Marca implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@Column(name="data_fundacao")
	private Date dataFundacao;
	
	private String sede;
	
	@ManyToOne
	@JoinColumn(name="pais_id")
	private Pais pais;
	
	@OneToMany(mappedBy="marca",fetch=FetchType.LAZY)
	private List<Veiculo> veiculos = new ArrayList<Veiculo>();

}
