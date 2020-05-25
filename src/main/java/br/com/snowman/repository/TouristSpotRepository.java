package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.TouristSpot;

@Repository
public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
	@Query(value = "SELECT * FROM tourist t WHERE t.name like %:name% ", nativeQuery = true)
	public TouristSpot findTouristSpotByName(@Param("name") String name);
}	

