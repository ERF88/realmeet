package com.github.erf88.realmeet.email;

import com.github.erf88.realmeet.email.model.Attachment;
import com.github.erf88.realmeet.email.model.EmailInfo;
import com.github.erf88.realmeet.exception.EmailSendingException;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.erf88.realmeet.util.StringUtils.join;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {
    private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";
    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;

    public void send(EmailInfo emailInfo) {
        log.info("Sending e-mail with subject '{}' to '{}'", emailInfo.getSubject(), emailInfo.getTo());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMultipart multipart = new MimeMultipart();

        addBasicDetails(emailInfo, mimeMessage);
        addHtmlBody(emailInfo.getTemplate(), emailInfo.getTemplateData(), multipart);
        addAttachments(emailInfo.getAttachments(), multipart);
    }

    private void addBasicDetails(EmailInfo emailInfo, MimeMessage mimeMessage) {
        try {
            mimeMessage.setFrom(emailInfo.getFrom());
            mimeMessage.setSubject(emailInfo.getSubject());
            mimeMessage.addRecipients(Message.RecipientType.TO, join(emailInfo.getTo()));

            if(nonNull(emailInfo.getCc())){
                mimeMessage.addRecipients(Message.RecipientType.CC, join(emailInfo.getCc()));
            }

            if(nonNull(emailInfo.getBcc())){
                mimeMessage.addRecipients(Message.RecipientType.BCC, join(emailInfo.getBcc()));
            }
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Error adding data to MIME Message");
        }
    }

    private void addHtmlBody(String template, Map<String, Object> templateData, MimeMultipart multipart) {
        MimeBodyPart messageHtmlPart = new MimeBodyPart();
        Context context = new Context();

        if(nonNull(templateData)){
            context.setVariables(templateData);
        }

        try {
            messageHtmlPart.setContent(templateEngine.process(template, context), TEXT_HTML_CHARSET_UTF_8);
            multipart.addBodyPart(messageHtmlPart);
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Error adding HTML content to MIME Message");
        }
    }

    private void addAttachments(List<Attachment> attachments, MimeMultipart multipart) {
        if(nonNull(attachments)){
            attachments.forEach(attachment -> {
                try {
                    MimeBodyPart messageAttachmentPart = new MimeBodyPart();
                    messageAttachmentPart.setDataHandler(
                        new DataHandler(
                            new ByteArrayDataSource(attachment.getInputStream(), attachment.getContentType())
                        )
                    );
                    messageAttachmentPart.setFileName(attachment.getFileName());
                    multipart.addBodyPart(messageAttachmentPart);
                } catch (MessagingException | IOException e) {
                    throwEmailSendingException(e, "Error adding attachments to MIME Message");
                }
            });
        }
    }

    private void throwEmailSendingException(Exception exception, String errorMessage) {
        String fullErrorMessage = String.format("%s: %s", exception.getMessage(), errorMessage);
        log.error(fullErrorMessage);
        throw new EmailSendingException(fullErrorMessage, exception);
    }
}
