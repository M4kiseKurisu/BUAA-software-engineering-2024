package com.hxt.backend.entity.section;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Section {
    @Id
    @Column(name = "section_id")
    private Integer section_id;

    @Column(name = "name")
    private String name;

    @Column(name = "intro")
    private String intro;

    @Column(name = "type")
    private String type;

    @Column(name = "academy")
    private String academy;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "capacity")
    private Integer capacity;
}
