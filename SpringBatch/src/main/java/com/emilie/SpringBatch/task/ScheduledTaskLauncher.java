package com.emilie.SpringBatch.task;

import com.emilie.SpringBatch.Service.LoanStatusService;
import com.emilie.SpringBatch.Service.LoginService;
import com.emilie.SpringBatch.Service.ReservationServices;
import com.emilie.SpringBatch.model.Reservation;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@EnableAsync
@Slf4j
public class ScheduledTaskLauncher {

    private final LoanStatusService loanStatusService;
    private final ReservationServices reservationServices;
    private final LoginService loginService;



    @Autowired
    public ScheduledTaskLauncher(LoanStatusService loanStatusService,
                                 ReservationServices reservationServices,
                                 LoginService loginService) {
        this.loanStatusService=loanStatusService;
        this.reservationServices = reservationServices;
        this.loginService=loginService;

    }

    /**
     * Scheduled task run in loop for the demo
     * (cron="0 0 1 * * ?") prod context : run task every day at 01:00 am
     * <p>
     * First, get a security token with batch account,
     * then run the mail task.
     */
  //  (cron="*/10 * * * * *") //10 seconds for presentation
  //  (cron="0 0 1 * * ?") // for one day
    @Scheduled(cron="*/10 * * * * *")
    public void runScheduledRecoveryTask() {
        try{
            List<String> dataResponse=loginService.authenticateBatch();
            String accessToken = dataResponse.get(0);
            loanStatusService.sendRecoveryMails(accessToken);

            log.info( "recovery mails successfully send" );

            //Expired reservation task

            List<Reservation> expiredResaList = reservationServices.getExpiredReservation(accessToken);

            if(expiredResaList.isEmpty()){
                log.info( "no expired reservation exist" );
            }

            for(Reservation expiredReservation : expiredResaList){
                reservationServices.deleteExpiredReservation(accessToken, expiredReservation);
                log.info( "expired reservation with id " + expiredReservation.getId() + " deleted" );
            }

        }catch(FeignException e){
            log.warn( e.getMessage(), e );
        }
    }

}
