package br.com.snowman.service;

import br.com.snowman.domain.AccessToken;
import br.com.snowman.domain.AccessTokenData;
import br.com.snowman.domain.UserDetails;

public interface HomeService {
   public abstract boolean userIsAuthenticated(String access_token);
   AccessTokenData inspectAccessToken(String accessToken, String appAccessToken);
   AccessToken getAccessTokenFromCode(String code);
   UserDetails getUserDetailsFromAccessToken(String accessToken);
   void saveUserIfNotExists(String accessToken, UserDetails userDetails);
   String getAppAccessToken();
   String genCSRF();
}