package com.example.fitness.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

import java.util.Map;
import java.util.HashMap;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy =GenerationType.UUID )
    private String id;
    @Enumerated(EnumType.STRING)
    private ActivityType type;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String,Object>additionalMetrics;

    private Integer duration;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
