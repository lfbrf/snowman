package br.com.snowman.repository;

import java.util.List;

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
	
	@Query(value = "SELECT * FROM tourist WHERE tourist_id = ?1 LIMIT 1", nativeQuery = true)
	public TouristSpot findTouristById(@Param("tourist_id") long id);
	
}	

