package com.it.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userCode;   //用户UUID
    private String username;    //用户名
    private String password;    //用户密码
    private String email;       //用户邮箱
    private String phone;   //电话号码
    private Integer role;        //用户角色
    private String image;       //用户头像
    private String lastIp;     //上次登录IP
    private String lastTime;   //上次登录时间
}
