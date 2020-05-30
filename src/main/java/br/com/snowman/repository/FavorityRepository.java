package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.snowman.model.Favority;

@Repository
public interface FavorityRepository extends JpaRepository<Favority, Long> {
	
	@Query(value = "SELECT * FROM favority WHERE tourist_id = ?1 and user_id = ?2  LIMIT 1", nativeQuery = true)
	public Favority findFavorityByTouristUser(@Param("tourist_id") long touristId, @Param("user_id")  long userId);
	
	@Modifying 
	@Query(value = " update favority set favorited = :favorited where tourist_id = :tourist_id and user_id = :user_id ", nativeQuery = true)
	@Transactional
	public int updateFavority(@Param("favorited") boolean favorited, @Param("tourist_id") long touristId, @Param("user_id") long userId);
	
}	
