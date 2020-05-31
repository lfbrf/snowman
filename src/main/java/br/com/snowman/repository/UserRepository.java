package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.User;

/**
 * @author luiz
 * Repositório do usuário
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// Busca usuário pelo id referente ao token que gerou
	@Query(value = "SELECT * FROM user WHERE face_id = ?1 ", nativeQuery = true)
	public User findUserByFaceId(String string);
	

}	
