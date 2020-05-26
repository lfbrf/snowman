package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.snowman.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query(value = "SELECT * FROM category WHERE name = ?1 LIMIT 1", nativeQuery = true)
	public Category findCategoryByName(String name);
	
	@Query(value = "SELECT * FROM category WHERE category_id = ?1 LIMIT 1", nativeQuery = true)
	public Category findCategoryById(long id);
}	
