package com.hxt.backend.entity.group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "apply_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ag_id;

    private Integer user_id;

    private Integer group_id;

    private String content;

    private boolean pushed;
}
