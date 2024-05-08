package com.hxt.backend.entity.checkIn;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "checkin_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CheckInComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cic_id;
    private Integer check_in_id;
    private Integer author_id;
    private String content;
    private Timestamp time;
}
