package com.emilie.Lib10WebClient.Controllers;

import com.emilie.Lib10WebClient.Proxy.FeignProxy;
import com.emilie.Lib10WebClient.Security.JwtProperties;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {

    private static final String REDIRECT_LOGIN_VIEW="redirect:/login";
    private static final String REDIRECT_USER_ACCOUNT_VIEW="redirect:/userAccount";

    private final FeignProxy feignProxy;

    @Autowired
    public ReservationController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }

    @GetMapping("/cancelReservation/{id}")
    public String cancelReservation(@CookieValue(value= JwtProperties.HEADER, required=false) String accessToken,
                                    @PathVariable(value="id") Long id)
    {
        if (accessToken == null) return REDIRECT_LOGIN_VIEW;
        try{
            ResponseEntity<?> response=feignProxy.cancelReservation( accessToken, id );

            return REDIRECT_USER_ACCOUNT_VIEW;
        }catch(FeignException e){
            //todo handle forbidden message
            return REDIRECT_USER_ACCOUNT_VIEW;
        }
    }
}
