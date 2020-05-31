package br.com.snowman.service;

import java.util.Map;

import org.springframework.ui.Model;

import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.TouristSpotPicture;

/**
 * @author luiz
 *
 */
public interface TouristSpotPictureService {
	boolean saveImageIfValidate(TouristSpotPicture touristSpotPicture, long id);
	String deleteImageFromTouristSpot(Map<String,String> requestParams);
	Model setModelIfAuth(Model model, long id);
	TouristSpot convertByteToBase64(TouristSpot touristSpot);
}
