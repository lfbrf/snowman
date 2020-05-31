package br.com.snowman.controller;


import java.util.HashSet;
import java.util.Set;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.snowman.model.Category;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.service.CategoryService;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;

/**
 * @author luiz
 * Controller dos pontos turísticos
 */
@Controller
@RequestMapping("/")
public class TouristSpotController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
    TouristSpotService touristSpotService;
	
	@Autowired
    CategoryService categoryService;
	
	@Autowired
    UserService userService;
	
	// Listar todos pontos turísticos 
    @GetMapping(value={"", "/"})
    public String  listCategories(Model model, HttpSession session) {
    	model = initModel(model);
        return "index";
    }
    
   
    // Busca favoritos do usuário autenticado
    @GetMapping("/buscafavoritos")
    public   String  searchFavority(Model model, HttpSession session) {
    	if (!homeService.isAuthenticated())
    		return "error";
    	
    	model = initModel(model);
    	model.addAttribute("searchName", touristSpotService.findTouristByFavority());
    	model.addAttribute("searchFilter", true);
    	
    	return "index";
    }
    
    // Busca ponto turístico por nome se usuário estiver autenticado
    @GetMapping("/buscanome")
    public   String  searchByName(Model model, HttpSession session, @RequestParam("nome") String name) {
    	if (!homeService.isAuthenticated())
    		return "error";
    	
    	model = initModel(model);
    	model.addAttribute("searchName", touristSpotService.findTouristSpotByName(name));
    	model.addAttribute("searchFilter", true);
    	
    	return "index";    	
    }
    
    // Busca ponto turístico que usuário autenticado criou
    @GetMapping("/buscausuario")
    public   String  searchByUser(Model model, HttpSession session) {
    	if (!homeService.isAuthenticated()) 
			return "error";
    	
		model = initModel(model);
		model.addAttribute("searchName", touristSpotService.findTouristSpotByCurrentUser());
		model.addAttribute("searchFilter", true);
		
		return "index";
    }
    
    // Inicia os atributos do modelo
    public Model initModel(Model model) {
    	TouristSpot tourist = new TouristSpot();
    	Set<Category> allCategories = new HashSet<Category>(categoryService.findAll());
    	
     	model = touristSpotService.setModelLatLngIfHasTourist(model);
     	model.addAttribute("auth", homeService.isAuthenticated());
    	model.addAttribute("allTouristPost", touristSpotService.convertBase64ToBinaryImage());
    	model.addAttribute("touristSpot", allCategories);
    	model.addAttribute("tourist", tourist);
    	model.addAttribute("savedTourist", "");
    	model.addAttribute("touristById", "");
    	model.addAttribute("searchName", "");
    	model.addAttribute("searchFilter", false);
    	
    	return model;
 
    }
    
    // Cadastra novo ponto turístico
    @PostMapping(value={"", "/"})
    public  String createCategory( @ModelAttribute("tourist") TouristSpot touristSpot, Model model, HttpSession session, HttpServletResponse response) {
    	if (!homeService.isAuthenticated())
    		return "error";
  
    	TouristSpot result = touristSpotService.saveTouristIfAuthAndVaidated(touristSpot);
		model.addAttribute("savedTourist", result);
    	
		
    	return "redirect:/";
    } 
    

}
