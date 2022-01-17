package com.emilie.library10WebClient.Controllers;


import com.emilie.library10WebClient.Proxy.FeignProxy;
import com.emilie.library10WebClient.Security.JwtProperties;
import com.emilie.library10WebClient.Entities.User;
import com.emilie.library10WebClient.Security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private static final String REDIRECT_LOGIN_VIEW="redirect:/login";
    private static final String USER_ACCOUNT_VIEW="userAccount";
    private static final String REDIRECT_USER_ACCOUNT_VIEW="redirect:/userAccount";
    private static final String USER_ATT="user";

    private final FeignProxy feignProxy;

    @Autowired
    public UserController(FeignProxy feignProxy) {
        this.feignProxy=feignProxy;
    }


    @GetMapping("/userAccount")
    public String userAccount(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, Model model) {

        if (accessToken == null) return REDIRECT_LOGIN_VIEW;
        User user=feignProxy.getLoggedUser( accessToken );

        model.addAttribute( "userInfos", user );
        model.addAttribute( "userFirstname", JwtTokenUtils.getFirstnameFromJWT( accessToken ) );
        model.addAttribute( "userLastname", JwtTokenUtils.getLastnameFromJWT( accessToken ) );
        model.addAttribute( "currentUserId", JwtTokenUtils.getUserIdFromJWT( accessToken ) );

        return USER_ACCOUNT_VIEW;
    }

    @PostMapping("/updateUserAccount")
    public String updateUserAccount(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken,
                                    @ModelAttribute(USER_ATT) User modifiedUser, HttpServletResponse response) {
        if (accessToken == null) return REDIRECT_LOGIN_VIEW;

        modifiedUser.setUserId( JwtTokenUtils.getUserIdFromJWT( accessToken ).longValue() );
        String newToken=feignProxy.updateUser( accessToken, modifiedUser );

        System.out.println( "newToken : " + newToken );
        System.out.println( "oldToken : " + accessToken );

        Cookie cookie=JwtTokenUtils.generateCookie( newToken );
        cookie.setMaxAge( 3600 );
        response.addCookie( cookie );
        return REDIRECT_USER_ACCOUNT_VIEW;
    }
}

