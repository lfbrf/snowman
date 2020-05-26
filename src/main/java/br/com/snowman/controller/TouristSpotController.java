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

import br.com.snowman.dao.TouristSpotDao;
import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.Category;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.User;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UserRepository;
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
    UserRepository userRepository;
	
	@Autowired
	private HttpServletRequest context;
	

    @GetMapping(value={"", "/"})
    public String  listCategories(Model model) {
    	
    	model = initModel(model);
    	
        return "index";
    }
    
    @GetMapping(value= "/turistico/*")
    public String  listC(Model model, HttpServletRequest request) {
    	System.out.println("ENTRA AQUI ++++++++ ");
    	System.out.println(request.getRequestURL().toString() );
    	System.out.println("---------------");
    	System.out.println(request.getQueryString());
    	model = initModel(model);
    	
        return "touristDetail";
    }
    
    public Model initModel(Model model) {
    	model.addAttribute("auth", isAuthenticated());
    	Set<Category> allCategories = new HashSet<Category>(categoryRepository.findAll());
    	TouristSpotDao touristSpotDao = new TouristSpotDao();
    	
     	Set<TouristSpot> allTouristSpot = new HashSet<TouristSpot>(touristSpotRepository.findAll());

    	model.addAttribute("allTouristPost", allTouristSpot);
    	model.addAttribute("touristSpot", allCategories);
    	model.addAttribute("tourist", touristSpotDao);
    	return model;
    	
    }
    
    public UserDetails returnUdIfAuth() {
    	HttpSession session = context.getSession();
    	Cookie cookie = (Cookie) session.getAttribute("cookieSession");
    	if (cookie!= null) {
    		  return homeService.getUserDetailsFromAccessToken(cookie.getValue());
    	}
    	return null;
    }
    
    @PostMapping(value={"", "/"})
    public  String createCategory( @Valid @ModelAttribute("tourist") TouristSpotDao touristSpot, Model model) {
    	System.out.println("123");
    	UserDetails ud = returnUdIfAuth();
    	if (ud == null )
    		return "index";
    	User u = userRepository.finUserById(ud.getId());
    	if (u == null)
    		return "index";
    	TouristSpot result = new TouristSpot();
    	
    	TouristSpot search = touristSpotRepository.findTouristSpotByName(touristSpot.getName());
    	System.out.println(" 456 456 456 ");
    	if (!isAuthenticated() || (search != null && search.getId() != null))
    		result.setName("Erro");
    	else { 
    		Category c = categoryRepository.findCategoryById(touristSpot.getIdCategory());
    		result.setName(touristSpot.getName());
    		System.out.println("ANTES DO ERRO ;;;; ;;;; ");
    	    result.setMainPicture(touristSpot.getPicture());
    	    System.out.println("DEPOIS DO ERRRO");
    		result.setStatus(true);
    		result.setUser(u);
    		result.setCategory(c);
    		result.setLatLocalization(touristSpot.getLatLocalization());
    		result.setLongLocalization(touristSpot.getLongLocalization());
    		System.out.println(" 000000 ");
    		result =  touristSpotRepository.save(result);
    		System.out.println("#######################");
    	}

		
		
    	model = initModel(model);
		
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
