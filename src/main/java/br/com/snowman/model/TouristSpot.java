package br.com.snowman.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tourist")
public class TouristSpot implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TouristSpot(String name, boolean status, String mainPicture,  Category category, User user) {
		this.name = name;
		this.status = status;
		this.mainPicture = mainPicture;
		this.category = category;
		this.user = user;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tourist_id")
	private Long id;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	@NotBlank
	private String name;
	
	@Column() 
	private boolean status;
	

	@Column() 
	private String latLocalization;
	
	@Column() 
	private String longLocalization;
	
	@Column() 
	private long numberVotes;
	
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

	public long getNumberVotes() {
		return numberVotes;
	}

	public void setNumberVotes(long numberVotes) {
		this.numberVotes = numberVotes;
	}
	@Lob
    @Column
    private String mainPicture;
	
	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String picture) {
		this.mainPicture = picture;
	}

	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Category category;
	
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	
	 
	@OneToMany(mappedBy="tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TouristSpotPicture> touristPictures;
	
	
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

	@OneToMany(mappedBy="tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Favority> favority;
	
	@OneToMany(mappedBy="tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Favority> upvote;
	
	
	

	public List<TouristSpotPicture> getTouristPictures() {
		return touristPictures;
	}

	public void setTouristPictures(List<TouristSpotPicture> touristPictures) {
		this.touristPictures = touristPictures;
	}
	
	
	

	
}
