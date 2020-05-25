package br.com.snowman.controller;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.snowm.dao.TouristSpotDao;
import br.com.snowman.model.Category;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.User;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.service.HomeService;

@Controller
@RequestMapping("/")
public class TouristSpotController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
    TouristSpotRepository touristSpotRepository;
	
	@Autowired
	private HttpServletRequest context;
	

    @GetMapping(value={"", "/"})
    public String  listCategories(Model model) {
    	model.addAttribute("auth", isAuthenticated());
    	TouristSpot touristSpot = new TouristSpot();
    	Set<Category> allCategories = new HashSet<Category>(categoryRepository.findAll());
    	touristSpot.setCategories( allCategories);
    	TouristSpotDao touristSpotDao = new TouristSpotDao();
    	// Pego o usuário atual
    	model.addAttribute("touristSpot", touristSpot);
    	model.addAttribute("tourist", touristSpotDao);
        return "index";
    }
    
    @PostMapping(value={"", "/"})
    public @ResponseBody  String createCategory( @Valid @ModelAttribute("tourist") TouristSpotDao touristSpot, Model model) {

    	System.out.println("It works");
    	System.out.println("NAME DA PIC " + touristSpot.getName());
    	System.out.println("PASSA");
    	System.out.println("PIC BYTES" + touristSpot.getPicture());
    	TouristSpot result = new TouristSpot();
    	Set categories = new HashSet();
    	TouristSpot search = touristSpotRepository.findTouristSpotByName(touristSpot.getName());
    	if (!isAuthenticated() || (search != null && search.getId() != null))
    		result.setName("Erro");
    	else {
    		Category c = new Category(touristSpot.getIdCategory());
    		categories.add(c);
    		result.setName(touristSpot.getName());
    		result.setMainPicture(touristSpot.getPicture());
    		result.setStatus(true);
    		result.setUser(new User(touristSpot.getIdUser()));
    		result.setCategories(categories);
    		result =  touristSpotRepository.save(result);
    	}
		System.out.println("CAT " + touristSpot.getName());
		System.out.println("CAT F" + touristSpot.getIdCategory());
		
    	return "index";
    }
    
    public boolean isAuthenticated() {
    	HttpSession session = context.getSession();
    	Cookie cookie = (Cookie) session.getAttribute("cookieSession");
    	if (cookie!= null) {
    		  return homeService.userIsAuthenticated(cookie.getValue());
    	}
    	return false;
    }

}
