package com.emilie.SpringBatch.Service;

import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.web.FeignProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanStatusService {


    private FeignProxy feignProxy;

    @Autowired
    public LoanStatusService(FeignProxy feignProxy) {
        this.feignProxy=feignProxy;
    }

    public List<Loan> getDelay(String accessToken) {
        return feignProxy.getLoanDelayLoan( accessToken );
    }
}
