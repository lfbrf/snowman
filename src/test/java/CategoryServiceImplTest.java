
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import br.com.snowman.model.Category;
import br.com.snowman.model.Favority;
import br.com.snowman.model.TouristSpot;
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
import br.com.snowman.service.impl.FavorityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;
	

	
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
	public void saveCategoryIfNotExistsShowuldSaveIfNotExists() {
		Category category = mock(Category.class);
		
		Category search = mock(Category.class);
		Category result = mock(Category.class);
		
		when(search.getId()).thenReturn((long) 1);
		when(category.getId()).thenReturn((long) 1);
		when(search.getName()).thenReturn("Nome categoria");
		when(category.getName()).thenReturn("Nome categoria");
		when(homeService.isAuthenticated()).thenReturn(false);
		when(categoryRepository.findCategoryByName(Mockito.anyString())).thenReturn(search);
		categoryServiceImpl.saveCategoryIfNotExists(category);
		assertEquals(categoryServiceImpl.saveCategoryIfNotExists(category).getName(), "Erro");
		
		
	}

}