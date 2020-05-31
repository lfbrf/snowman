package br.com.snowman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.Favority;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.User;
import br.com.snowman.repository.FavorityRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.FavorityService;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;

/**
 * @author luiz
 * Implementação da interface FavorityService 
 */
@Service
public class FavorityServiceImpl implements FavorityService {
	
	@Autowired
	TouristSpotService touristSpotService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TouristSpotRepository touristSpotRepository;
	
	@Autowired
	FavorityRepository favorityRepository;

	@Autowired
	HomeService homeService;
	
	@Autowired
	UserRepository userRepository;
	// Verifica se o ponto turístico já foi curtido anteriormente pelo usuário
	@Override
	public void favorityUpdateOrSaveTouristSpot(long id) {
		TouristSpot tourist = touristSpotRepository.getOne(id);
		User user = userService.currentUserInSession();
		Favority favority = new Favority(tourist, user, true);
		Favority favoritySearch = favorityRepository.findFavorityByTouristUser(tourist.getId(), user.getId());
		// Se sim, atualiza, caso contrário insere o registro no banco.
		if (favoritySearch != null)
			favorityRepository.updateFavority(true, tourist.getId(), user.getId());
		else
			favorityRepository.save(favority);
	}

	// Desfavorita um ponto turístico, como o mesmo já foi favoritado apenas atualizo
	@Override
	public void desfavorityTouristSpot(long id) {
		TouristSpot tourist = touristSpotRepository.getOne(id);
		UserDetails ud = homeService.getCurrentUserInSession();
		User user = userRepository.findUserByFaceId(ud.getId());
		favorityRepository.updateFavority(false, tourist.getId(), user.getId());
		
	}

}
