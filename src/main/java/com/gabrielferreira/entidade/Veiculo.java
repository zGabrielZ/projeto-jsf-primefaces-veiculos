package com.gabrielferreira.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tab_veiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude= {"tipo","marca"})
public class Veiculo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String modelo;
	
	@Column(name="data_lancamento")
	private Date dataLancamento;
	
	private BigDecimal preco;
	
	private String cor;
	
	@ManyToOne
	@JoinColumn(name="tipo_id")
	private Tipo tipo;
	
	@ManyToOne
	@JoinColumn(name="marca_id")
	private Marca marca;
	

}
