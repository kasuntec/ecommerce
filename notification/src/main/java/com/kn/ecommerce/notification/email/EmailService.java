package com.kn.ecommerce.notification.email;

import com.kn.ecommerce.kafka.order.OrderLine;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kn.ecommerce.notification.email.EmailTemplate.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentConfirmationEmail(String toSend, String name, BigDecimal amount, String orderRef) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("contact@ecommerce.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> varibles =new HashMap<>();
        varibles.put("customerName", name);
        varibles.put("amount", amount);
        varibles.put("orderRef", orderRef);

        Context context =new Context();
        context.setVariables(varibles);
        mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate,true);

            mimeMessageHelper.setTo(toSend);
            mailSender.send(mimeMessage);

            log.info("INFO - Email send to {} with template", toSend);
        } catch (Exception e) {
            log.warn("can't send email to: {}", toSend);
        }

    }

    @Async
    public void sendOrderConfirmationEmail(String toSend, String name, BigDecimal amount, String orderRef, List<OrderLine> orderLines) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("contact@ecommerce.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> varibles =new HashMap<>();
        varibles.put("customerName", name);
        varibles.put("amount", amount);
        varibles.put("orderRef", orderRef);
        varibles.put("orderLines", orderLines);

        Context context =new Context();
        context.setVariables(varibles);
        mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate,true);

            mimeMessageHelper.setTo(toSend);
            mailSender.send(mimeMessage);

            log.info("INFO - Email send to {} with template", toSend);
        } catch (Exception e) {
            log.warn("can't send email to: {}", toSend);
        }

    }

}
