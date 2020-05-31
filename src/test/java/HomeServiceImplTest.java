
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.User;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.impl.HomeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class HomeServiceImplTest {


	@InjectMocks
	HomeServiceImpl homeServiceImpl;


	@Mock
	UserRepository userRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}


	
	@Test
	public void shouldSaveUserIfNotExist() {

		UserDetails uds = mock(UserDetails.class);
		uds.setId("aa");
		uds.setEmail("aa");
		uds.setName("aa");
		when(uds.getId()).thenReturn("aa");
		when(userRepository.findUserByFaceId(Mockito.anyString())).thenReturn(null);

		homeServiceImpl.saveUserIfNotExists(uds);

		verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void shouldNotSaveUserIfExists() {
		User user = new User();
		user.setName("Name");
		UserDetails uds = mock(UserDetails.class);
		uds.setId("aa");
		uds.setEmail("aa");
		uds.setName("aa");
		when(uds.getId()).thenReturn("aa");
		when(userRepository.findUserByFaceId(Mockito.anyString())).thenReturn(user);

		homeServiceImpl.saveUserIfNotExists(uds);

		verify(userRepository, times(1));
	}

	@Test
	public void shouldNotSaveUserIfExistsEmail() {
		User user = new User();
		user.setEmail("Email");
		UserDetails uds = mock(UserDetails.class);
		uds.setId("aa");
		uds.setEmail("aa");
		uds.setName("aa");
		when(uds.getId()).thenReturn("aa");
		when(userRepository.findUserByFaceId(Mockito.anyString())).thenReturn(user);

		homeServiceImpl.saveUserIfNotExists(uds);

		verify(userRepository, times(1));
	}
}
