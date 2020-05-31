package br.com.snowman.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.TouristSpotPicture;
import br.com.snowman.service.FavorityService;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotPictureService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UpvoteService;
import br.com.snowman.service.UserService;

/**
 * @author luiz
 * Controller dos detalhes do ponto turístico, 
 * Usuário pode ver mais detalhes e se autenticado votar, favoritar, desfavoritar, enviar imagem e deletar a própria imagem
 */
@Controller
@RequestMapping("/turistico")
public class TouristSpotDetails {
	@Autowired
	HomeService homeService;

	@Autowired
	UserService userService;

	@Autowired
	TouristSpotService touristSpotService;

	@Autowired
	TouristSpotPictureService touristSpotPictureService;

	@Autowired
	UpvoteService upvoteService;

	@Autowired
	FavorityService favorityService;

	// Salva imagem do usuário a um ponto turístico
	@PostMapping("/image")
	public  String  saveImage(Model model, @RequestParam("id") long id, 
			@ModelAttribute("touristUploadImage") TouristSpotPicture touristSpotPicture) {
		if (!homeService.isAuthenticated()) {
			return "error";
		}

		touristSpotPictureService.saveImageIfValidate(touristSpotPicture, id);

		return "redirect:?id=" + id ;
	}

	// Deleta imagem de um ponto turístico
	@DeleteMapping("/image")
	public  String  deleteImage(Model model, 
			@RequestParam Map<String,String> requestParams) {
		if (!homeService.isAuthenticated()) {
			return "error";
		}

		return touristSpotPictureService.deleteImageFromTouristSpot(requestParams); 

	}

	// Ação votar em um ponto turístico
	@PostMapping("/votar")
	public  String  vote(Model model, @RequestParam("id") long id) {
		if (!homeService.isAuthenticated()) {
			return "error";
		}

		upvoteService.saveUserVote(id);
		return "redirect:?id=" + id ;
	}

	// Verifica existência do favorito e favorita
	@PostMapping("/favoritar")
	public  String  like(Model model, @RequestParam("id") long id) {
		if (!homeService.isAuthenticated()) {
			return "error";
		}

		favorityService.favorityUpdateOrSaveTouristSpot(id);
		return "redirect:?id=" + id ;
	}

	// Desfavorita um ponto turístico
	@PostMapping("/desfavoritar")
	public  String  dislike(Model model, @RequestParam("id") long id) {
		if (!homeService.isAuthenticated()) {
			return "error";
		}

		favorityService.desfavorityTouristSpot(id);
		return "redirect:?id=" + id ;
	}

	// Retorna os detalhes do ponto turístico previamente selecionado na home
	@GetMapping("")
	public  String  listById(Model model, @RequestParam("id") long id) {
		model.addAttribute("currentUserId", (long) -1);
		model.addAttribute("auth", homeService.isAuthenticated());
		model.addAttribute("isFavority", false);
		model.addAttribute("isUp", false);
		
		TouristSpot ts = touristSpotService.getOne(id);

		model = touristSpotPictureService.setModelIfAuth(model, id);
		ts = touristSpotPictureService.convertByteToBase64(ts);

		model.addAttribute("numberVotes", upvoteService.getCurrenteUpvote(id));
		model.addAttribute("touristById", ts);
		TouristSpotPicture t = new TouristSpotPicture();
		model.addAttribute("touristUploadImage", t);

		return "touristDetail";
	}

}
