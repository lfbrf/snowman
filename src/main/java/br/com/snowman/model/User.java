package br.com.snowman.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "tourist")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column() 
	private boolean status;
	
	@Column() 
	private String name;
	
	@Column() 
	private String idApi;
	
	@Column(nullable = true) 
	private String email;
}
