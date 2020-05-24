package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.snowman.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}	
