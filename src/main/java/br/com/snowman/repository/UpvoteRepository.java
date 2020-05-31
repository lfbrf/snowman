package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.Upvote;

/**
 * @author luiz
 * Repositório para controlar votos nos pontos turísticos
 */
@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
	// Retorna um objeto referente ao voto de um usuário em um ponto turístico 
	@Query(value = "SELECT * FROM upvote WHERE tourist_id = ?1 and user_id = ?2  LIMIT 1", nativeQuery = true)
	public Upvote findUpvoteByTouristUser(@Param("tourist_id") long touristId, @Param("user_id")  long userId);
	
	// Retorna número total de votos em um ponto turístico
	@Query(value = "select count(upvote_id) from upvote where up = true and tourist_id = ?1 ", nativeQuery = true)
	public int getCurrenteUpvote(@Param("tourist_id") long touristId);
}	