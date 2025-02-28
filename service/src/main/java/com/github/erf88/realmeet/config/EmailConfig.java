package com.github.erf88.realmeet.config;

import com.github.erf88.realmeet.config.properties.EmailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static com.github.erf88.realmeet.config.properties.EmailProperties.*;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSender javaMailSender(EmailProperties emailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(Integer.parseInt(emailProperties.getProperty(PROPERTY_SMTP_PORT)));
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put(PROPERTY_TRANSPORT_PROTOCOL, emailProperties.getProperty(PROPERTY_TRANSPORT_PROTOCOL));
        properties.put(PROPERTY_SMTP_AUTH, emailProperties.getProperty(PROPERTY_SMTP_AUTH));
        properties.put(PROPERTY_SMTP_STARTTLS_ENABLE, emailProperties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE));

        return mailSender;
    }
}
