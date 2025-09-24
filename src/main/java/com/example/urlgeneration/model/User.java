package com.example.urlgeneration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "tokens")
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number", length = 20)
    private String phoneNo;

    @Column(name = "created_at", updatable = false, nullable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false)
    private Instant updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UrlToken> tokens = new ArrayList<>();


    // --- helper methods ---
    public void addToken(UrlToken token) {
        if (token == null) return;
        if (!this.tokens.contains(token)) {
            this.tokens.add(token);
        }
        token.setUser(this);
    }

    public void removeToken(UrlToken token) {
        if (token == null) return;
        this.tokens.remove(token);
        token.setUser(null);
    }

}
