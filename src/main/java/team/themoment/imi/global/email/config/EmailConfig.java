package team.themoment.imi.global.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${google.smtp.port}")
    private int smtpPort;
    @Value("${google.smtp.user.name}")
    private String smtpUsername;
    @Value("${google.smtp.user.password}")
    private String smtpUserPassword;
    private final static String SMTP_HOST = "smtp.gmail.com";

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(SMTP_HOST);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(smtpUsername);
        mailSender.setPassword(smtpUserPassword);


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.timeout", "10000");

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}