package com.hxt.backend.entity.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "apply_notice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApplyNotice {

    @Id
    @Column(name = "an_id")
    private Integer an_id;

    @Column(name = "group_id")
    private Integer group_id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "content")
    private String content;

    @Column(name = "promoter_id")
    private Integer promoter_id;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "result")
    private Boolean result;
}
