package com.emilie.SpringBatch.Service;

import com.emilie.SpringBatch.model.Reservation;
import com.emilie.SpringBatch.web.FeignProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServices {

    private FeignProxy feignProxy;

    @Autowired
    public ReservationServices(FeignProxy feignProxy) {
        this.feignProxy=feignProxy;
    }

    public List<Reservation> getExpiredReservation(String accessToken){
        List<Reservation> reservationsList = this.feignProxy.getAllReservation(accessToken);

        List<Reservation> expiredReservations= new ArrayList<>();
        for(Reservation reservation : reservationsList){
            if(reservation.isActive() && reservation.getReservationEndDate().isBefore(LocalDateTime.now())){
                expiredReservations.add(reservation);
            }
        }

        return expiredReservations;
    }

    public void deleteExpiredReservation(String accessToken, Reservation expiredReservation){
        feignProxy.deleteExpiredReservation(accessToken, expiredReservation.getId());
    }
}
