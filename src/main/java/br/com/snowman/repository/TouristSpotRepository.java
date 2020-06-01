package br.com.snowman.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.TouristSpot;

/**
 * @author luiz
 *  Repositório do ponto turístico
 */
@Repository
public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
	// Busca ponto turístico por nome
	@Query(value = "SELECT * FROM tourist t WHERE t.name like %:name% ", nativeQuery = true)
	public List<TouristSpot> findTouristSpotByName(@Param("name") String name);
	
	// Verifica existência de um ponto turístico por nome
	@Query(value = "SELECT * FROM tourist t WHERE t.name = :name LIMIT 1", nativeQuery = true)
	public TouristSpot findUniqueTouristSpotByName(@Param("name") String name);
	
	// Busca ponto turístico por usuário
	@Query(value = "SELECT * FROM tourist WHERE user_id = ?1 ", nativeQuery = true)
	public List<TouristSpot> findTouristSpotByUser(@Param("userId") long userId);
	
	// Buscar favoritos de um usuário específico
	@Query(value = "SELECT tourist.* FROM tourist WHERE tourist_id IN (SELECT tourist_id from favority where favority.user_id = ?1 "
			+ " and favorited = true) ", nativeQuery = true)
	public List<TouristSpot> findTouristByFavority(@Param("user_id") long userId);
}	

