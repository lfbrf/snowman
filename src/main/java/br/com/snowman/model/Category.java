package br.com.snowman.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

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
	
	
	
	@ManyToOne
    @JoinColumn(name="tourist_id")
	private TouristSpot touristSpot;
	

  
	
	public TouristSpot getTourist() {
		return touristSpot;
	}

	public void setTouristSpot(TouristSpot touristSpot) {
		this.touristSpot = touristSpot;
	}

	@Column(nullable = false)
	@NotBlank
	private String name;

	@Column()
	private boolean status;
}
