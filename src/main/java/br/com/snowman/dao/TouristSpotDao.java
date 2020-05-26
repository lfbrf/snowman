package br.com.snowman.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;


public class TouristSpotDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public TouristSpotDao() {}
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String name;
	
	private boolean status;
	
	
    public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Lob
	private String picture;
	
	

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(long idCategory) {
		this.idCategory = idCategory;
	}
	public String getLatLocalization() {
		return latLocalization;
	}

	public void setLatLocalization(String latLocalization) {
		this.latLocalization = latLocalization;
	}

	public String getLongLocalization() {
		return longLocalization;
	}

	public void setLongLocalization(String longLocalization) {
		this.longLocalization = longLocalization;
	}
	
	private String latLocalization;
	private String longLocalization;
	private long idUser;
	private long idCategory;
	@Id
	private Long id;
	


	
}
