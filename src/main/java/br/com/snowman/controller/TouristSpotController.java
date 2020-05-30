package br.com.snowman.controller;



import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import br.com.snowman.adapter.TouristSpotAdapter;
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
    
   
    
    @GetMapping("/buscafavoritos")
    public   String  searchFavority(Model model, HttpSession session) {
    	if (!homeService.isAuthenticated())
    		return "error";
    	model = initModel(model, session);
    	UserDetails ud = homeService.getCurrentUserInSession();
		User user = userRepository.findUserByFaceId(ud.getId());
    	List<TouristSpot> tourist = touristSpotRepository.findTouristByFavority(user.getId());
    	model.addAttribute("searchName", tourist);
    	
    	model.addAttribute("searchFilter", true);
    	System.out.println("CHAMA AQUI S ++++++++++ +++ buscafavoritos "); 
    	
    	return "index";
    	
    }
    
    @GetMapping("/buscanome")
    public   String  searchByName(Model model, HttpSession session, @RequestParam("nome") String name) {
    	model = initModel(model, session);
    	model.addAttribute("searchName", touristSpotRepository.findTouristSpotByName(name));
    	model.addAttribute("searchFilter", true);
    	System.out.println("CHAMA AQUI S ++++++++++ +++"); 
    	if (homeService.isAuthenticated())
    		return "index";
    	return "error";
    }
    
    @GetMapping("/buscausuario")
    public   String  searchByUser(Model model, HttpSession session) {
    	if (!homeService.isAuthenticated()) {
    		return "error";
    	}
    	else {
    		model = initModel(model, session);
        	UserDetails ud = homeService.getCurrentUserInSession();
    		User user = userRepository.findUserByFaceId(ud.getId());
        	model.addAttribute("searchName", touristSpotRepository.findTouristSpotByUser(user.getId()));
        	model.addAttribute("searchFilter", true);
        	System.out.println("CHAMA AQUI S ++++++++++ +++"); 
        	
        	return "index";
    	}
    	
    }
    
    public Model initModel(Model model, HttpSession session ) {
    	model.addAttribute("auth", homeService.isAuthenticated());
    	
    	TouristSpotAdapter touristSpotDao = new TouristSpotAdapter();
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
    public  String createCategory( @ModelAttribute("tourist") TouristSpotAdapter touristSpot, Model model, HttpSession session, HttpServletResponse response) {
    	System.out.println("123");
    	UserDetails ud = returnUdIfAuth(session);
    	if (ud == null )
    		return "index";
    	User u = userRepository.findUserByFaceId(ud.getId());
    	if (u == null)
    		return "index";
    	TouristSpot result = new TouristSpot();
    	 
    	TouristSpot search = touristSpotRepository.findUniqueTouristSpotByName(touristSpot.getName());
    	System.out.println(" 456 456 456 ");
    	if (!homeService.isAuthenticated())
    		result.setName("Not auth");
    	else if ((search != null && search.getId() != null))
    		result.setName("Erro");
    	else { 
    		Category c = categoryRepository.findCategoryById(touristSpot.getIdCategory());
    		System.out.println(touristSpot.getPicture());
    		
    		String separator =",";
    		int sepPos = touristSpot.getPicture().indexOf(separator);
    		String[] str = touristSpot.getPicture().split(separator, 2);
    		byte[] decodedByte = Base64.getDecoder().decode(str[1]);
    		
    		 
    		System.out.println("UM POCO ANTES");
    		System.out.println("TIPO ENCONTRADO " + str[0]);
    		try {
    			System.out.println("VEM AQUI ######  ");
				Blob b = new SerialBlob(decodedByte);
			     
				String encoded = Base64.getEncoder().encodeToString(b.getBytes(1l, (int)b.length()));
				System.out.println("BASE  64 DEPOIS:: ");
				System.out.println(encoded);
			} catch (SerialException e) {
				System.out.println("FIRST EXC");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SECOND EXC");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

    	//model = initModel(model, session);
		model.addAttribute("savedTourist", result);
    	
		
    	return "redirect:/";
    } 
    
    /*
    public boolean isAuthenticated(HttpSession session) {
    	session = context.getSession();
    	Cookie cookie = (Cookie) session.getAttribute("cookieSession");
    	if (cookie!= null) {
    		  return homeService.userIsAuthenticated(cookie.getValue());
    	}
    	return false;
    }*/

}
