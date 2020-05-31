package br.com.snowman.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import br.com.snowman.model.Category;
import br.com.snowman.model.TouristSpot;
import br.com.snowman.model.User;
import br.com.snowman.repository.CategoryRepository;
import br.com.snowman.repository.TouristSpotRepository;
import br.com.snowman.service.TouristSpotService;
import br.com.snowman.service.UserService;
import br.com.snowman.util.MainUtil;

/**
 * @author luiz
 * Implementação da interface TouristSpotService 
 */
@Service
public class TouristSpotImpl implements TouristSpotService {
	
	@Autowired
    CategoryRepository categoryRepository;
	
	@Autowired
    TouristSpotRepository touristSpotRepository;

	@Autowired
    UserService userService;
	
	// Busca todos os pontos turísticos favoritos do usuário que está logado
	public List<TouristSpot> findTouristByFavority() {
		User user = userService.currentUserInSession();
		List<TouristSpot> tourist = touristSpotRepository.findTouristByFavority(user.getId());
		return tourist;
	}

	// Busca pontos turísticos por nome
	@Override
	public List<TouristSpot> findTouristSpotByName(String name) {
		return touristSpotRepository.findTouristSpotByName(name);
	}

	// Busca pontos turísticos do usuário autneticado
	@Override
	public List<TouristSpot> findTouristSpotByCurrentUser() {
		User user = userService.currentUserInSession();
		List<TouristSpot> tourist = touristSpotRepository.findTouristSpotByUser(user.getId());
		return tourist;
	}

	@Override
	public List<TouristSpot> findAll() {
		return touristSpotRepository.findAll();
	}

	// Método responsável por definir os atributos de latitude, longitude e identificador dos mesmos
	// Esse atributos são usados para busca por proximidade, em caso de dúvidas ver script embutido no index.html
	@Override
	public Model setModelLatLngIfHasTourist(Model model) {
		List<TouristSpot> tourists = findAll();
		model.addAttribute("arrayLatLocalization", "default");
     	model.addAttribute("arrayLongLocalization", "default");
     	model.addAttribute("arrayIds", "default");
     	if (tourists != null && tourists.size() > 0) {
     		try {
     			// usando strem().map.collect para extrair um array de um atributo da lista de tipo TouristSpot
	     		List<String> arrayLatLocalization = tourists.stream().map(TouristSpot::getLatLocalization).collect(Collectors.toList());
	         	List<String> arrayLongLocalization = tourists.stream().map(TouristSpot::getLongLocalization).collect(Collectors.toList());
	        	List<Long> arrayIds = tourists.stream().map(TouristSpot::getId).collect(Collectors.toList());
	        	model.addAttribute("arrayLatLocalization", arrayLatLocalization);
	        	model.addAttribute("arrayLongLocalization", arrayLongLocalization);
	        	model.addAttribute("arrayIds", arrayIds);
     		}catch(Exception e) {
     			System.err.println("Erro ao extrair list do set setModelLatLngIfHasTourist: " + e.getMessage());
     		}
        	
     		
     	}
 
     	return model;
	}

	// Converte base64 para binário 
	// A conversão ocorre no set que é passado para verificação
	@Override
	public Set<TouristSpot> convertBase64ToBinaryImage() {
		Set<TouristSpot> allTouristSpot = new HashSet<TouristSpot>(findAll());
		String base64 = "";
		// Itera todos os pontos turísticos e seta os atributos conforme conversão
		try {
			for(TouristSpot allTourist : allTouristSpot){
	     		base64 = MainUtil.convertByteToStringFile(allTourist.getMainPicture());
	     		base64 = allTourist.getNameMainPicture() + "," + base64;
	     		allTourist.setBase64(base64);
	     	}
		} catch(Exception e) {
			System.err.println("Erro ao converter base 64 para binário convertBase64ToBinaryImage : " + e.getMessage());
		}
		return allTouristSpot;
	}

	// Salva ponto turístico se a validação for efetivada
	@Override
	public TouristSpot saveTouristIfAuthAndVaidated(TouristSpot touristSpot) {
		TouristSpot tourist = touristSpotRepository.findUniqueTouristSpotByName(touristSpot.getName());
		TouristSpot result = new TouristSpot();
		User user = userService.currentUserInSession();
    	if ((tourist != null && tourist.getId() != null)) {
    		result.setName("Erro");
    	}
    	else if (user!= null) { 
    		
    		Category category = categoryRepository.findCategoryById(touristSpot.getCategory().getId());
    		try {
    			// Usando os métodos da MainUtil extraio a string da base64 e o tipo de arquivo (que não é usado no byte array)
    			String base64Content = MainUtil.getBase64fFile(touristSpot.getBase64());
        		String nameContent = MainUtil.getNameOfFile(touristSpot.getBase64());
        		
        		// Ao criar um objeto do ponto turístico já converto a base64 String para byte []
        		result = new TouristSpot(touristSpot.getName(), MainUtil.convertStringToByteFile(base64Content), nameContent
        				, true, user, category, touristSpot.getLatLocalization(), touristSpot.getLongLocalization());
        		result =  touristSpotRepository.save(result);
    		}catch(Exception e) {
    			System.err.println("Erro ao extrair base64 saveTouristIfAuthAndVaidated : " + e.getMessage());
    		}
    		
    	}
    	return result;
	}

	@Override
	public TouristSpot getOne(long id) {
		return touristSpotRepository.getOne(id);
	}

}
