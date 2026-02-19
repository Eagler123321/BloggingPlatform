package com.example.bloggingplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64)
    private String name;
}
