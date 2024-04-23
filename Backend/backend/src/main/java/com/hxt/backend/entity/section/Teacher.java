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
@Table(name = "course_teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Teacher {

    @Id
    @Column(name = "ct_id")
    private Integer ct_id;

    @Column(name = "section_id")
    private Integer section_id;

    @Column(name = "teacher_name")
    private String teacher_name;

    @Column(name = "teacher_intro")
    private String teacher_intro;

}
