package com.fawry.user_api.entity;

import com.fawry.user_api.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Email(message = "Invalid email format")
    @NotNull
    private String email;

    @NotBlank(message = "password can't be blank")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean isActive=true;

    @CreationTimestamp
    @NotNull
    private Instant createdAt;

    @UpdateTimestamp
    @NotNull
    private Instant updatedAt;
}
