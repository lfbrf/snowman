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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 * @author luiz
 * Um ponto turístico tem um autor e uma categoria
 * Além disso, pode ter muitas fotos e muitas reações (favorito ou votos)
 */
@Entity
@Table(name = "tourist")
public class TouristSpot implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TouristSpot(String name, boolean status, byte[] mainPicture,  Category category, User user) {
		this.name = name;
		this.status = status;
		this.mainPicture = mainPicture;
		this.category = category;
		this.user = user;
	}
	
	public TouristSpot(String name, byte[] mainPicture,String nameMainPicture, boolean status, 
			User user ,  Category category, String latLocalization, String longLocalization) {
		this.name = name;
		this.mainPicture = mainPicture;
		this.nameMainPicture = nameMainPicture;
		this.status = status;
		this.user = user;
		this.category = category;
		this.latLocalization = latLocalization;
		this.longLocalization = longLocalization;
	}
	
	public TouristSpot() {}
	
	public TouristSpot(long id) {
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


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public byte[] getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(byte[] picture) {
		this.mainPicture = picture;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List<Favority> getFavority() {
		return favority;
	}

	public void setFavority(List<Favority> favority) {
		this.favority = favority;
	}

	public List<Upvote> getUpvote() {
		return upvote;
	}

	public void setUpvote(List<Upvote> upvote) {
		this.upvote = upvote;
	}
	
	public List<TouristSpotPicture> getTouristPictures() {
		return touristPictures;
	}

	public void setTouristPictures(List<TouristSpotPicture> touristPictures) {
		this.touristPictures = touristPictures;
	}
	
	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	public String getNameMainPicture() {
		return nameMainPicture;
	}

	public void setNameMainPicture(String nameMainPicture) {
		this.nameMainPicture = nameMainPicture;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tourist_id")
	private Long id;
	
	@Column(nullable = false)
	@NotBlank
	private String name;
	
	@Column() 
	private boolean status;

	@Column() 
	private String latLocalization;
	
	@Column() 
	private String longLocalization;

	@Lob
    @Column
    @NotBlank
    private byte[] mainPicture;
	
	@Column()
	@NotBlank
	private String nameMainPicture; 
	
	@Transient
	private String base64;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Category category;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	 
	@OneToMany(mappedBy="tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TouristSpotPicture> touristPictures;
	
	@OneToMany(mappedBy="tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Favority> favority;
	
	@OneToMany(mappedBy="tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Upvote> upvote;
	
}
