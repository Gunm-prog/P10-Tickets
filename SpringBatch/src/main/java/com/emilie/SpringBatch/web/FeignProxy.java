package com.emilie.SpringBatch.web;


import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.model.Reservation;
import com.emilie.SpringBatch.model.UserAccountJwt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name= "feignClient", url="localhost:8081")
public interface FeignProxy {

    @PostMapping("/authenticate")
    String login(@RequestBody UserAccountJwt userAccountJwt);

    @GetMapping("/api/v1/loans/delayList")
    List<Loan> getLoanDelayLoan(@RequestHeader("Authorization") String accessToken);

    @PostMapping("api/v1/loans/sendRecoveryMails/{id}")
    void callRecoveryMailSender(@RequestHeader("Authorization") String accessToken);

    @GetMapping("api/v1/reservations/all")
    List<Reservation> getAllReservation(@RequestHeader("Authorization") String accessToken);

    @DeleteMapping("api/v1/reservations/cancel/{id}")
    void deleteExpiredReservation(@RequestHeader("Authorization") String accessToken, @PathVariable("id") Long id);
}