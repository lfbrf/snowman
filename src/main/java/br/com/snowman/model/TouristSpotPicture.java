package br.com.snowman.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * @author luiz
 * Essa entidade representa as diversas imagens que um ponto turístico pode ter
 * portanto essa está relacionada á varios potnos turísticos e vários usuários
 */
@Entity
@Table(name = "tourist_picture")
public class TouristSpotPicture implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TouristSpotPicture(){}
	
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TouristSpot getTourist() {
		return tourist;
	}

	public void setTourist(TouristSpot tourist) {
		this.tourist = tourist;
	}
	
	public String getNamePicture() {
		return namePicture;
	}

	public void setNamePicture(String namePicture) {
		this.namePicture = namePicture;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	@Lob
	@Column
    private byte[] picture;

	@Column()
	@NotBlank
	private String namePicture; 
	
	@Transient
	private String base64;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tourist_picture_id")
	private Long id;
	
	@ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="tourist_id", nullable=false)
    private TouristSpot tourist;
	
	@ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
