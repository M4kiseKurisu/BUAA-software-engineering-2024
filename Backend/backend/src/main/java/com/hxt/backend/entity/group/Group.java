package com.hxt.backend.entity.group;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Group {
    @Id
    @Column(name = "group_id")
    private Integer group_id;

    @Column(name = "name")
    private String name;

    @Column(name = "promoter_id")
    private Integer promoter_id;

    @Column(name = "member_count")
    private Integer member_count;

    @Column(name = "content")
    private String content;

    @Column(name = "permitted_num")
    private Integer permitted_num;

    @Column(name = "is_examine")
    private boolean is_examine;
}
