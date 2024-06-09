package com.bottle_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm")
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm")
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Bottle> created = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.PERSIST)
    private List<Bottle> received = new ArrayList<>();

    @Builder
    public User(String username, String password, String email, Date createdAt, Date updatedAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addCreated(Bottle bottle) {
        this.created.add(bottle);
        bottle.setCreator(this);
    }
    public void addReceived(Bottle bottle) {
        this.received.add(bottle);
        bottle.setReceiver(this);
    }
}
