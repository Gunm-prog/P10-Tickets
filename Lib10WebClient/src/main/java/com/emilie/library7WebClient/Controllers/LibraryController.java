package com.emilie.library7WebClient.Controllers;

import com.emilie.library7WebClient.Entities.Library;
import com.emilie.library7WebClient.Proxy.FeignProxy;
import com.emilie.library7WebClient.Security.JwtProperties;
import com.emilie.library7WebClient.Security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/libraries")
public class LibraryController {

    private final FeignProxy feignProxy;


    private static final String LIBRARY_DETAILS = "libraryContactDetails";
    private static final String LIBRARY_LIST = "libraryList";

    @Autowired
    public LibraryController(FeignProxy feignProxy) {
        this.feignProxy=feignProxy;
    }


    @GetMapping("/{id}")
    public String library(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken,
                          @PathVariable("id") Long id, Model model) {
        if (accessToken != null) {
            int userId=JwtTokenUtils.getUserIdFromJWT( accessToken );
            model.addAttribute( "currentUserId", userId );
            model.addAttribute( "userFirstname", JwtTokenUtils.getFirstnameFromJWT( accessToken ) );
            model.addAttribute( "userLastname", JwtTokenUtils.getLastnameFromJWT( accessToken ) );
        }
        Library library=feignProxy.getLibraryById( id );
        model.addAttribute( "library", library );

        return LIBRARY_DETAILS;
    }

    @GetMapping("/list")
    public String libraryList(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, Model model) {
        if (accessToken != null) {
            int userId=JwtTokenUtils.getUserIdFromJWT( accessToken );
            model.addAttribute( "currentUserId", userId );
            model.addAttribute( "userFirstname", JwtTokenUtils.getFirstnameFromJWT( accessToken ) );
            model.addAttribute( "userLastname", JwtTokenUtils.getLastnameFromJWT( accessToken ) );
        }
        List<Library> libraries=feignProxy.getLibraryList();
        model.addAttribute( "libraries", libraries );
        return LIBRARY_LIST;
    }

}
