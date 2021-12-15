package com.emilie.SpringBatch.Service;

import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.model.LoanEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
@EnableScheduling
@EnableAsync
public class JavaMailSenderService {

    private final JavaMailSender javaMailSender;


    @Autowired
    public JavaMailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender=javaMailSender;


    }

    /**
     * This method retrieve all delays loans by calling the MS library-api and send a mail to all customers
     */
    public void sendRecoveryMail(List<Loan> loansList) {

        List<LoanEmail> loanEmails=new ArrayList<>();

        for (Loan loan : loansList) {

            LoanEmail loanEmail=new LoanEmail();
            loanEmail.setUserDto( loan.getUserDto() );
            loanEmail.setBookDto( loan.getCopyDto().getBookDto() );
            loanEmail.setExpectedBookReturn( loan.getLoanEndDate().toInstant().atZone( ZoneId.systemDefault() ).toLocalDate() );

            String message="the return date of your loan for the book " + loan.getCopyDto().getBookDto().getTitle() + " has passed.";

            if (loan.isExtended()) {
                System.out.println( loan.getId() + " delay and non renewable " );
                message+="you cannot renew your loan anymore.";
            } else {
                System.out.println( loan.getId() + " delay but renewable " );
                message+="you can renew your loan.";
            }

            loanEmail.setMessage( message );
            loanEmails.add( loanEmail );
        }

        for (LoanEmail loanEmail : loanEmails) {
            sendSimpleMessage(
                    loanEmail.getUserDto().getEmail(),
                    "delay of your loan.",
                    loanEmail.getMessage()
            );

        }

    }


    private void sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom( "lib7@batch.com" );
        message.setTo( to );
        message.setSubject( subject );
        message.setText( body );
        System.out.println( message );
        javaMailSender.send( message );
    }

}

