package com.bottle_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RAND_ID", unique = true)
    private Long randId; //random index for sending bottle randomly

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL", unique = true)
    @Email
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "LAST_BOTTLE_CREATION")
    private Date lastBottleCreation;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "VERIFIED")
    private Boolean verified;

    @Column(name = "LOCKED")
    private Boolean locked;

    @Column(name = "ACC_CRED_EXPIRED")
    private Boolean accountCredentialsExpired;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Bottle> created = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.PERSIST)
    private List<Bottle> received = new ArrayList<>();

    @Builder
    public User(String username, String password, String email, Date createdAt, Date updatedAt, Date lastBottleCreation, Role role, Boolean verified, long randId, String refreshToken ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.verified = verified;
        this.locked = false;
        this.accountCredentialsExpired = false;
        this.randId = randId;
        this.lastBottleCreation = lastBottleCreation;
        this.refreshToken = refreshToken;
    }

    public void addCreated(Bottle bottle) {
        this.created.add(bottle);
        bottle.setCreator(this);
    }
    public void addReceived(Bottle bottle) {
        this.received.add(bottle);
        bottle.setReceiver(this);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.accountCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !this.verified;
    }
}
