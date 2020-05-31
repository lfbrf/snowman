package br.com.snowman.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.snowman.domain.AccessToken;
import br.com.snowman.domain.AccessTokenData;
import br.com.snowman.domain.Data;
import br.com.snowman.domain.UserDetails;
import br.com.snowman.model.User;
import br.com.snowman.repository.UserRepository;
import br.com.snowman.service.HomeService;
/**
 * @author luiz
 * Implementação da interface HomeService
 */
@Service
public class HomeServiceImpl implements HomeService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private HttpServletRequest context;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	private final String REDIRECT_URI;
	private final String APP_ID;
	private final String APP_SECRET;

	public HomeServiceImpl(
			@Value("${REDIRECT_URI}") String REDIRECT_URI,
			@Value("${APP_ID}") String APP_ID,
			@Value("${APP_SECRET}") String APP_SECRET) {
		this.REDIRECT_URI = REDIRECT_URI;
		this.APP_ID = APP_ID;
		this.APP_SECRET = APP_SECRET;
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeServiceImpl.class);
	public String genCSRF() {
		return UUID.randomUUID().toString();
	}
	
	// Retorna o usuário da sessão com o token salvo na mesma
	public UserDetails getCurrentUserInSession() {
		HttpSession session = context.getSession();
		Cookie cookie = (Cookie) session.getAttribute("cookieSession");
		return getUserDetailsFromAccessToken(cookie.getValue());
	}

	// Verifica se usuário está autenticado na sessão
	public boolean isAuthenticated() {
		HttpSession session = context.getSession();
		Cookie cookie = (Cookie) session.getAttribute("cookieSession");
		if (cookie!= null) {
			return userIsAuthenticated(cookie.getValue());
		}
		return false;
	}
	
	// Consulta se o token de acesso está autenticado
	public boolean userIsAuthenticated(String access_token) {
		AccessTokenData accessTokenData;
		try {
			accessTokenData = inspectAccessToken(access_token, getAppAccessToken());
		} catch (RuntimeException e) {
			LOGGER.warn(e.getMessage());
			return false;
		}
		// Se não ocorreu exception verifico se o token é válido e retorno
		return !(!accessTokenData.isIs_valid() || accessTokenData.getApp_id() != Long.valueOf(APP_ID));
	}

	// Verifica o token de acesso
	public AccessTokenData inspectAccessToken(String accessToken, String appAccessToken) {
		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("input_token", accessToken);
		urlparams.put("access_token", appAccessToken);
		try {
			// chama o debug token
			return restTemplate.getForObject(
					"https://graph.facebook.com/debug_token?input_token={input_token}&access_token={access_token}",
					Data.class, urlparams).getData();
		} catch (HttpStatusCodeException exception) {
			LOGGER.warn(exception.getResponseBodyAsString());
			throw new RuntimeException(String.valueOf(exception.getStatusCode()));
		}
	}

	// Monta url e retorna o Token de acesso na API do faceook
	public AccessToken getAccessTokenFromCode(String code) {
		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("client_id", APP_ID);
		urlparams.put("redirect_uri", REDIRECT_URI);
		urlparams.put("client_secret", APP_SECRET);
		urlparams.put("code", code);

		try {
			return restTemplate.getForObject(
					"https://graph.facebook.com/oauth/access_token?client_id={client_id}&code={code}&client_secret"
							+ "={client_secret}&redirect_uri={redirect_uri}",
							AccessToken.class, urlparams);
		} catch (HttpStatusCodeException exception) {
			LOGGER.warn(exception.getResponseBodyAsString());
			throw new RuntimeException(String.valueOf(exception.getStatusCode()));
		}
	}

	// Consulta na API do facebook o usuário através de um token
	public UserDetails getUserDetailsFromAccessToken(String accessToken) {

		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("access_token", accessToken);
		urlparams.put("fields", "id,name,email");
		LOGGER.info("Retrieving user details with {} and {}", accessToken, urlparams);
		try { // Fazendo a chamada na api com o accessToken para retorno do UserDetails
			return restTemplate
					.getForObject("https://graph.facebook.com/v2.9/me/?access_token={access_token}&fields={fields}",
							UserDetails.class, urlparams);
		} catch (HttpStatusCodeException exception) {
			LOGGER.warn(exception.getResponseBodyAsString());
			if (String.valueOf(exception.getStatusCode()).equals("403")) {
				LOGGER.warn("Limite de uso do facebook excedido");
			}
			throw new RuntimeException(String.valueOf(exception.getStatusCode()));
		}
	}
	// Responsável por gerar um access token
	public String getAppAccessToken() {
		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("client_id", APP_ID);
		urlparams.put("client_secret", APP_SECRET);
		LOGGER.info("Retrieving app access token");

		try { // Gerando o access token chamadno a API
			String json = restTemplate.getForObject(
					"https://graph.facebook.com/oauth/access_token?client_id={client_id}&client_secret={client_secret"
							+ "}&grant_type=client_credentials",
							String.class, urlparams);
			return new JSONObject(json).getString("access_token");
		} catch (HttpStatusCodeException exception) {
			LOGGER.warn(exception.getResponseBodyAsString());
			throw new RuntimeException(String.valueOf(exception.getStatusCode()));
		}
	}
	
	// Ao autenticar verifica se o usuário já foi salvo no banco 
	// Caso não encontre um registro então salva o mesmo
	public void saveUserIfNotExists( UserDetails userDetails) {
		User u = userRepository.findUserByFaceId(userDetails.getId());
		
		if (u != null && u.getName() != null) 
			return ;
		else
			userRepository.save(new User(userDetails.getName(), userDetails.getEmail(), userDetails.getId(), true));
	}


	
}
