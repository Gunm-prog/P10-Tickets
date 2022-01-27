package com.emilie.Lib10.Services;

import com.emilie.Lib10.Models.Dtos.LoanDto;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import com.emilie.Lib10.Models.Entities.Reservation;
import com.emilie.Lib10.Models.Mails.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import com.emilie.Lib10.Config.ThymeleafTemplateConfig;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class JavaMailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private void sendHtmlEmail(Mail mail) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
    //    helper.addAttachment("template-cover", new ClassPathResource("templates/bibliotheque.png"));
        Context context = new Context();
        context.setVariables(mail.getProps());
        String html = templateEngine.process(mail.getTemplatePath(), context);

        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        log.info("Sending email: {} with html body: {}", mail, html);
        javaMailSender.send(message);
    }

    /**
     * This method retrieve all delays loans by calling the MS library-api and send a mail to all customers
     */
    public void sendRecoveryMail(UserDto userWithDelayedLoan) throws MessagingException {

        Mail mail = new Mail();
        mail.setFrom("lib10@batch.com");
        mail.setMailTo(userWithDelayedLoan.getEmail());
        mail.setSubject("delay of your loan");
        mail.setTemplatePath("recoveryMail");

        Map<String, Object> modelVar = new HashMap<>();
        modelVar.put("subject", mail.getSubject());

        //retrieve delayedLoan in userDto Object
        List<LoanDto> delayedLoans = userWithDelayedLoan.getLoanDtos().stream()
                        .filter(l -> l.getLoanEndDate().before(new Date()))
                .collect(Collectors.toList());

        //add delayedLoans for recoveryMail model
        modelVar.put("delayedLoans", delayedLoans);

        mail.setProps(modelVar);

            sendHtmlEmail(mail);

    }


    public void sendActiveReservationMail(ReservationDto reservationDto) throws MessagingException {
        Mail mail = new Mail();
        mail.setFrom("lib10@batch.com");
        mail.setMailTo(reservationDto.getUserDto().getEmail());
        mail.setSubject("reservation available for pick-up");
        mail.setTemplatePath("activeReservation");

        Map<String, Object> modelVar = new HashMap<>();
        modelVar.put("subject", mail.getSubject());
        modelVar.put("bookTitle", reservationDto.getBookDto().getTitle());

        mail.setProps(modelVar);

        sendHtmlEmail(mail);
    }
}