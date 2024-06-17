package com.kj.musinsaproject.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Entity
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime addDate;

    @PrePersist
    protected void onCreate() {
        addDate = LocalDateTime.now();
    }

    @Builder
    private Category(Long id, String name, LocalDateTime addDate) {
        this.id = id;
        this.name = name;
        this.addDate = addDate;
    }
}
