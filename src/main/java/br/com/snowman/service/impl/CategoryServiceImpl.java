package br.com.snowman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.snowman.model.Category;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.service.CategoryService;
import br.com.snowman.service.HomeService;

/**
 * @author luiz
 * Implementação da interface CategoryService
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
    HomeService homeService;
	
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	// Verifica se o usuário está autenticado e se o nome da categoria não existe ainda no banco para então salvar
	@Override
	public Category saveCategoryIfNotExists(Category category) {
		Category result = new Category();
		Category search = new Category();
		search = categoryRepository.findCategoryByName(category.getName());
    	if (!homeService.isAuthenticated() || (search != null && search.getId() != null))
    		result =  new Category ("Erro", false);
    	else
    		result =  categoryRepository.save(category);
		return result;
	}

}
