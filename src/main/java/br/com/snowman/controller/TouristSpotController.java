package br.com.snowman.controller;



import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String  listCategories(Model model, HttpSession session) {

    	model = initModel(model, session);
    	System.out.println("CHAMA AQUI S ++++++++++ +++");
        return "index";
    }
    
    @GetMapping("/buscanome")
    public   String  searchByName(Model model, HttpSession session, @RequestParam("nome") String name) {
    	model = initModel(model, session);
    	model.addAttribute("searchName", touristSpotRepository.findTouristSpotByName(name));
    	model.addAttribute("searchFilter", true);
    	System.out.println("CHAMA AQUI S ++++++++++ +++"); 
    	if (isAuthenticated(session))
    		return "index";
    	return "error";
    }
    
    @GetMapping("/turistico")
    public  String  listById(Model model, HttpServletRequest request, HttpSession session, @RequestParam("id") long id) {
    	model.addAttribute("auth", isAuthenticated(session));
    	TouristSpot ts = touristSpotRepository.getOne(id);
    	model = initModel(model, session);
    	model.addAttribute("touristById", ts);
        return "touristDetail";
    }
    
    public Model initModel(Model model, HttpSession session ) {
    	model.addAttribute("auth", isAuthenticated(session));
    	
    	TouristSpotDao touristSpotDao = new TouristSpotDao();
    	Set<Category> allCategories = new HashSet<Category>(categoryRepository.findAll());
     	Set<TouristSpot> allTouristSpot = new HashSet<TouristSpot>(touristSpotRepository.findAll());
     	List<TouristSpot> tourists = touristSpotRepository.findAll();
     	
     	if (tourists != null && tourists.size() > 0) {
     		List<String> arrayLatLocalization = tourists.stream().map(TouristSpot::getLatLocalization).collect(Collectors.toList());
         	List<String> arrayLongLocalization = tourists.stream().map(TouristSpot::getLongLocalization).collect(Collectors.toList());
        	List<Long> arrayIds = tourists.stream().map(TouristSpot::getId).collect(Collectors.toList());
   
        	model.addAttribute("arrayLatLocalization", arrayLatLocalization);
        	model.addAttribute("arrayLongLocalization", arrayLongLocalization);
        	model.addAttribute("arrayIds", arrayIds);
     		
     	}
     	else {
     		model.addAttribute("arrayLatLocalization", "default");
        	model.addAttribute("arrayLongLocalization", "default");
        	model.addAttribute("arrayIds", "default");
        	System.out.println("AQUI ###1");
     	}
     	
     	
    	model.addAttribute("allTouristPost", allTouristSpot);
    	model.addAttribute("touristSpot", allCategories);
    	model.addAttribute("tourist", touristSpotDao);
    	model.addAttribute("savedTourist", "");
    	model.addAttribute("touristById", "");
    	model.addAttribute("searchName", "");
    	model.addAttribute("searchFilter", false);
    	return model;
    	
    }
    
    public UserDetails returnUdIfAuth(HttpSession session) {
    	session = context.getSession();
    	Cookie cookie = (Cookie) session.getAttribute("cookieSession");
    	if (cookie!= null) {
    		  return homeService.getUserDetailsFromAccessToken(cookie.getValue());
    	}
    	return null;
    }
    
   
    
    //estePost
    
    
    @PostMapping(value={"", "/"})
    public  String createCategory( @ModelAttribute("tourist") TouristSpotDao touristSpot, Model model, HttpSession session) {
    	System.out.println("123");
    	UserDetails ud = returnUdIfAuth(session);
    	if (ud == null )
    		return "index";
    	User u = userRepository.finUserById(ud.getId());
    	if (u == null)
    		return "index";
    	TouristSpot result = new TouristSpot();
    	 
    	TouristSpot search = touristSpotRepository.findUniqueTouristSpotByName(touristSpot.getName());
    	System.out.println(" 456 456 456 ");
    	if (!isAuthenticated(session))
    		result.setName("Not auth");
    	else if ((search != null && search.getId() != null))
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

    	model = initModel(model, session);
		model.addAttribute("savedTourist", result);
    	
		
    	return "index";
    }
    
    public boolean isAuthenticated(HttpSession session) {
    	session = context.getSession();
    	Cookie cookie = (Cookie) session.getAttribute("cookieSession");
    	if (cookie!= null) {
    		  return homeService.userIsAuthenticated(cookie.getValue());
    	}
    	return false;
    }

}
