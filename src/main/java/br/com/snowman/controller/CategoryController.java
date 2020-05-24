package br.com.snowman.controller;

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

import br.com.snowman.model.Category;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.service.HomeService;

@Controller
@RequestMapping("/categoria")
public class CategoryController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
	private HttpServletRequest context;
	
	@GetMapping(value={"teste/", "/teste"})
    public String  teste(Model model) {
        return "teste";
    }
    
    
    @GetMapping(value={"", "/"})
    public String  listCategories(Model model) {
    	model = initCategory(model);
        return "category";
    }
    
    public Model initCategory(Model model) {
    	model.addAttribute("allCategories", categoryRepository.findAll() + "");
    	model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("auth", isAuthenticated());
        model.addAttribute("category", new Category());
        return model;
    }
    
    
    @PostMapping(value={"", "/"})
    public  String createCategory( @Valid @ModelAttribute("category") Category category, Model model) {
    	Category result = new Category();
    	Category search = new Category();
    	
    	search = categoryRepository.findCategoryByName(category.getName());
    	if (!isAuthenticated() || (search != null && search.getId() != null))
    		result =  new Category ("Erro", false);
    	else
    		result =  categoryRepository.save(category);
    	model = initCategory(model);
    	model.addAttribute("result", result);
    	return "category";
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
