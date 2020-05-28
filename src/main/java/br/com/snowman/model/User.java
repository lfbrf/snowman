package br.com.snowman.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "user")
public class User  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	public User(Long id){
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column() 
	private boolean status;
	
	@Column() 
	private String name;
	

	
	private String faceId;
	
	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public User() {}
	
	public User(String name, String email,  String faceId, boolean status) {
		this.name = name;
		this.email = email;
		this.faceId = faceId;
		this.status = status;
	} 

	@Column(nullable = true) 
	private String email;
	
	
	public List<Favority> getFavority() {
		return favority;
	}

	public void setFavority(List<Favority> favority) {
		this.favority = favority;
	}

	public List<Favority> getUpvote() {
		return upvote;
	}

	public void setUpvote(List<Favority> upvote) {
		this.upvote = upvote;
	}

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Favority> favority;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Favority> upvote;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TouristSpotPicture> touristPictures;

	public List<TouristSpotPicture> getTouristPictures() {
		return touristPictures;
	}

	public void setTouristPictures(List<TouristSpotPicture> touristPictures) {
		this.touristPictures = touristPictures;
	}

	

}
