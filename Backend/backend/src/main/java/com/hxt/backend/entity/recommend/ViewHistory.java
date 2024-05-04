package com.hxt.backend.entity.recommend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "view_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ViewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer view_history_id;
    private Integer user_id;
    private Integer post_id;
    private Timestamp time;
}
