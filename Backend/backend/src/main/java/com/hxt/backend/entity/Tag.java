package com.hxt.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer tag_id;
    private String name;
}
