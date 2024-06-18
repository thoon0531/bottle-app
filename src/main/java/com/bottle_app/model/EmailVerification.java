package com.bottle_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "EMAIL_VERIFICATION")
@Getter
@Setter
@NoArgsConstructor
public class EmailVerification {
    @Id
    @GeneratedValue(generator = "UUID_GENETATOR")
    @GenericGenerator(
            name = "UUID_GENERATOR",
            type = org.hibernate.id.uuid.UuidGenerator.class
    )
    private UUID verificationId;

    private String username;

    public EmailVerification(String username) {
        this.username = username;
    }
}
