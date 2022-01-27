package com.emilie.SpringBatch.task;


import com.emilie.SpringBatch.Service.JavaMailSenderService;
import com.emilie.SpringBatch.Service.LoanStatusService;
import com.emilie.SpringBatch.Service.LoginService;
import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.model.User;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@EnableAsync
@Slf4j
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
     * (cron="0 0 1 * * ?") prod context : run task every day at 01:00 am
     * <p>
     * First, get a security token with batch account,
     * then run the mail task.
     */
   // @Scheduled(cron="0 0 1 * * ?")
    @Scheduled(cron="*/10 * * * * *")
    public void runScheduledTask() {
        try{
            String accessToken=loginService.authenticateBatch();

            /*List<Loan> delayedLoans=loanStatusService.getDelay( accessToken );

            List<User> userList = new ArrayList<>();
            for(Loan loan : delayedLoans){
                if(!userList.contains(loan.getUserDto())){
                    userList.add(loan.getUserDto());
                }
            }

            for(User user : userList){
                List<Loan> delayedLoanByUser = delayedLoans.stream()
                        .filter(l -> l.getUserDto().equals(user))
                        .collect(Collectors.toList());

                loanStatusService.sendRecoveryMails(accessToken, delayedLoanByUser);
            }*/

            loanStatusService.sendRecoveryMails(accessToken);
            /*List<List<Loan>> delayedLoansByUser= new ArrayList<>();
            for(Loan loan : delayedLoans) {
                List<Loan> result = delayedLoans.stream()
                        .filter(l -> l.getUserDto().equals(loan.getUserDto()))
                        .collect(Collectors.toList());
            }*/

          //  loanStatusService.sendRecoveryMails(accessToken, delayedLoansByUser);
          /*  for(Loan loan : delayedLoans){
                loanStatusService.sendRecoveryMails(accessToken, loan.getId());
            }
*/
            log.info( "recovery mails successfully send" );
        }catch(FeignException e){
            log.warn( e.getMessage(), e );
        }
    //
    //    System.out.println( accessToken );
    //    List<Loan> delayedLoans=loanStatusService.getDelay( accessToken );
    //    System.out.println( delayedLoans );
    //   javaMailSenderService.sendRecoveryMail( delayedLoans );
    }

    //todo add task for check expired Reservations
}
