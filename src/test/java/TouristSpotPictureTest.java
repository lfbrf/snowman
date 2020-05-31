import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.Category;
import br.com.snowman.model.Favority;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.TouristSpotPicture;
import br.com.snowman.model.Upvote;
import br.com.snowman.model.User;
import br.com.snowman.repository.FavorityRepository;
import br.com.snowman.repository.TouristSpotPictureRepository;
import br.com.snowman.repository.UpvoteRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;
import br.com.snowman.service.impl.FavorityServiceImpl;
import br.com.snowman.service.impl.TouristSpotPictureImpl;

@RunWith(MockitoJUnitRunner.class)
public class TouristSpotPictureTest {

	@InjectMocks
	TouristSpotPictureImpl touristSpotPictureImpl;
	
	@Mock
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
	FavorityRepository favorityRepository;
	
	@Mock
	TouristSpotPictureRepository touristSpotPictureRepository;
	
	@Mock
	UpvoteRepository upvoteRepository;
	
	Model model = new Model() {
		
		@Override
		public Model mergeAttributes(Map<String, ?> attributes) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object getAttribute(String attributeName) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean containsAttribute(String attributeName) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Map<String, Object> asMap() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Model addAttribute(String attributeName, Object attributeValue) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Model addAttribute(Object attributeValue) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Model addAllAttributes(Map<String, ?> attributes) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Model addAllAttributes(Collection<?> attributeValues) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	

	@Test
	public void whenTryTosaveImageInvalidatedShouldReturnFalse() {
		User u = new User("Test", "Test", "Test", true);
		UserDetails ud = new UserDetails();
		ud.setId("1");
		ud.setEmail("email");
		ud.setName("name");
		byte[] b = "NameContent,Base64".getBytes();
		TouristSpot tourist = new TouristSpot("", true, b, new Category(), new User());
		when(touristSpotService.getOne(1)).thenReturn(tourist);
		when(userService.currentUserInSession()).thenReturn(u);
		boolean test = touristSpotPictureImpl.saveImageIfValidate(new TouristSpotPicture(), 1);
		verify(touristSpotPictureRepository, times(1));
		assertEquals(test, false);
		
	}
	
	@Test
	public void whenDeleteImageWithCorrectParmaReturnRedirectUrl() {
		Map<String, String> requestParams = new HashMap <String,String>() ;
		requestParams.put("idTouristPicture", "1");
		requestParams.put("idImage", "2");
		String result = touristSpotPictureImpl.deleteImageFromTouristSpot(requestParams);
		assertEquals(result, "redirect:?id=1");
	}
	
	@Test
	public void whenDeleteImageWithIncorrectParmaReturnError() {
		Map<String, String> requestParams = new HashMap <String,String>() ;
		requestParams.put("idTouristPicture", "teste");
		requestParams.put("idImage", "2");
		String result = touristSpotPictureImpl.deleteImageFromTouristSpot(requestParams);
		assertEquals(result, "error");
	}
	
	@Test
	public void whenSetModelIsNotAuthReturnModel() {
		
		when(homeService.isAuthenticated()).thenReturn(false);
		touristSpotPictureImpl.setModelIfAuth(model, (long) 1);
	}
	
	@Test
	public void whenSetModelIsAuthAndHasFavorityUpvoteTheReturnModelWithUserAndFavority() {
		//mock(UserDetails.class);
		User user = mock(User.class);
		user.setId((long) 1);
		TouristSpot tourist = mock(TouristSpot.class);
		tourist.setId((long) 1);
		Favority favority =  mock(Favority.class);
		favority.setFavorited(true);
		Upvote upvote = new Upvote();
		upvote.setUp(true);
		
		when(user.getId()).thenReturn( (long) 1);
		when(tourist.getId()).thenReturn( (long) 1);
		when(favority.isFavorited()).thenReturn( true);
		when(homeService.isAuthenticated()).thenReturn(true);
		when(userService.currentUserInSession()).thenReturn(user);
		when(touristSpotService.getOne(Mockito.anyLong())).thenReturn(tourist);
		when(favorityRepository.findFavorityByTouristUser(Mockito.anyLong(), Mockito.anyLong())).thenReturn(favority);
		when(upvoteRepository.findUpvoteByTouristUser(Mockito.anyLong(), Mockito.anyLong())).thenReturn(upvote);
		model = touristSpotPictureImpl.setModelIfAuth(model, (long) 1);
		assertEquals(favorityRepository.findFavorityByTouristUser(Mockito.anyLong(), Mockito.anyLong()), favority);
		assertEquals(upvoteRepository.findUpvoteByTouristUser(Mockito.anyLong(), Mockito.anyLong()), upvote);
	}
	
	@Test
	public void whenSetModelIsAuthAndDontHaveFavorityUpvoteTheReturnModelWithUserAndFavority() {
		//mock(UserDetails.class);
		User user = mock(User.class);
		user.setId((long) 1);
		TouristSpot tourist = mock(TouristSpot.class);
		tourist.setId((long) 1);
		Favority favority =  mock(Favority.class);
		favority.setFavorited(true);
		Upvote upvote = new Upvote();
		upvote.setUp(true);
		
		when(user.getId()).thenReturn( (long) 1);
		when(tourist.getId()).thenReturn( (long) 1);
		when(favority.isFavorited()).thenReturn( true);
		when(homeService.isAuthenticated()).thenReturn(true);
		when(userService.currentUserInSession()).thenReturn(user);
		when(touristSpotService.getOne(Mockito.anyLong())).thenReturn(tourist);
		when(favorityRepository.findFavorityByTouristUser(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);
		when(upvoteRepository.findUpvoteByTouristUser(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);
		model = touristSpotPictureImpl.setModelIfAuth(model, (long) 1);
		assertEquals(favorityRepository.findFavorityByTouristUser(Mockito.anyLong(), Mockito.anyLong()), null);
		assertEquals(upvoteRepository.findUpvoteByTouristUser(Mockito.anyLong(), Mockito.anyLong()), null);
	}
	
	@Test
	public void whenConvertByteToBase64PicturesIsNullThanReturnNullForPic() {
		//mock(UserDetails.class);
		TouristSpot touristSpot = new TouristSpot();
		byte[] picture = "NameContent,Base64".getBytes();
		touristSpot.setMainPicture(picture);
		touristSpot.setNameMainPicture("11");
		List<TouristSpotPicture> touristPictures = new ArrayList<TouristSpotPicture>();
		
		touristSpot.setTouristPictures(touristPictures);
		touristSpotPictureImpl.convertByteToBase64(touristSpot);
	}
	
	

}