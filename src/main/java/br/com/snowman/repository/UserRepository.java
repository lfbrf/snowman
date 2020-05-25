package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.Category;
import br.com.snowman.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT * FROM user WHERE face_id = ?1 ", nativeQuery = true)
	public User finUserById(String string);
	

}	
