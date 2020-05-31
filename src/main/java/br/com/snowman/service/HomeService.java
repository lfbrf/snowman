package br.com.snowman.service;

import br.com.snowman.domain.AccessToken;
import br.com.snowman.domain.AccessTokenData;
import br.com.snowman.domain.UserDetails;

/**
 * @author luiz
 *
 */
public interface HomeService {
   public abstract boolean userIsAuthenticated(String access_token);
   AccessTokenData inspectAccessToken(String accessToken, String appAccessToken);
   AccessToken getAccessTokenFromCode(String code);
   UserDetails getUserDetailsFromAccessToken(String accessToken);
   void saveUserIfNotExists( UserDetails userDetails);
   String getAppAccessToken();
   String genCSRF();
   boolean isAuthenticated();
   UserDetails getCurrentUserInSession();
}