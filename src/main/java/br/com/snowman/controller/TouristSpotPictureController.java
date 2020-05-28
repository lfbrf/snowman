package br.com.snowman.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/turistico/")
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
	    public  String  saveImage(Model model, HttpServletRequest request, HttpSession session, @RequestParam("id") long id, 
	    		@ModelAttribute("touristUploadImage") TouristSpotPicture touristSpotPicture) {
		 	if (!homeService.isAuthenticated()) {
		 		return "error";
		 	}
		 	System.out.println("#################");
		 
		 	System.out.println("ID passado" + id);
		 	
		 	System.out.println("----------");
		 	System.out.println(touristSpotPicture.getPicture());
		 	
		 	TouristSpot tourist = touristSpotRepository.getOne(id);
		 	touristSpotPicture.setTourist(tourist);
		 	UserDetails ud = homeService.getCurrentUserInSession();
		 	User user = userRepository.findUserByFaceId(ud.getId());
		 	touristSpotPicture.setUser(user);
		 	touristSpotPictureRepository.save(touristSpotPicture);
	        return "touristDetail";
	    }

}
