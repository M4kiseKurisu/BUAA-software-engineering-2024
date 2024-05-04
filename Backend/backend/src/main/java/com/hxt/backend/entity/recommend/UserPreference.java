package com.hxt.backend.entity.recommend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "user_preference")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_preference_id;
    private Integer user_id;
    private String keyword;
    private double preference;
    private Timestamp time;
}
