package com.emilie.Lib10WebClient.Controllers;

import com.emilie.Lib10WebClient.Entities.Book;
import com.emilie.Lib10WebClient.Entities.Reservation;
import com.emilie.Lib10WebClient.Entities.User;
import com.emilie.Lib10WebClient.Entities.UserAccountLogin;
import com.emilie.Lib10WebClient.Proxy.FeignProxy;
import com.emilie.Lib10WebClient.Security.JwtProperties;
import com.emilie.Lib10WebClient.Security.JwtTokenUtils;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/newReservation/{id}")
    public String createReservation(@CookieValue(value= JwtProperties.HEADER, required=false) String accessToken,
                                    @PathVariable(value="id") Long id, Model model){
//todo
        if (accessToken == null) return REDIRECT_LOGIN_VIEW;
        try{

            /*System.out.println(reservation);
            System.out.println(reservation.getUserDto().getUserId());
            System.out.println(reservation.getBookDto().getBookId());*/
          //  System.out.println(book);

            Reservation newReservation = new Reservation();
            User userInfo = new User();
            userInfo.setUserId( JwtTokenUtils.getUserIdFromJWT( accessToken ).longValue() );
            Book bookInfo = new Book();
            bookInfo.setBookId( id );
            newReservation.setUserDto( userInfo );
            newReservation.setBookDto( bookInfo );

            ResponseEntity<?> response=feignProxy.createReservation( accessToken, newReservation );

            return REDIRECT_USER_ACCOUNT_VIEW;
        }catch(FeignException e){
            //todo handle forbidden message
            //with model and an erreurPage
            return REDIRECT_USER_ACCOUNT_VIEW;
        }
    }
}
