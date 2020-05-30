package br.com.snowman.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.TouristSpot;

@Repository
public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
	@Query(value = "SELECT * FROM tourist t WHERE t.name like %:name% ", nativeQuery = true)
	public List<TouristSpot> findTouristSpotByName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM tourist t WHERE t.name = :name LIMIT 1", nativeQuery = true)
	public TouristSpot findUniqueTouristSpotByName(@Param("name") String name);
	 

	@Query(value = "SELECT * FROM tourist WHERE category_category_id = ?1 LIMIT 1", nativeQuery = true)
	public List<TouristSpot> findTouristSpotByCategory(@Param("categoryId") long categoryId);
	
	@Query(value = "SELECT * FROM tourist WHERE user_id = ?1 LIMIT 1", nativeQuery = true)
	public List<TouristSpot> findTouristSpotByUser(@Param("userId") long userId);
	
	@Query(value = "SELECT * FROM tourist WHERE tourist_id = ?1 LIMIT 1", nativeQuery = true)
	public TouristSpot findTouristById(@Param("tourist_id") long id);
	
	@Query("SELECT t FROM TouristSpot t WHERE t.id IN :ids")
	List<TouristSpot> findTouristSpotNearByIds(@Param("ids") int[] arrayIdToShow);
	
	@Query(value = "SELECT tourist.* FROM tourist WHERE tourist_id IN (SELECT tourist_id from favority where favority.user_id = ?1 "
			+ " and favorited = true) ", nativeQuery = true)
	public List<TouristSpot> findTouristByFavority(@Param("user_id") long userId);
}	

