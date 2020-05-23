package br.com.snowman.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
    
    
    @GetMapping(value={"", "/"})
    public String  listCategories(Model model) {
    	System.out.println("Entra aqui");
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("auth", isAuthenticated());
        System.out.println("Depois");
        return "category";
    }
    
    
    @PostMapping(value={"", "/"})
    public @ResponseBody  Category createCategory(@Valid @RequestBody Category category) {
    	System.out.println("Chama aqui !");
    	if (isAuthenticated())
    		return categoryRepository.save(category);
    	else
    		return new Category ("É ncessário estar logado para criar nova categoria", false);
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
