package br.com.snowman.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author luiz
 * Vários usuários pode votar em um ou mais ponto turistico
 */
@Entity
@Table(name = "upvote")
public class Upvote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Upvote(TouristSpot tourist, User user, boolean up) {
		this.tourist = tourist;
		this.user = user;
		this.up = up;
	}
	
	public Upvote() {}

	public TouristSpot getTourist() {
		return tourist;
	}

	public void setTourist(TouristSpot tourist) {
		this.tourist = tourist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "up")
	private boolean up;

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "upvote_id")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="tourist_id", nullable=true)
    private TouristSpot tourist;


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=true)
    private User user;
}
