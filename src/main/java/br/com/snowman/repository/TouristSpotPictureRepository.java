package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.TouristSpotPicture;

@Repository
public interface TouristSpotPictureRepository extends JpaRepository<TouristSpotPicture, Long> {
	
}	