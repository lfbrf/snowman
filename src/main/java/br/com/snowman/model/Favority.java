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
 * A tabela favorito pode ter vários pontos turísticos e vários usuários
 */
@Entity
@Table(name = "favority")
public class Favority implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Favority(TouristSpot tourist, User user,boolean favorited) {
		this.favorited = favorited;
		this.user = user;
		this.tourist = tourist;
	}
	
	public Favority() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favority_id")
	private Long id;

	@Column(name = "favorited")
	private boolean favorited;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="tourist_id", nullable=true)
    private TouristSpot tourist;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=true)
    private User user;

}
