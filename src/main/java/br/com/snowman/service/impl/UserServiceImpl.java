package br.com.snowman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.User;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.UserService;

/**
 * @author luiz
 * implementação da interface UserService
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
    TouristSpotRepository touristSpotRepository;
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
    HomeService homeService;
	
	// Retorna o usuário corrente na sessão
	@Override
	public User currentUserInSession() {
		UserDetails ud = homeService.getCurrentUserInSession();
		User user = userRepository.findUserByFaceId(ud.getId());
		return user;
	}

}
