package br.com.snowman.service;

import java.util.List;
import java.util.Set;

import org.springframework.ui.Model;

import br.com.snowman.model.TouristSpot;

/**
 * @author luiz
 *
 */
public interface TouristSpotService {
	TouristSpot getOne(long id);
	List<TouristSpot> findTouristByFavority();
	List<TouristSpot> findTouristSpotByName(String name);
	List<TouristSpot> findTouristSpotByCurrentUser();
	List<TouristSpot> findAll();
	Model setModelLatLngIfHasTourist(Model model);
	Set<TouristSpot>  convertBase64ToBinaryImage();
	TouristSpot  saveTouristIfAuthAndVaidated(TouristSpot touristSpot);
}
