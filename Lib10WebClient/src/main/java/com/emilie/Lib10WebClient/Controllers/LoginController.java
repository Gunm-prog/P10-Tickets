package com.emilie.Lib10WebClient.Controllers;

import com.emilie.Lib10WebClient.Entities.User;
import com.emilie.Lib10WebClient.Entities.UserAccountLogin;
import com.emilie.Lib10WebClient.Proxy.FeignProxy;
import com.emilie.Lib10WebClient.Security.JwtProperties;
import com.emilie.Lib10WebClient.Security.JwtTokenUtils;
import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author gunmm
 */
@Controller
public class LoginController {

    private static final String USER_ATT="user";
    private static final String LOGIN_VIEW="login";
    private static final String REDIRECT_LOGIN_VIEW="redirect:/login";
    private static final String REDIRECT_USER_HOME_VIEW="redirect:/userAccount";
    private static final String ERROR_EMAIL_CONFLICT="Email already exist";
    private static final String REGISTER_CUTOMER="registerCustomerForm";

    private final FeignProxy proxy;

    @Autowired
    public LoginController(FeignProxy proxy) {
        this.proxy=proxy;
    }

    @GetMapping("/registration/customer")
    public String getCustomerRegisterForm(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, Model model) {
        if (accessToken != null) return REDIRECT_USER_HOME_VIEW;
        model.addAttribute( USER_ATT, new User() );
        return REGISTER_CUTOMER;
    }

    @PostMapping("/registration/customer")
    public String newCustomerAccount(@ModelAttribute(USER_ATT) User newUser, Model model) {
        try {
            newUser.setUsername( newUser.getEmail() );
            proxy.newCustomerAccount( newUser );
        } catch (FeignException e) {
            if (e.status() == HttpStatus.CONFLICT.value()) {
                model.addAttribute( "error", ERROR_EMAIL_CONFLICT );
            } else {
                model.addAttribute( "error", "An error occured" );
            }

            model.addAttribute( USER_ATT, newUser );
            return REGISTER_CUTOMER;
        }

        return REDIRECT_LOGIN_VIEW;

    }


    @GetMapping(path= {"/login", "/"})
    public String loginForm(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, Model model) {
        if (accessToken != null) return REDIRECT_USER_HOME_VIEW;
        model.addAttribute( USER_ATT, new User() );

        return LOGIN_VIEW;

    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute(USER_ATT) UserAccountLogin userAccountLogin, Model model,
                            HttpServletResponse response) {

        try {
            List<String> dataResponse=proxy.login( userAccountLogin );
            Cookie cookie=JwtTokenUtils.generateCookie( dataResponse.get( 0 ) );
            cookie.setMaxAge( 3600 );
            response.addCookie( cookie );

            return REDIRECT_USER_HOME_VIEW;
        } catch (Exception e) {
            model.addAttribute( "error", "Bad credentials" );
            model.addAttribute( "user", userAccountLogin );
            return LOGIN_VIEW;
        }
    }

    @GetMapping("/logout")
    public String logoutUser(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, HttpServletResponse response) {
        if (accessToken == null) return REDIRECT_LOGIN_VIEW;
        JwtTokenUtils.clear( response );

        return REDIRECT_LOGIN_VIEW;
    }

}
