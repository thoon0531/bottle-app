package com.bottle_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "BOTTLE")
public class Bottle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @Builder
    public Bottle(String title, String content, Date createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
