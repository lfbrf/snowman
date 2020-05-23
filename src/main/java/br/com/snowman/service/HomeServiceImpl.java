package br.com.snowman.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

import br.com.snowman.controller.HomeController;
import br.com.snowman.domain.AccessToken;
import br.com.snowman.domain.AccessTokenData;
import br.com.snowman.domain.Data;
import br.com.snowman.domain.UserDetails;
@Service
public class HomeServiceImpl implements HomeService {
	@Autowired
	private RestTemplate restTemplate;
	
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
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	public String genCSRF() {
		return UUID.randomUUID().toString();
	}

	public boolean userIsAuthenticated(String access_token) {
		AccessTokenData accessTokenData;
		try {
			accessTokenData = inspectAccessToken(access_token, getAppAccessToken());
		} catch (RuntimeException e) {
			LOGGER.warn(e.getMessage());
			return false;
		}

		return !(!accessTokenData.isIs_valid() || accessTokenData.getApp_id() != Long.valueOf(APP_ID));
	}

	public AccessTokenData inspectAccessToken(String accessToken, String appAccessToken) {
		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("input_token", accessToken);
		urlparams.put("access_token", appAccessToken);
		try {
			return restTemplate.getForObject(
					"https://graph.facebook.com/debug_token?input_token={input_token}&access_token={access_token}",
					Data.class, urlparams).getData();
		} catch (HttpStatusCodeException exception) {
			LOGGER.warn(exception.getResponseBodyAsString());
			throw new RuntimeException(String.valueOf(exception.getStatusCode()));
		}
	}

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

	public UserDetails getUserDetailsFromAccessToken(String accessToken) {

		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("access_token", accessToken);
		urlparams.put("fields", "id,name,email");
		LOGGER.info("Retrieving user details with {} and {}", accessToken, urlparams);
		try {
			return restTemplate
					.getForObject("https://graph.facebook.com/v2.9/me/?access_token={access_token}&fields={fields}",
							UserDetails.class, urlparams);
		} catch (HttpStatusCodeException exception) {
			LOGGER.warn(exception.getResponseBodyAsString());
			throw new RuntimeException(String.valueOf(exception.getStatusCode()));
		}
	}

	public String getAppAccessToken() {
		Map<String, String> urlparams = new HashMap<>();
		urlparams.put("client_id", APP_ID);
		urlparams.put("client_secret", APP_SECRET);
		LOGGER.info("Retrieving app access token");

		try {
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
}
