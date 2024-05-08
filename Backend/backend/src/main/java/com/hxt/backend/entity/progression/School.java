package com.hxt.backend.entity.progression;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "school")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer school_id;
    private String name;
    private String intro;
    private String badge;
    private String web;
}
