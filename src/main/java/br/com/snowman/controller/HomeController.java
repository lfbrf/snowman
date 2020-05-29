package br.com.snowman.controller;

import java.io.IOException;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.snowman.domain.AccessToken;
import br.com.snowman.domain.AccessTokenData;
import br.com.snowman.domain.UserDetails;
import br.com.snowman.service.HomeService;

@RestController
//@Controller
public class HomeController {
	
	@Autowired
	HomeService homeService;

	@Autowired
	private HttpServletRequest context;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private final String REDIRECT_URI;
    private final String APP_ID;
    
    
   

    public HomeController(
        @Value("${REDIRECT_URI}") String REDIRECT_URI,
        @Value("${APP_ID}") String APP_ID,
        @Value("${APP_SECRET}") String APP_SECRET) {
        this.REDIRECT_URI = REDIRECT_URI;
        this.APP_ID = APP_ID;
    }
    
    
    

    @GetMapping("/facebook/login")
    public ResponseEntity<?> facebookLogin(@RequestParam("code") String code, @RequestParam("state") String state,
        HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {
        AccessToken accessToken;
        try {
            accessToken = homeService.getAccessTokenFromCode(code);
        } catch (RuntimeException e) {
            return ResponseEntity.status(Integer.parseInt(e.getMessage())).build();
        }

        LOGGER.info("Access token = {}", accessToken);

        String appAccessToken;
        try {
            appAccessToken = homeService.getAppAccessToken();
        } catch (RuntimeException e) {
            return ResponseEntity.status(Integer.parseInt(e.getMessage())).build();
        }

        AccessTokenData accessTokenData = homeService.inspectAccessToken(accessToken.getAccess_token(), appAccessToken);
        LOGGER.info("Verify token = {}", accessTokenData);
        if (!accessTokenData.isIs_valid() || accessTokenData.getApp_id() != Long.valueOf(APP_ID)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails;
        try {
            userDetails = homeService.getUserDetailsFromAccessToken(accessToken.getAccess_token());
        } catch (RuntimeException e) {
            return ResponseEntity.status(Integer.parseInt(e.getMessage())).build();
        }

        LOGGER.info("User is authenticated: {}", userDetails);
        Cookie cookie = new Cookie("access_token", accessToken.getAccess_token());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int) accessToken.getExpires_in());
        
        
        
        HttpSession session = context.getSession();
        session.setAttribute("cookieSession", cookie);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.sendRedirect(REDIRECT_URI);
        homeService.saveUserIfNotExists(accessToken.getAccess_token(), userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/facebook/auth")
    public boolean isAuthenticated(@CookieValue(value = "access_token", required = false) String access_token, HttpServletResponse response) {
    	
        if (access_token == null) {
            return false;
        }
        return homeService.userIsAuthenticated(access_token);
    }

    @GetMapping("/facebook/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "access_token") String access_token,
        HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("access_token", "");
        HttpSession session = context.getSession();
        session.setAttribute("cookieSession", cookie);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/facebook/userinfo")
    public UserDetails getUserDetails(@CookieValue("access_token") String access_token) {
        return homeService.getUserDetailsFromAccessToken(access_token);
    }

    @GetMapping("/facebook/getLoginUri")
    public String getLoginUri() {
        String uri = "https://www.facebook.com/v2.9/dialog/oauth?client_id=" + APP_ID + "&redirect_uri=" + "https://localhost:8445/"
            + "&state=" + homeService.genCSRF();
        return uri;
    }

 
}
