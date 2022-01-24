package com.emilie.Lib10.Services;

import com.emilie.Lib10.Models.Dtos.LoanDto;
import com.emilie.Lib10.Models.Entities.MailAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class JavaMailSenderService {

    @Value("${spring.mail.username}")
    private String applicationEmail;

    @Value("${spring.mail.password}")
    private String appEmailPassword;

    private final JavaMailSender javaMailSender;

    @Autowired
    public JavaMailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender=javaMailSender;
    }

    private void sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom( "lib10@batch.com" );
        message.setTo( to );
        message.setSubject( subject );
        message.setText( body );
        javaMailSender.send( message );
    }

    private void sendHtmlEmail(String to, String subject, List<String> messageList) throws MessagingException {

        Properties props = System.getProperties();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(applicationEmail, appEmailPassword);
            }
        });

        MimeMessage message = new MimeMessage( session );

        message.setFrom(new InternetAddress("lib10@batch.com", false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

        message.setSubject( subject );

        StringBuilder htmlMsg =new StringBuilder( "<h3>" + subject + "</h3>"
                + "<img src='http://www.apache.org/images/asf_logo_wide.gif'>" );

        for(String msgText : messageList){
            htmlMsg.append( " <p>" ).append( msgText ).append( "</p>" );
        }

        message.setContent( htmlMsg.toString(), "text/html");
        message.setSentDate(new Date());

        javaMailSender.send( message );
      //  Transport.send(message);
    }

    /**
     * This method retrieve all delays loans by calling the MS library-api and send a mail to all customers
     */
    public void sendRecoveryMail(LoanDto loanDto) throws MessagingException {

    //   for (LoanDto loanDto : loansList) {

            List<String> messageList = new ArrayList<>();

            messageList.add("the return date of your loan for the book " + loanDto.getCopyDto().getBookDto().getTitle() + " has passed.");
        //    String message="the return date of your loan for the book " + loanDto.getCopyDto().getBookDto().getTitle() + " has passed.";

            if (loanDto.isExtended()) {
             //   System.out.println( loanDto.getId() + " delay and non renewable " );
                messageList.add("you cannot renew your loan anymore.");
              //  message+="you cannot renew your loan anymore.";
            } else {
               // System.out.println( loanDto.getId() + " delay but renewable " );
                messageList.add("you can renew your loan.");
             //   message+="you can renew your loan.";
            }

            /*sendHtmlEmail(
                    loanDto.getUserDto().getEmail(),
                    "delay of your loan.",
                    messageList
            );*/

            sendSimpleMessage(
                    loanDto.getUserDto().getEmail(),
                    "delay of your loan.",
                    messageList.toString()
            );

     //   }
    }

    //todo mail for reservation available
}
