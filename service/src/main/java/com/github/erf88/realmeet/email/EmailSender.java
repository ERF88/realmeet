package com.github.erf88.realmeet.email;

import com.github.erf88.realmeet.email.model.EmailInfo;
import com.github.erf88.realmeet.util.StringUtils;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;

import java.util.Objects;

import static com.github.erf88.realmeet.util.StringUtils.join;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;

    public void send(EmailInfo emailInfo) {
        log.info("Sending e-mail with subject '{}' to '{}'", emailInfo.getSubject(), emailInfo.getTo());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMultipart multipart = new MimeMultipart();

        mimeMessage.setFrom(emailInfo.getFrom());
        mimeMessage.setSubject(emailInfo.getSubject());
        mimeMessage.addRecipients(Message.RecipientType.TO, join(emailInfo.getTo()));

        if(nonNull(emailInfo.getCc())){
            mimeMessage.addRecipients(Message.RecipientType.CC, join(emailInfo.getCc()));
        }

        if(nonNull(emailInfo.getBcc())){
            mimeMessage.addRecipients(Message.RecipientType.BCC, join(emailInfo.getBcc()));
        }
    }
}
