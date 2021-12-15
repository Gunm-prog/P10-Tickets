package com.emilie.SpringBatch.web;


import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.model.UserAccountJwt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name= "feignClient", url="localhost:8181")
public interface FeignProxy {

    @PostMapping("/authenticate")
    String login(@RequestBody UserAccountJwt userAccountJwt);

    @GetMapping("/api/v1/loans/delayList")
    List<Loan> getLoanDelayLoan(@RequestHeader("Authorization") String accessToken);




}