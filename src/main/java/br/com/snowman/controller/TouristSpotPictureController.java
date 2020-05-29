package br.com.snowman.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.snowman.dao.TouristSpotDao;
import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.TouristSpotPicture;
import br.com.snowman.model.User;
import br.com.snowman.repository.TouristSpotPictureRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;

@Controller
@RequestMapping("/turistico")
public class TouristSpotPictureController {
	@Autowired
	HomeService homeService;

	@Autowired
	TouristSpotRepository touristSpotRepository;

	@Autowired
	TouristSpotPictureRepository touristSpotPictureRepository;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/image")
	public  String  saveImage(Model model, @RequestParam("id") long id, 
			@ModelAttribute("touristUploadImage") TouristSpotPicture touristSpotPicture) {
		if (!homeService.isAuthenticated()) {
			return "error";
		}
		TouristSpot tourist = touristSpotRepository.getOne(id);
		UserDetails ud = homeService.getCurrentUserInSession();
		User user = userRepository.findUserByFaceId(ud.getId());
		touristSpotPicture.setUser(user);
		touristSpotPicture.setTourist(tourist);
		touristSpotPicture.setPicture(touristSpotPicture.getPicture());
		touristSpotPictureRepository.insertInPictureTouristSpot(touristSpotPicture.getPicture(), id, user.getId());
		return "redirect:?id=" + id ;
	}

	// @RequestParam("idTouristPicture") long idTouristPicture, @RequestParam("idImage"
	@DeleteMapping("/image")
	public  String  deleteImage(Model model, 
			@RequestParam Map<String,String> requestParams) {
		System.out.println("ENTRA DELETE $$$$ ");
		if (!homeService.isAuthenticated()) {
			return "error";
		}
		try {
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

	@GetMapping("")
	public  String  listById(Model model, @RequestParam("id") long id) {
		model.addAttribute("currentUserId", (long) -1);
		if (homeService.isAuthenticated()) {
			UserDetails ud = homeService.getCurrentUserInSession();
			User user = userRepository.findUserByFaceId(ud.getId());
			
			if (user != null) {
				model.addAttribute("currentUserId", user.getId());
			}
		}


		model.addAttribute("auth", homeService.isAuthenticated());
		TouristSpot ts = touristSpotRepository.getOne(id);
		model.addAttribute("touristById", ts);
		TouristSpotPicture t = new TouristSpotPicture();
		model.addAttribute("touristUploadImage", t);

		return "touristDetail";
	}

}
