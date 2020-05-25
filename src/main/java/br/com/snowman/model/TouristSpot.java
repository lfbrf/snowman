package br.com.snowman.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;

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
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tourist")
public class TouristSpot implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TouristSpot(String name, boolean status, byte[] mainPicture, Set<Category> categories, User user) {
		this.name = name;
		this.status = status;
		this.mainPicture = mainPicture;
		this.categories = categories;
		this.user = user;
	}
	
	public TouristSpot() {}
	
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




	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
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
    private byte[] mainPicture;
	
	public byte[] getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(byte[] picture) {
		this.mainPicture = picture;
	}

	// In future maybe is necessary to add more than one category for touris spot
	@OneToMany(mappedBy="touristSpot")
	private Set<Category> categories;
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	
	
}
