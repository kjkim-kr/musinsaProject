package com.kj.musinsaproject.brand;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
