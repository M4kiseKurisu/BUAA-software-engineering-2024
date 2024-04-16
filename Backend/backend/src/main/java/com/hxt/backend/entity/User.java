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
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phonenum")
    private String phoneNum;

    @Column(name = "email")
    private String email;

    @Column(name = "major")
    private String major;

    @Column(name = "graduate_year")
    private Integer graduateYear;

    @Column(name = "photo")
    private String photo;

    @Column(name = "sign")
    private String sign;

    @Column(name = "token")
    private String token;

    @Column(name = "token_time")
    private Integer tokenTime;
}
