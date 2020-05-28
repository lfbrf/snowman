package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.com.snowman.model.Upvote;

@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
	
}	