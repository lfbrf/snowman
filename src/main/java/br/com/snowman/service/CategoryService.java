package br.com.snowman.service;

import java.util.List;

import br.com.snowman.model.Category;

/**
 * @author luiz
 *
 */
public interface CategoryService {
	 List<Category>  findAll();
	 Category saveCategoryIfNotExists(Category category);
}
