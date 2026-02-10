package com.example.bloggingplatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64)
    private String name;
}
