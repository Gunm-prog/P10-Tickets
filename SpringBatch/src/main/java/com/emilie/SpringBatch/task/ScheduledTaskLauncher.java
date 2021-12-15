package com.emilie.SpringBatch.task;


import com.emilie.SpringBatch.Service.JavaMailSenderService;
import com.emilie.SpringBatch.Service.LoanStatusService;
import com.emilie.SpringBatch.Service.LoginService;
import com.emilie.SpringBatch.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@EnableAsync
public class ScheduledTaskLauncher {

    private final JavaMailSenderService javaMailSenderService;
    private final LoanStatusService loanStatusService;
    private final LoginService loginService;



    @Autowired
    public ScheduledTaskLauncher(JavaMailSenderService javaMailSenderService, LoanStatusService loanStatusService,
                                 LoginService loginService) {
        this.javaMailSenderService=javaMailSenderService;
        this.loanStatusService=loanStatusService;
        this.loginService=loginService;

    }

    /**
     * Scheduled task run in loop for the demo
     * (cron="0 0 1 * * ?") prod context : run task every days at 01:00 am
     * <p>
     * First, get a security token with batch account,
     * then run the mail task.
     */

    @Scheduled(cron="0 0 1 * * ?")
    public void runScheduledTask() {
        String accessToken=loginService.authenticateBatch();
        System.out.println( accessToken );
        List<Loan> delayedLoans=loanStatusService.getDelay( accessToken );
        System.out.println( delayedLoans );
        javaMailSenderService.sendRecoveryMail( delayedLoans );
    }

}
