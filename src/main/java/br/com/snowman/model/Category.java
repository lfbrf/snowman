package br.com.snowman.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author luiz
 * A categoria está relacionada com o ponto turístico
 * As categorias inicias foram definidas para inicializar junto com o banco (ver data.sql)
 */
@Entity
@Table(name = "category")
public class Category  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Category() {}
	
	public Category(Long id) {
		this.id = id;
	}
	
	public Category(String name, boolean status) {
		this.name = name;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

	@Column(nullable = false)
	@NotBlank
	private String name;

	@Column()
	private boolean status;
}
