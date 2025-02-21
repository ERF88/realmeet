package com.github.erf88.realmeet.domain.entity;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Client client = (Client) o;
        return getApiKey() != null && Objects.equals(getApiKey(), client.getApiKey());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
            : getClass().hashCode();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String apiKey;
        private String description;
        private Boolean active;

        private Builder() {}

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Client build() {
            return new Client(apiKey, description, active);
        }
    }
}
