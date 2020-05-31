

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.snowman.model.Category;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.Upvote;
import br.com.snowman.model.User;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.repository.FavorityRepository;
import br.com.snowman.repository.TouristSpotPictureRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UpvoteRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;
import br.com.snowman.service.impl.CategoryServiceImpl;
import br.com.snowman.service.impl.UpvoteServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UpvoteServiceImplTest {

	@InjectMocks
	UpvoteServiceImpl upvoteServiceImpl;
	

	
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
	
	@Mock
	CategoryRepository categoryRepository;
	

	
	@Test
	public void saveUserVoteItMustSaveUserWhenIsNew() {
		User user = mock(User.class);
		TouristSpot tourist = mock(TouristSpot.class);
		when(tourist.getId()).thenReturn( (long) 1 );
		when(user.getId()).thenReturn( (long) 1 );
		when(userService.currentUserInSession()).thenReturn(user);
		when(touristSpotService.getOne(Mockito.anyLong())).thenReturn(tourist);
		when(upvoteRepository.findUpvoteByTouristUser(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);
		upvoteServiceImpl.saveUserVote( (long) 1 );
		
	}

}