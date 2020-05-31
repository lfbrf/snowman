
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.User;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;
import br.com.snowman.service.impl.FavorityServiceImpl;
import br.com.snowman.service.impl.TouristSpotPictureImpl;
import br.com.snowman.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TouristSpotPictureTest {
	
	@InjectMocks
	TouristSpotPictureImpl touristSpotPictureImpl;
	
	@Mock
	FavorityServiceImpl favorityServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	HomeService homeService;
	@Test
	public void sum_with3numbers() {
		System.out.println("Test1");
		assertEquals(6, 3 + 3);
	}


	@Test
	public void whenUserInsessionAndHasUserByFaceIdThenMustReturnUser() {
		User u = new User("Test", "Test", "Test", true);
		UserDetails ud = new UserDetails();
		ud.setId("1");
		ud.setEmail("email");
		ud.setName("name");
		when(homeService.getCurrentUserInSession()).thenReturn(ud);
		when(userRepository.findUserByFaceId("1")).thenReturn(u);
		// assertEquals(u, userServiceImpl.currentUserInSession());
		
		
	}
	

}