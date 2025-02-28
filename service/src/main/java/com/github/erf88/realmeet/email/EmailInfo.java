package com.github.erf88.realmeet.email;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EmailInfo {
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final List<String> bcc;
    private final String subject;
    private final List<Attachment> attachments;
    private final String template;
    private final Map<String, Object> templateData;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String from;
        private List<String> to;
        private List<String> cc;
        private List<String> bcc;
        private String subject;
        private List<Attachment> attachments;
        private String template;
        private Map<String, Object> templateData;

        private Builder() { }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder cc(List<String> cc) {
            this.cc = cc;
            return this;
        }

        public Builder bcc(List<String> bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder template(String template) {
            this.template = template;
            return this;
        }

        public Builder templateData(Map<String, Object> templateData) {
            this.templateData = templateData;
            return this;
        }

        public EmailInfo build() {
            return new EmailInfo(from, to, cc, bcc, subject, attachments, template, templateData);
        }
    }
}
