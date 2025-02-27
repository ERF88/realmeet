package com.github.erf88.realmeet.config.properties;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
@Getter
@RequiredArgsConstructor
public class EmailProperties {
    public static final String PROPERTY_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String PROPERTY_SMTP_PORT = "mail.smtp.port";
    public static final String PROPERTY_SMTP_AUTH = "mail.smtp.auth";
    public static final String PROPERTY_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    private final String host;
    private final String username;
    private final String password;
    private final String from;
    private final Map<String, String> properties;

    public String getProperty(String name) {
        return properties.get(name);
    }
}
