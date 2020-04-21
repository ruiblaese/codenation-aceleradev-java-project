package com.blaese.error.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 6036815330898209680L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

}
