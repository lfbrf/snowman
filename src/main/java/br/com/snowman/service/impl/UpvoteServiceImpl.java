package br.com.snowman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.Upvote;
import br.com.snowman.model.User;
import br.com.snowman.repository.UpvoteRepository;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UpvoteService;
import br.com.snowman.service.UserService;

/**
 * @author luiz
 * Classe que implementa a interface UpvoteService
 */
@Service
public class UpvoteServiceImpl implements UpvoteService {
	@Autowired
    UserService userService;
	
	@Autowired
	UpvoteRepository upvoteRepository;
	
	@Autowired
	TouristSpotService touristSpotService;
	
	// Método responsável por realizar a ação de votar em um ponto turístico
	@Override
	public void saveUserVote(long id) {
		
		User user = userService.currentUserInSession();
		TouristSpot tourist = touristSpotService.getOne(id);
		Upvote upvote = new Upvote(tourist, user, true);
		Upvote upvoteSearch = upvoteRepository.findUpvoteByTouristUser(tourist.getId(), user.getId());
		// Salva somente se ainda não foi votado
		if (upvoteSearch != null)
			return;
		else
			upvoteRepository.save(upvote);
	}

	@Override
	public int getCurrenteUpvote(long id) {
		return upvoteRepository.getCurrenteUpvote(id);
	}

}
