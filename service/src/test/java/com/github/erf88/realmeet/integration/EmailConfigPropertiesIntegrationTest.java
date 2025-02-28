package com.github.erf88.realmeet.integration;

import static com.github.erf88.realmeet.config.properties.EmailProperties.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.erf88.realmeet.config.properties.EmailProperties;
import com.github.erf88.realmeet.core.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class EmailConfigPropertiesIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private EmailProperties emailProperties;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.port}")
    private String port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    @Test
    void testLoadConfigProperties() {
        assertNotNull(host);
        assertEquals(host, emailProperties.getHost());

        assertNotNull(username);
        assertEquals(username, emailProperties.getUsername());

        assertNotNull(password);
        assertEquals(password, emailProperties.getPassword());

        assertNotNull(from);
        assertEquals(from, emailProperties.getFrom());

        assertNotNull(protocol);
        assertEquals(protocol, emailProperties.getProperty(PROPERTY_TRANSPORT_PROTOCOL));

        assertNotNull(port);
        assertEquals(port, emailProperties.getProperty(PROPERTY_SMTP_PORT));

        assertNotNull(auth);
        assertEquals(auth, emailProperties.getProperty(PROPERTY_SMTP_AUTH));

        assertNotNull(starttlsEnable);
        assertEquals(starttlsEnable, emailProperties.getProperty(PROPERTY_SMTP_STARTTLS_ENABLE));
    }
}
