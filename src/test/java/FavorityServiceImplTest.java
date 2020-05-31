
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.Favority;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.User;
import br.com.snowman.repository.FavorityRepository;
import br.com.snowman.repository.TouristSpotPictureRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UpvoteRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;
import br.com.snowman.service.impl.FavorityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FavorityServiceImplTest {

	@InjectMocks
	FavorityServiceImpl favorityServiceImpl;
	

	
	@Mock
	UserRepository userRepository;
	
	@Mock
	UserService userService;
	
	@Mock
	HomeService homeService;
	
	@Mock
	TouristSpotService touristSpotService;
	
	@Mock
	TouristSpotRepository touristSpotRepository;
	
	@Mock
	FavorityRepository favorityRepository;
	
	@Mock
	TouristSpotPictureRepository touristSpotPictureRepository;
	
	@Mock
	UpvoteRepository upvoteRepository;

	

	@Test
	public void whenFavorityUpdateOrSaveTouristSpotShouldAndNameIsUniqueHasToSave() {
		TouristSpot tourist = mock(TouristSpot.class);
		Favority favoritySearch = new Favority();
		Favority favorityPersist = new Favority();
		favorityPersist.setId( (long)1 );
		favoritySearch.setId( (long) 1 );
		User user = mock(User.class);
		when(tourist.getId()).thenReturn(  (long) 1);
		when(user.getId()).thenReturn(  (long) 1);
		when(touristSpotRepository.getOne(Mockito.anyLong())).thenReturn(tourist);
		when(userService.currentUserInSession()).thenReturn(user);
		when(favorityRepository.findFavorityByTouristUser(Mockito.anyLong(), Mockito.anyLong())).thenReturn(favoritySearch);
		when(favorityRepository.save(Mockito.any(Favority.class))).thenReturn(favorityPersist);
		favorityServiceImpl.favorityUpdateOrSaveTouristSpot( (long) 1);
		assertNotNull(favorityRepository.findFavorityByTouristUser(Mockito.anyLong(), Mockito.anyLong()));
	}
	
	@Test
	public void whenDesfavorityTouristSpotHasToUpdate() {
		TouristSpot tourist = mock(TouristSpot.class);
		User user = mock(User.class);
		UserDetails userDetails = mock(UserDetails.class);
		when(userDetails.getId()).thenReturn("123");
		when(user.getId()).thenReturn((long) 1);
		when(tourist.getId()).thenReturn((long)1);
		when(touristSpotRepository.getOne(Mockito.anyLong())).thenReturn(tourist);
		when(userRepository.findUserByFaceId(Mockito.anyString())).thenReturn(user);
		when(homeService.getCurrentUserInSession()).thenReturn(userDetails);
		favorityServiceImpl.desfavorityTouristSpot((long)1);
	}

}