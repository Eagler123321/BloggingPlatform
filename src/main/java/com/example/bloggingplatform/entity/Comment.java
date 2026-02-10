package com.example.bloggingplatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 600)
    private String content;
    @Column
    private LocalDateTime createdAt;
    @ManyToOne
    private Post post;
}
