package br.com.snowman.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.snowman.model.Category;
import br.com.snowman.service.CategoryService;
import br.com.snowman.service.HomeService;

/**
 * @author luiz
 * Controller de categoria
 */
@Controller
@RequestMapping("/categoria")
public class CategoryController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
	CategoryService categoryService;

	// Lista todas categorias
    @GetMapping(value={"", "/"})
    public String  listCategories(Model model) {
    	model = initCategory(model);
        return "category";
    } 
    
    // Inicia os models
    public Model initCategory(Model model) {
    	model.addAttribute("allCategories", categoryService.findAll() + "");
    	model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("auth", homeService.isAuthenticated());
        model.addAttribute("category", new Category());
        return model;
    }
    
    // Cadastra nova categoria
    @PostMapping(value={"", "/"})
    public  String createCategory( @Valid @ModelAttribute("category") Category category, Model model) {
    	categoryService.saveCategoryIfNotExists(category);
    	model.addAttribute("result", category);
    	return "redirect:/categoria";
    }
  
 
}
