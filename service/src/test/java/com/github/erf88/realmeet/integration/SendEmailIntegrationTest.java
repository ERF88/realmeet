package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.utils.TestUtils.sleep;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.erf88.realmeet.core.BaseIntegrationTest;
import com.github.erf88.realmeet.email.EmailSender;
import com.github.erf88.realmeet.email.model.EmailInfo;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class SendEmailIntegrationTest extends BaseIntegrationTest {
    private static final String EMAIL_ADDRESS = "dev@gmail.com";
    private static final String SUBJECT = "subject";
    private static final String EMAIL_TEMPLATE = "template-test.html";

    @Autowired
    private EmailSender victim;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Test
    void testSendEmail() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        EmailInfo emailInfo = EmailInfo
            .newBuilder()
            .from("sandbox.smtp.mailtrap.io")
            .to(List.of(EMAIL_ADDRESS))
            .subject(SUBJECT)
            .template(EMAIL_TEMPLATE)
            .templateData(Map.of("param", "some text"))
            .build();

        victim.send(emailInfo);
        sleep(2000);
        verify(javaMailSender).send(eq(mimeMessage));
    }
}
