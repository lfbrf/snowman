
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.Favority;
import br.com.snowman.model.TouristSpotPicture;
import br.com.snowman.model.User;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.impl.HomeServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class HomeServiceImplTest {
	@InjectMocks
	HomeServiceImpl homeServiceImpl;
	
	@Mock
	UserRepository userRepository;

	@Test
	public void shouldSaveUserIfNotExist() {
		User user = new User();

		UserDetails ud = new UserDetails();
		ud.setId("1");
		ud.setEmail("email");
		ud.setName("name");
		when(userRepository.save(user))
        .thenAnswer(i -> i.getArguments()[0]);
		User u = userRepository.findUserByFaceId("1");
		
		when(userRepository.findUserByFaceId(Mockito.anyString())).thenReturn(u);
		
		homeServiceImpl.saveUserIfNotExists(ud);
		
	}
}
