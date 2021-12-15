package com.emilie.SpringBatch.Service;

import com.emilie.SpringBatch.model.UserAccountJwt;
import com.emilie.SpringBatch.web.FeignProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class LoginService {

    private final FeignProxy feignProxy;

    @Autowired
    public LoginService(FeignProxy feignProxy) {
        this.feignProxy=feignProxy;
    }

    public String authenticateBatch() {
        return feignProxy.login( new UserAccountJwt() );
    }

}
