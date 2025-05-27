package team.themoment.imi.global.email.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.themoment.imi.global.email.exception.EmailSendingFailedException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    @Value("${google.smtp.content.redirect-url}")
    private String redirectUrl;
    private static final String EMAIL_SUBJECT = "imi 이메일 인증";
    private static final String CONTACT_EMAIL = "the.moment.imi@gmail.com";

    public void sendAuthenticationEmail(String email, String authCode) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        context.setVariable("officialEmail",CONTACT_EMAIL);
        context.setVariable("verificationUrl", redirectUrl);
        String htmlContent = templateEngine.process("MailTemplate", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        ClassPathResource logoImage = new ClassPathResource("static/image/logo.png");
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(EMAIL_SUBJECT);
            mimeMessageHelper.setText(htmlContent, true);
            mimeMessageHelper.addInline("logo", logoImage);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailSendingFailedException();
        }
    }
}