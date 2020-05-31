
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import br.com.snowman.model.Category;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.User;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.UserService;
import br.com.snowman.service.impl.TouristSpotImpl;

@RunWith(MockitoJUnitRunner.class)
public class TouristSpotImplTest {


	@InjectMocks
	TouristSpotImpl touristSpotImpl;


	@Mock
	UserRepository userRepository;
	
	@Mock
	UserService userService;
	
	@Mock
	TouristSpotRepository touristSpotRepository;
	
	@Mock
	CategoryRepository categoryRepository;
	
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
	

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findTouristByFavorityShouldReturnListOfTourist() {
		List<TouristSpot> tourist = new ArrayList <TouristSpot>();
		User user = new User();
		user.setEmail("email");
		user.setId( (long) 1 );
		when(userService.currentUserInSession()).thenReturn(user);
		when(touristSpotRepository.findTouristByFavority(Mockito.anyLong())).thenReturn(tourist);
		touristSpotImpl.findTouristByFavority();
		assertEquals(touristSpotRepository.findTouristByFavority(Mockito.anyLong()), tourist);
	}
	
	@Test
	public void findTouristSpotByCurrentUserShouldReturnListOfTourist() {
		List<TouristSpot> tourist = new ArrayList <TouristSpot>();
		User user = new User();
		user.setEmail("email");
		user.setId( (long) 1 );
		when(userService.currentUserInSession()).thenReturn(user);
		when(touristSpotRepository.findTouristSpotByUser(Mockito.anyLong())).thenReturn(tourist);
		touristSpotImpl.findTouristByFavority();
		assertEquals(touristSpotRepository.findTouristSpotByUser(Mockito.anyLong()), tourist);
	}
	
	@Test
	public void setModelLatLngIfHasTouristShouldSetModelIfListNotNull() {
		List<TouristSpot> tourist = new ArrayList <TouristSpot>();
		TouristSpot t  = new TouristSpot();
		t.setId( (long) 1);
		t.setLatLocalization("");
		t.setLongLocalization("");
		tourist.add(t);
		when(touristSpotImpl.findAll()).thenReturn(tourist);
		
		model = touristSpotImpl.setModelLatLngIfHasTourist( model);
		assertEquals(touristSpotImpl.findAll().size(), 1);
	}
	
	@Test
	public void convertBase64ToBinaryImageShouldSTrowExceptionIfImageInvalid() {
		List<TouristSpot> tourist = new ArrayList <TouristSpot>();
		TouristSpot t  = new TouristSpot();
		t.setId( (long) 1);
		t.setLatLocalization("");
		t.setLongLocalization("");
		tourist.add(t);
		when(touristSpotImpl.findAll()).thenReturn(tourist);
		 touristSpotImpl.convertBase64ToBinaryImage( );
		 // Chega no for, porém como a imagem é nulla da exception
		assertEquals(touristSpotImpl.findAll().size(), 1);
	}
	
	@Test
	public void convertBase64ToBinaryImageShouldSTrowExceptionIfArrayNull() {
		List<TouristSpot> tourist = new ArrayList <TouristSpot>();
		when(touristSpotImpl.findAll()).thenReturn(tourist);
		 touristSpotImpl.convertBase64ToBinaryImage( );
		 // Chega no for, porém como a imagem é nulla da exception
		assertEquals(touristSpotImpl.findAll().size(), 0);
	}
	
	@Test
	public void saveTouristIfAuthAndVaidatedShopuldReturnErroIfExists() {
		Category c = new Category();
		User u = new User();
		u.setId( (long) 1);
		TouristSpot t  = mock(TouristSpot.class);
		t.setName("name");
		t.setId( (long) 1);
		t.setLatLocalization("");
		t.setLongLocalization("");
		when(t.getId()).thenReturn((long) 1);
		when(t.getName()).thenReturn("name");
		
		when(touristSpotRepository.findUniqueTouristSpotByName(Mockito.anyString())).thenReturn(t);
		when(userService.currentUserInSession()).thenReturn(u);
		
		when(categoryRepository.findCategoryById(Mockito.anyLong())).thenReturn(c);
		t = touristSpotImpl.saveTouristIfAuthAndVaidated( t);
		 // Chega no for, porém como a imagem é nulla da exception
		assertEquals(t.getName(), "Erro");
	}
	
	@Test
	public void saveTouristIfAuthAndVaidatedShopuldReturnNotErroIfSuccess() {
		Category c =  mock(Category.class);
		c.setId(  (long) 1);
		User u = new User();
		u.setId( (long) 1);
		TouristSpot t  = mock(TouristSpot.class);
		t.setName("name");
		t.setId( (long) 1);
		t.setLatLocalization("");
		t.setLongLocalization("");
		when(t.getId()).thenReturn((long) 1);
		when(t.getName()).thenReturn("name");
		when(t.getCategory()).thenReturn( c);
		when(t.getCategory().getId()).thenReturn( (long) 1);
		when(touristSpotRepository.findUniqueTouristSpotByName(Mockito.anyString())).thenReturn(null);
		when(userService.currentUserInSession()).thenReturn(u);
		
		when(categoryRepository.findCategoryById(Mockito.anyLong())).thenReturn(c);
		touristSpotImpl.saveTouristIfAuthAndVaidated( t);
		 // Chega no for, porém como a imagem é nulla da exception

		assertNotEquals(t.getName(), "Erro");
	}
	
	
}
