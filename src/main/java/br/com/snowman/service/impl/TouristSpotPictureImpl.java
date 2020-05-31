package br.com.snowman.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.com.snowman.model.Favority;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.TouristSpotPicture;
import br.com.snowman.model.Upvote;
import br.com.snowman.model.User;
import br.com.snowman.repository.FavorityRepository;
import br.com.snowman.repository.TouristSpotPictureRepository;
import br.com.snowman.repository.UpvoteRepository;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotPictureService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;
import br.com.snowman.util.MainUtil;

/**
 * @author luiz
 * Implementa a interface TouristSpotPictureService
 */
@Service
public class TouristSpotPictureImpl implements TouristSpotPictureService{

	@Autowired
	TouristSpotService touristSpotService;
	
	@Autowired
    UserService userService;
	
	@Autowired
	TouristSpotPictureRepository touristSpotPictureRepository;
	
	@Autowired
	HomeService homeService;
	
	@Autowired
	FavorityRepository favorityRepository;
	
	@Autowired
	UpvoteRepository upvoteRepository;
	
	// Conseguindo converter a base64 para byte a mesma é salva
	@Override
	public boolean saveImageIfValidate(TouristSpotPicture touristSpotPicture, long id) {
		try {
			TouristSpot tourist = touristSpotService.getOne(id);
			User user = userService.currentUserInSession();
			touristSpotPicture.setUser(user);
			touristSpotPicture.setTourist(tourist); 
			
			// Separo a parte inicial do arquivo da parte final para poder converter em byte
		   	String nameContent = MainUtil.getNameOfFile(touristSpotPicture.getBase64());
		   	String base64 = MainUtil.getBase64fFile(touristSpotPicture.getBase64());
		   	touristSpotPicture.setNamePicture(nameContent);
			touristSpotPictureRepository.insertInPictureTouristSpot(MainUtil.convertStringToByteFile(base64), nameContent,
					touristSpotPicture.getId(), user.getId());
			return true;
		}catch (Exception e) {
			System.err.println("Erro ao salvar imagem no ponto turístico: " + e.getMessage());
		}
		return false;
	}

	// Deleta imagem do ponto, a verificação do usuário ativo já ocorreu antes da chamada desse método
	@Override
	public String deleteImageFromTouristSpot(Map<String, String> requestParams) {
		try {
			// Pega o id da imagem pelo parâmetro da url em seguida consulta esse objeto no JPA para então deletar
			 long idTouristPicture = Long.parseLong(requestParams.get("idTouristPicture")) ;
			 long idImage = Long.parseLong(requestParams.get("idImage")) ;
			 TouristSpotPicture touristSpotPictureDelete = touristSpotPictureRepository.getOne(idImage);
			 touristSpotPictureRepository.delete(touristSpotPictureDelete);
	    	 
			 return "redirect:?id=" + idTouristPicture ;
		}catch(Exception e) {
			System.err.println("Erro ao converter parâmetros do deleteImage: " + e.getMessage());
			return "error";
		}
	}

	// Atribui model somente se tiver autenticado, então adicionando usuário, se já foi favoritado e se já foi votado
	@Override
	public Model setModelIfAuth(Model model, long id) {
		if (homeService.isAuthenticated()) {
			// Busca usuário, o ponto turístico e o favorito em questão para configurar no model
			User user = userService.currentUserInSession();
			TouristSpot ts = touristSpotService.getOne(id);
			model.addAttribute("currentUserId", user.getId());
			Favority favority = favorityRepository.findFavorityByTouristUser(ts.getId(), user.getId());
			if (favority != null) {
				model.addAttribute("isFavority", favority.isFavorited());
			}

			Upvote upvote = upvoteRepository.findUpvoteByTouristUser(ts.getId(), user.getId());
			if (upvote != null) {
				model.addAttribute("isUp", upvote.isUp());
			}
			
		}
		return model;
	}

	// Converte byte imagem para base 64 do TouristSpot (mainPicture e touristPictures)
	@Override
	public TouristSpot convertByteToBase64(TouristSpot touristSpot) {
		String base64 = "";
		// Itera para verificar as imagens que os usuário adicionaram ao ponto turístico convertendo-as para base64 
		for(int i = 0; i<touristSpot.getTouristPictures().size(); i++) {
			TouristSpotPicture current = touristSpot.getTouristPictures().get(i);
			base64 = MainUtil.convertByteToStringFile(current.getPicture());
			base64 = current.getNamePicture() + "," + base64;
			current.setBase64(base64);
		}
		
		base64 = "";
		// Converte também a foto principal exibida no ponto turístico
		base64 = MainUtil.convertByteToStringFile(touristSpot.getMainPicture());
		base64 = touristSpot.getNameMainPicture() + "," + base64;
		
		touristSpot.setBase64(base64);
		return touristSpot;
	}




}
