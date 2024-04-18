package com.hxt.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String account;

    private String password;

    private String name;

    private String phoneNum;

    private String email;

    private String major;

    private Integer graduateYear;

    private String photo;

    private String sign;

    private String token;

    private Integer tokenTime;

    private Integer headId;
}
