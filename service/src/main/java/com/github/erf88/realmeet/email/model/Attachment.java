package com.github.erf88.realmeet.email.model;

import java.io.InputStream;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Attachment {
    private final InputStream inputStream;
    private final String contentType;
    private final String fileName;

    public static final class Builder {
        private InputStream inputStream;
        private String contentType;
        private String fileName;

        public static Builder newBuilder() {
            return new Builder();
        }

        private Builder() {}

        public Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Attachment build() {
            return new Attachment(inputStream, contentType, fileName);
        }
    }
}
