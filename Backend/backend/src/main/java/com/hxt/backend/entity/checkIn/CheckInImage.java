package com.hxt.backend.entity.checkIn;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "checkin_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CheckInImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cii_id;
    private Integer check_in_id;
    private Integer image_id;
}
