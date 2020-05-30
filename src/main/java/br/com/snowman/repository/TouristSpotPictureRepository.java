package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.snowman.model.TouristSpotPicture;
@Repository
public interface TouristSpotPictureRepository extends JpaRepository<TouristSpotPicture, Long> {
	
	@Modifying
	@Query(value = "insert into tourist_picture (picture, tourist_id, user_id) VALUES (:picture,:tourist_id,:user_id)", nativeQuery = true)
	@Transactional
	public int insertInPictureTouristSpot(@Param("picture") String picture, @Param("tourist_id") long touristId, @Param("user_id") long userId);
	
}	