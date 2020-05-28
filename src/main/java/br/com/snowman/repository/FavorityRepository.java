package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.Favority;

@Repository
public interface FavorityRepository extends JpaRepository<Favority, Long> {
	
}	
